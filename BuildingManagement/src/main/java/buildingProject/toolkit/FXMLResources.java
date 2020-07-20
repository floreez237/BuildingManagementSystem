package buildingProject.toolkit;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FXMLResources {

    @Value("classpath:/fxml/DisplayBill.fxml")
    private Resource displayBillResource;

    @Value("classpath:/fxml/AddBedRoom.fxml")
    private Resource addBedroomResource;

    @Value("classpath:/fxml/AddBill.fxml")
    private Resource addBillResource;

    @Value("classpath:/fxml/AddExtra.fxml")
    private Resource addExtraResource;

    @Value("classpath:/fxml/AddArea.fxml")
    private Resource addAreaResource;

    @Value("classpath:/fxml/AddAdditionalRoom.fxml")
    private Resource addAdditionalRoomResource;

    @Value("classpath:/fxml/Dashboard.fxml")
    private Resource dashboardResource;

    @Value("classpath:/fxml/AddAppartment.fxml")
    private Resource addAppartmentResource;

    @Value("classpath:/fxml/AddBuilding.fxml")
    private Resource addBuildingResource;

    @Value("classpath:/fxml/AddContract.fxml")
    private Resource addContractResource;

    @Value("classpath:/fxml/AddFurniture.fxml")
    private Resource addFurnitureResource;

    @Value("classpath:/fxml/AddPerson.fxml")
    private Resource addPersonResource;

    @Value("classpath:/fxml/AddRoom.fxml")
    private Resource addRoomResource;

    @Value("classpath:/fxml/AddStudio.fxml")
    private Resource addStudioResource;

    @Value("classpath:/fxml/BuildingManagement.fxml")
    private Resource buildingManagementResource;

    @Value("classpath:/fxml/ContractsManagement.fxml")
    private Resource contractsManagementResource;

    @Value("classpath:/fxml/DisplayAllLevels.fxml")
    private Resource displayAllLevels;

    @Value("classpath:/fxml/DisplayAllPersons.fxml")
    private Resource displayAllPersonsResource;

    @Value("classpath:/fxml/DisplayAppartment.fxml")
    private Resource displayAppartmentResource;

    @Value("classpath:/fxml/DisplayBedRoom.fxml")
    private Resource displayBedRoomResource;

    @Value("classpath:/fxml/DisplayAllBills.fxml")
    private Resource displayAllBillsResource;

    @Value("classpath:/fxml/DisplayBuilding.fxml")
    private Resource displayBuildingResource;

    @Value("classpath:/fxml/DisplayContract.fxml")
    private Resource displayContractResource;

    @Value("classpath:/fxml/DisplayLevel.fxml")
    private Resource displayLevelResource;

    @Value("classpath:/fxml/DisplayPerson.fxml")
    private Resource displayPersonResource;

    @Value("classpath:/fxml/DisplayStudio.fxml")
    private Resource displayStudioResource;

    @Value("classpath:/fxml/ExpiredContracts.fxml")
    private Resource expiredContractsResource;

    @Value("classpath:/fxml/Help.fxml")
    private Resource helpResource;

    @Value("classpath:/fxml/Login.fxml")
    private Resource loginResource;

    @Value("classpath:/fxml/MainView.fxml")
    private Resource mainViewResource;

    @Value("classpath:/fxml/RenewContract.fxml")
    private Resource renewContractResource;

    @Value("classpath:/fxml/Settings.fxml")
    private Resource settingsResource;

    @Value("classpath:/fxml/UnpaidBills.fxml")
    private Resource unpaidBillsResource;

    @Value("classpath:/fxml/UpdateBuilding.fxml")
    private Resource updateBuildingResource;

}
