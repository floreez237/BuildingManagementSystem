package buildingProject.map_configs;

import buildingProject.repositories.BuildingLevelRepository;
import buildingProject.repositories.bill_repositories.ElectricityBillRepository;
import buildingProject.repositories.bill_repositories.WaterBillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomMapperEtoDTOTest {
    public final static ModelMapper MODEL_MAPPER = CustomMapper.getInstance();

    ElectricityBillRepository electricityBillRepository;
    WaterBillRepository waterBillRepository;
    BuildingLevelRepository buildingLevelRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    void electricityBill() {

    }
}