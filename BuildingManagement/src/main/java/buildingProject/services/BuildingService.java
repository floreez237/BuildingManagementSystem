package buildingProject.services;

import buildingProject.dto.BuildingDTO;
import buildingProject.dto.PersonDTO;
import buildingProject.model.BuildingEntity;
import buildingProject.model.BuildingLevelEntity;
import buildingProject.repositories.BuildingLevelRepository;
import buildingProject.repositories.BuildingRepository;
import buildingProject.services.rooms.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepo;
    private final ModelMapper mapper;
    private final BuildingLevelService buildingLevelService;
    private final RoomService roomService;
    private final BuildingLevelRepository buildingLevelRepo;

    public BuildingService(BuildingRepository buildingRepo, ModelMapper mapper, BuildingLevelService buildingLevelService, RoomService roomService, BuildingLevelRepository buildingLevelRepo) {
        this.buildingRepo = buildingRepo;
        this.mapper = mapper;
        this.buildingLevelService = buildingLevelService;
        this.roomService = roomService;
        this.buildingLevelRepo = buildingLevelRepo;
    }

     public long createBuilding(BuildingDTO buildingDTO, int numberLevels){
         BuildingEntity buildingEntity = mapper.map(buildingDTO, BuildingEntity.class);
         buildingEntity = buildingRepo.save(buildingEntity);

         for (long i = 0; i < numberLevels; i++) {
             buildingLevelService.createLevel(buildingEntity,i+1);
         }
         return buildingEntity.getId();
     }

     public void save(BuildingDTO buildingDTO){
         buildingRepo.save(mapper.map(buildingDTO, BuildingEntity.class));
     }

     @Transactional
     public List<BuildingDTO> getListOfBuildings(){
         List<BuildingEntity> listOfBuildings = buildingRepo.findAll();
         List<BuildingDTO> listOfBuildingsDto = new ArrayList<>();
         listOfBuildings.forEach(buildingEntity -> listOfBuildingsDto.add(mapper.map(buildingEntity,BuildingDTO.class)));

         return listOfBuildingsDto;
     }

     @Transactional
     public void delete(BuildingDTO buildingDTO){
        buildingDTO.getListOfLevelId().forEach(levelId ->{
            BuildingLevelEntity levelEntity = buildingLevelRepo.getOne(levelId);
            roomService.deleteAllByBuildingIdAndLevelNumber(buildingDTO.getId(),levelEntity.getLevelNumber());
        });
        buildingRepo.deleteById(buildingDTO.getId());
     }

     public Long getNumberOfAvailableRooms(BuildingDTO buildingDTO) {
         long total = 0;
         for (Long levelId : buildingDTO.getListOfLevelId()) {
             total += buildingLevelService.getNumberOfFreeRooms(levelId);
         }
         return total;
     }


    public Long getNumberOfOccupiedRooms(BuildingDTO buildingDTO) {
        long total = 0;
        for (Long levelId : buildingDTO.getListOfLevelId()) {
            total += buildingLevelService.getNumberOfOccupiedRooms(levelId);
        }
        return total;
    }

    @Transactional
    public Set<PersonDTO> findAllPersons(BuildingDTO buildingDTO) {
        Set<PersonDTO> setOfPersons = new HashSet<>();
        buildingDTO.getListOfLevelId().forEach(levelId ->{
            setOfPersons.addAll(buildingLevelService.findAllPersons(levelId));
        });
        return setOfPersons;
    }
}
