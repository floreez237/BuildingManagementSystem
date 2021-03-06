package buildingProject.services;

import buildingProject.dto.ContractDTO;
import buildingProject.model.ContractEntity;
import buildingProject.repositories.ContractRepository;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {
    private final ContractRepository contractRepo;
    private final ModelMapper mapper;
    private final RoomRepository roomRepository;

    public ContractService(ContractRepository contractRepo, ModelMapper mapper, RoomRepository roomRepository) {
        this.contractRepo = contractRepo;
        this.mapper = mapper;
        this.roomRepository = roomRepository;
    }

    public  boolean isContractExpired(ContractDTO contractDTO) {
        return LocalDate.now().isAfter(contractDTO.getDateOfPayment().plusMonths(contractDTO.getDuration()));
    }

    public void deleteByRoomId(Long roomId) {
        contractRepo.deleteAllByRoomId(roomId);
    }

    @Transactional
    public void delete(ContractDTO contractDTO) {
        contractRepo.deleteById(contractDTO.getId());
        if (!contractDTO.isObsolete()) {
            roomRepository.getOne(contractDTO.getRoomId()).setOccupied(false);
        }
    }

    @Transactional
    public Long save(ContractDTO contractDTO) {
        roomRepository.getOne(contractDTO.getRoomId()).setOccupied(true);
        return contractRepo.save(mapper.map(contractDTO, ContractEntity.class)).getId();
    }

    @Transactional
    public List<ContractDTO> findAllNonObsolete() {
        return contractRepo.findAllByIsObsoleteIsFalse().stream().map(contractEntity -> mapper.map(contractEntity, ContractDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ContractDTO> findAllObsolete() {
        return contractRepo.findAllByIsObsoleteIsTrue().stream().map(contractEntity -> mapper.map(contractEntity, ContractDTO.class))
                .collect(Collectors.toList());
    }

    public List<ContractDTO> findAllExpiredContracts() {
        return contractRepo.findAll().stream().map(contractEntity -> mapper.map(contractEntity, ContractDTO.class))
                .filter(contractDTO -> {
                    Period period = Period.between(contractDTO.getDateOfPayment(), LocalDate.now());
                    int monthDuration = period.getMonths();
                    return contractDTO.getDuration() < monthDuration;
                }).collect(Collectors.toList());
    }

    public long countExpiredContracts() {
        List<ContractEntity> contractEntityList = contractRepo.findAll();
        return contractEntityList.stream().filter(contractEntity -> {
            Period period = Period.between(contractEntity.getDateOfPayment(), LocalDate.now());
            int monthDuration = period.getMonths();
            return contractEntity.getDuration() < monthDuration;
        }).count();
    }

    public long countExpireIn(int days) {
        List<ContractEntity> contractEntityList = contractRepo.findAll();
        return contractEntityList.stream().filter(contractEntity -> {
            Period period = Period.between(contractEntity.getDateOfPayment(), LocalDate.now());
            int dayDuration = period.getDays();
            LocalDate expirationDate = contractEntity.getDateOfPayment().plusMonths(contractEntity.getDuration());
            int contractDurationInDays = Period.between(contractEntity.getDateOfPayment(), expirationDate).getDays();
            return contractDurationInDays - dayDuration == days;
        }).count();
    }

    public ContractDTO findByRoomId(Long roomId) {
        return mapper.map(contractRepo.findByRoomId(roomId),ContractDTO.class);
    }
}
