package buildingProject.controllers;

import buildingProject.dto.PersonDTO;
import buildingProject.services.BuildingService;
import buildingProject.services.PersonService;
import buildingProject.toolkit.FXMLResources;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DisplayAllPersonsController {

    private final ApplicationContext applicationContext;
    private final PersonService personService;
    private final FXMLResources fxmlResources;
    private final BuildingService buildingService;

    private static Long RoomId;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<PersonDTO> tblPersons;

    @FXML
    private TableColumn<PersonDTO, String> colPersonId;

    @FXML
    private TableColumn<PersonDTO, String> colName;

    @FXML
    private TableColumn<PersonDTO, String> colPhone;

    @FXML
    private TableColumn<PersonDTO, String> colNationalId;

    @FXML
    private TableColumn<PersonDTO, String> colGender;

    @FXML
    private JFXButton btnAddPerson;

    private static Set<PersonDTO> completeList = new HashSet<>();

    public DisplayAllPersonsController(ApplicationContext applicationContext, PersonService personService, FXMLResources fxmlResources, BuildingService buildingService) {
        this.applicationContext = applicationContext;
        this.personService = personService;
        this.fxmlResources = fxmlResources;
        this.buildingService = buildingService;
    }

    @FXML
    void handleAddPerson(ActionEvent event) throws IOException {
        AddPersonController.setRoomId(RoomId);
        FXMLLoader loader = new FXMLLoader(fxmlResources.getAddPersonResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }


    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        FXMLLoader loader;
        if(RoomId == null){
            loader = new FXMLLoader(fxmlResources.getDisplayBuildingResource().getURL());
        }else{
            loader = new FXMLLoader(fxmlResources.getDisplayLevelResource().getURL());
        }
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleDisplayPerson(ActionEvent event) throws IOException {
        PersonDTO selectedDto = tblPersons.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayPersonController.setPersonDTO(selectedDto);
            FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayPersonResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            MainViewController.getGlobalMainPage().setCenter(loader.load());
        }

    }

    @FXML
    public void initialize() {
        setAddButtonVisible(RoomId != null);

        //to make sure id's are arranged in order of numbers
        colPersonId.setComparator((personId1, personId2) ->{
            long id1 = Long.parseLong(personId1.substring(4));
            long id2 = Long.parseLong(personId2.substring(4));
            return Long.compare(id1, id2);
        } );
        colPersonId.setCellValueFactory(param -> {
            String id = "PERS" + param.getValue().getId();
            return new ReadOnlyObjectWrapper<>(id);
        });
        colPersonId.setSortType(SortType.ASCENDING);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNationalId.setCellValueFactory(new PropertyValueFactory<>("nationalIdNumber"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colGender.setCellValueFactory(param -> {
            String gender;
            char cGender = param.getValue().getSex();
            gender = cGender == 'M' ? "Male" : "Female";
            return new ReadOnlyObjectWrapper<>(gender);
        });

        tblPersons.getItems().addAll(completeList);
        tblPersons.getSortOrder().add(colPersonId);

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Set<PersonDTO> result = completeList.stream().filter(personDTO -> {
                    String stringId = "pers" + personDTO.getId();
                    return stringId.contains(newValue.toLowerCase()) || personDTO.getName().toLowerCase().contains(newValue.toLowerCase());
                }).collect(Collectors.toSet());
                tblPersons.getItems().clear();
                tblPersons.getItems().addAll(result);
            }
        });
    }

    public void setAddButtonVisible(boolean visible) {
        btnAddPerson.setVisible(visible);
    }

    public static void setRoomId(Long roomId) {
        RoomId = roomId;
    }

    public static void initializeCompleteList(Collection<PersonDTO> collection) {
        completeList = new HashSet<>(collection);
    }
}
