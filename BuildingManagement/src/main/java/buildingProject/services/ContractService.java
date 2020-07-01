package buildingProject.services;

import buildingProject.dto.ContractDTO;
import buildingProject.model.ContractEntity;
import buildingProject.repositories.ContractRepository;
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

    public ContractService(ContractRepository contractRepo, ModelMapper mapper) {
        this.contractRepo = contractRepo;
        this.mapper = mapper;
    }

    public void deleteByRoomId(Long roomId) {
        contractRepo.deleteAllByRoomId(roomId);
    }

    public void delete(Long conId) {
        contractRepo.deleteById(conId);
    }

    public Long save(ContractDTO contractDTO) {
        return contractRepo.save(mapper.map(contractDTO, ContractEntity.class)).getId();
    }

    @Transactional
    public List<ContractDTO> findAll() {
        return contractRepo.findAll().stream().map(contractEntity -> mapper.map(contractEntity,ContractDTO.class))
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
