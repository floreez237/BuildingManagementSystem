package buildingProject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String nationalIdNumber;
    private char sex;
    private String maritalStatus;
    private String phoneNumber;
    private Long roomId;
}
