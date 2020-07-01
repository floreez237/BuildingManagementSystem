package buildingProject.services.rooms;

import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.model.rooms.AppartmentEntity;
import buildingProject.repositories.room_repositories.AppartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppartmentService {
   private final AppartmentRepository appartmentRepository;
    private final ModelMapper mapper;

    public AppartmentService(AppartmentRepository appartmentRepository, ModelMapper mapper) {
        this.appartmentRepository = appartmentRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AppartmentDTO getAppartment(Long roomId) {
        return mapper.map(appartmentRepository.getOne(roomId),AppartmentDTO.class);
    }

    @Transactional
    public Long save(AppartmentDTO appartmentDTO) {
        AppartmentEntity appartmentEntity = mapper.map(appartmentDTO, AppartmentEntity.class);
        return appartmentRepository.save(appartmentEntity).getId();
    }
}
