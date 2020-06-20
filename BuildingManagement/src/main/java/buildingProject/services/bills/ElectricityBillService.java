package buildingProject.services.bills;

import buildingProject.dto.bills.BillDTO;
import buildingProject.dto.bills.ElectricityBillDTO;
import buildingProject.model.bills.ElectricityBillEntity;
import buildingProject.repositories.bill_repositories.ElectricityBillRepository;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectricityBillService {
    private final ElectricityBillRepository electricityBillRepo;
    private final ModelMapper mapper;
    private final RoomRepository roomRepository;

    public ElectricityBillService(ElectricityBillRepository electricityBillRepo, ModelMapper mapper, RoomRepository roomRepository) {
        this.electricityBillRepo = electricityBillRepo;
        this.mapper = mapper;
        this.roomRepository = roomRepository;
    }

    public void deleteAllByRoomId(long roomId) {
        electricityBillRepo.deleteAllByRoomId(roomId);
    }

    @Transactional
    public List<BillDTO> findAllBills(List<Long> listOfId) {
        return listOfId.stream().map(id -> mapper.map(electricityBillRepo.getOne(id), ElectricityBillDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public Long create(ElectricityBillDTO billDTO) {
        ElectricityBillEntity billEntity = mapper.map(billDTO, ElectricityBillEntity.class);
        electricityBillRepo.save(billEntity);
        roomRepository.getOne(billDTO.getRoomId()).getElectricityBills().add(billEntity);
        return billEntity.getId();
    }

    public void save(ElectricityBillDTO billDTO) {
        electricityBillRepo.save(mapper.map(billDTO, ElectricityBillEntity.class));

    }

    @Transactional
    public List<ElectricityBillDTO> findAllUnPaidBills() {
        return electricityBillRepo.findAllByIsPaidFalse().stream().map(electricityBillEntity -> mapper.map(electricityBillEntity, ElectricityBillDTO.class))
                .collect(Collectors.toList());
    }

    public Long countUnPaidBills() {
        return electricityBillRepo.countAllByIsPaidFalse();
    }

    public void delete(Long billId) {
        electricityBillRepo.deleteById(billId);
    }
}
