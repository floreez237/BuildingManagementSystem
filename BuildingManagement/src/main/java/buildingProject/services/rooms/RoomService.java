package buildingProject.services.rooms;

import buildingProject.dto.rooms.RoomDTO;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.repositories.room_repositories.RoomRepository;
import buildingProject.repositories.room_repositories.RoomRepository.IdOnly;
import buildingProject.services.PersonService;
import buildingProject.services.bills.ElectricityBillService;
import buildingProject.services.bills.WaterBillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {
    private final RoomRepository roomRepo;
    private final WaterBillService waterBillService;
    private final ElectricityBillService electricityBillService;
    private final PersonService personService;
    private final ModelMapper mapper;
    public RoomService(RoomRepository roomRepo, WaterBillService waterBillService, ElectricityBillService electricityBillService, PersonService personService, ModelMapper mapper) {
        this.roomRepo = roomRepo;
        this.waterBillService = waterBillService;
        this.electricityBillService = electricityBillService;
        this.personService = personService;
        this.mapper = mapper;
    }

    public void deleteRoom(Long roomId) {
        waterBillService.deleteAllByRoomId(roomId);
        electricityBillService.deleteAllByRoomId(roomId);
        personService.deleteAllByRoomId(roomId);
        roomRepo.deleteById(roomId);
    }

    public void deleteAllByBuildingIdAndLevelNumber(long buildingId,long levelNumber) {
        Set<IdOnly> listOfRoomId = roomRepo.findAllByBuildingIdAndLevelLevelNumber(buildingId,levelNumber);
        listOfRoomId.stream().map(IdOnly::getId).forEach(roomId ->{
            waterBillService.deleteAllByRoomId(roomId);
            electricityBillService.deleteAllByRoomId(roomId);
            personService.deleteAllByRoomId(roomId);//this also deletes all the associated contracts
        });
        roomRepo.deleteAllByBuildingIdAndLevelLevelNumber(buildingId,levelNumber);
    }

    public Set<RoomDTO> findAllInRoomsInLevel(Long levelId) {
        return roomRepo.findAllByLevelId(levelId).stream().map(roomEntity -> mapper.map(roomEntity,RoomDTO.class))
                .collect(Collectors.toSet());
    }

    public RoomDTO getRoom(Long roomId) {
        return mapper.map(roomRepo.getOne(roomId),RoomDTO.class);
    }

    public List<RoomDTO> findAll() {
        return roomRepo.findAll().stream().map(roomEntity -> mapper.map(roomEntity, RoomDTO.class)).collect(Collectors.toList());
    }

    public List<RoomDTO> findAllFreeRooms() {
        return roomRepo.findAllByOccupiedFalse().stream().map(roomEntity -> mapper.map(roomEntity, RoomDTO.class)).collect(Collectors.toList());
    }

    public Long countOccupiedRooms() {
        return roomRepo.count() - countFreeRooms();
    }

    public Long countFreeRooms() {
        return roomRepo.countAllByOccupiedFalse();
    }

    public void setRoomOccupied(long roomId,boolean isOccupied) {
        RoomEntity roomEntity = roomRepo.getOne(roomId);
        roomEntity.setOccupied(isOccupied);
        roomRepo.save(roomEntity);
    }
}
