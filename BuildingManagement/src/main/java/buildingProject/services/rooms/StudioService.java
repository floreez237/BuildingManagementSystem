package buildingProject.services.rooms;

import buildingProject.dto.rooms.StudioDTO;
import buildingProject.model.rooms.StudioEntity;
import buildingProject.repositories.room_repositories.StudioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudioService {
    private final ModelMapper mapper;
    private final StudioRepository studioRepository;

    public StudioService(ModelMapper mapper, StudioRepository studioRepository) {
        this.mapper = mapper;
        this.studioRepository = studioRepository;
    }

    @Transactional
    public StudioDTO getStudio(Long roomId) {
        return mapper.map(studioRepository.getOne(roomId),StudioDTO.class);
    }

    @Transactional
    public Long save(StudioDTO studioDTO) {
        return studioRepository.save(mapper.map(studioDTO, StudioEntity.class)).getId();

    }
}
