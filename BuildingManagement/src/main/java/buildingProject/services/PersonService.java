package buildingProject.services;

import buildingProject.dto.ContractDTO;
import buildingProject.dto.PersonDTO;
import buildingProject.model.PersonEntity;
import buildingProject.repositories.PersonRepository;
import buildingProject.repositories.room_repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {
    private final ContractService contractService;
    private final PersonRepository personRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper mapper;

    public PersonService(ContractService contractService, PersonRepository personRepository, RoomRepository roomRepository, ModelMapper mapper) {
        this.contractService = contractService;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
        this.mapper = mapper;
    }

    public void deleteAllByRoomId(long roomId) {
        contractService.deleteByRoomId(roomId);
        personRepository.deleteAllByRoomId(roomId);

    }

    public PersonDTO findTenantByRoomId(Long roomId) {
//        return mapper.map(personRepository.findTenantByRoomId(roomId),PersonDTO.class);
        ContractDTO contractDTO = contractService.findByRoomId(roomId);
        return findPerson(contractDTO.getTenantId());
    }

    public PersonDTO findPerson(Long personId) {
        return mapper.map(personRepository.getOne(personId),PersonDTO.class);
    }

    public Long createPerson(PersonDTO personDTO) {
        PersonEntity personEntity = mapper.map(personDTO, PersonEntity.class);
        Long personId = personRepository.save(personEntity).getId();
        roomRepository.getOne(personDTO.getRoomId()).getSetOfPersons().add(personEntity);
        return personId;
    }


    public void savePerson(PersonDTO personDTO) {
        PersonEntity personEntity = mapper.map(personDTO, PersonEntity.class);
        personRepository.save(personEntity);
    }

    public Set<PersonDTO> findAllByRoomId(Long roomId) {
        return personRepository.getAllByRoomId(roomId).stream().map(personEntity -> mapper.map(personEntity,PersonDTO.class))
                .collect(Collectors.toSet());
    }

    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream().map(personEntity -> mapper.map(personEntity,PersonDTO.class)).collect(Collectors.toList());
    }

}
