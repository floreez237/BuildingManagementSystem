package buildingProject.services;

import buildingProject.dto.BuildingLevelDTO;
import buildingProject.dto.PersonDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.model.PersonEntity;
import buildingProject.model.rooms.RoomEntity;
import buildingProject.repositories.BuildingLevelRepository;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BuildingLevelService {
    private final BuildingLevelRepository buildingLevelRepo;
    private final RoomRepository roomRepository;
    private final ModelMapper mapper;

    public BuildingLevelService(BuildingLevelRepository buildingLevelRepo, RoomRepository roomRepository, ModelMapper mapper) {
        this.buildingLevelRepo = buildingLevelRepo;
        this.roomRepository = roomRepository;
        this.mapper = mapper;
    }

    public void createLevel(BuildingEntity buildingEntity, long levelNumber){
        BuildingLevelEntity levelEntity = new BuildingLevelEntity( levelNumber);
        levelEntity.setBuilding(buildingEntity);
        buildingLevelRepo.save(levelEntity);
        buildingEntity.getListOfLevels().add(levelEntity);
    }

    public Long getNumberOfOccupiedRooms(Long levelId) {
        return getTotalNumberOfRooms(levelId) - getNumberOfFreeRooms(levelId);
    }

    public Long getNumberOfFreeRooms(Long levelId) {
        return roomRepository.countAllByLevelIdAndOccupiedFalse(levelId);
    }

    public Long getTotalNumberOfRooms(Long levelId) {
        return roomRepository.countAllByLevelId(levelId);
    }

    @Transactional
    public BuildingLevelDTO getLevelDto(Long levelId) {
        return mapper.map(buildingLevelRepo.getOne(levelId),BuildingLevelDTO.class);
    }

    //try get it from the person table if this does not function
    @Transactional
    public Set<PersonDTO> findAllPersons(Long levelId) {
        Set<PersonDTO> setOfPersonsInLevel = new HashSet<>();
        Set<RoomEntity> roomsInLevel = roomRepository.findAllByLevelId(levelId);
        roomsInLevel.forEach(roomEntity -> {
            Set<PersonEntity> personsInRoom = roomEntity.getSetOfPersons();
            Set<PersonDTO> dtoSet = personsInRoom.stream().map(personEntity -> mapper.map(personEntity,PersonDTO.class)).collect(Collectors.toSet());
            setOfPersonsInLevel.addAll(dtoSet);
        });
        return setOfPersonsInLevel;
    }
}
