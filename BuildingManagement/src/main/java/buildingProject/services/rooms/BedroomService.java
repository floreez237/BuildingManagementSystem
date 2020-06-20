package buildingProject.services.rooms;

import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.model.rooms.BedroomEntity;
import buildingProject.repositories.room_repositories.BedroomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BedroomService {
    private final ModelMapper mapper;
    private final BedroomRepository bedroomRepository;

    public BedroomService(ModelMapper mapper, BedroomRepository bedroomRepository) {
        this.mapper = mapper;
        this.bedroomRepository = bedroomRepository;
    }

    @Transactional
    public BedroomDTO getBedroom(Long roomId) {
        return mapper.map(bedroomRepository.getOne(roomId), BedroomDTO.class);
    }

    @Transactional
    public Long save(BedroomDTO bedroomDTO) {
        return bedroomRepository.save(mapper.map(bedroomDTO, BedroomEntity.class)).getId();
    }
}
