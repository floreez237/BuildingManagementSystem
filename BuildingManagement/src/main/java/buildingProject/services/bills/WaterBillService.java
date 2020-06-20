package buildingProject.services.bills;

import buildingProject.dto.bills.BillDTO;
import buildingProject.dto.bills.WaterBillDTO;
import buildingProject.model.bills.WaterBillEntity;
import buildingProject.repositories.bill_repositories.WaterBillRepository;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaterBillService {
    private final WaterBillRepository waterBillRepo;
    private final ModelMapper mapper;
    private final RoomRepository roomRepository;

    public WaterBillService(WaterBillRepository waterBillRepo, ModelMapper mapper, RoomRepository roomRepository) {
        this.waterBillRepo = waterBillRepo;
        this.mapper = mapper;
        this.roomRepository = roomRepository;
    }

    public void deleteAllByRoomId(long roomId) {
        waterBillRepo.deleteAllByRoomId(roomId);
    }

    @Transactional
    public List<BillDTO> findAllBills(List<Long> listOfId) {
        return listOfId.stream().map(id -> mapper.map(waterBillRepo.getOne(id), WaterBillDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public Long create(WaterBillDTO billDTO) {
        WaterBillEntity billEntity = mapper.map(billDTO, WaterBillEntity.class);
        waterBillRepo.save(billEntity);
        roomRepository.getOne(billDTO.getRoomId()).getWaterBills().add(billEntity);
        return billEntity.getId();
    }

    public void save(WaterBillDTO billDTO) {
        waterBillRepo.save(mapper.map(billDTO,WaterBillEntity.class));
    }

    @Transactional
    public List<WaterBillDTO> findAllUnPaidBills() {
        return waterBillRepo.findAllByIsPaidFalse().stream().map(waterBillEntity -> mapper.map(waterBillEntity, WaterBillDTO.class))
                .collect(Collectors.toList());
    }

    public Long countUnPaidBills() {
        return waterBillRepo.countAllByIsPaidFalse();
    }

    public void delete(Long billId) {
        waterBillRepo.deleteById(billId);
    }
}
