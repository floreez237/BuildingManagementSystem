package buildingProject.controllers;

import buildingProject.dto.PersonDTO;
import buildingProject.toolkit.FXMLResources;
import buildingProject.toolkit.ViewFlow;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableRow;
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
    private final FXMLResources fxmlResources;
    private final ViewFlow viewFlow;

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

    public DisplayAllPersonsController(ApplicationContext applicationContext, FXMLResources fxmlResources, ViewFlow viewFlow) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
        this.viewFlow = viewFlow;
    }

    @FXML
    void handleAddPerson(ActionEvent event) throws IOException {
        AddPersonController.setRoomId(RoomId);
        viewFlow.loadResource(fxmlResources.getDisplayAllPersonsResource(),fxmlResources.getAddPersonResource());

    }


    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        viewFlow.goBack();
    }

    @FXML
    void handleDisplayPerson(ActionEvent event) throws IOException {
        PersonDTO selectedDto = tblPersons.getSelectionModel().getSelectedItem();
        if (selectedDto != null) {
            DisplayPersonController.setPersonDTO(selectedDto);
            viewFlow.loadResource(fxmlResources.getDisplayAllPersonsResource(),fxmlResources.getDisplayPersonResource());
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
        tblPersons.setRowFactory(param -> {
            TableRow<PersonDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    PersonDTO selectedDto = param.getSelectionModel().getSelectedItem();
                    DisplayPersonController.setPersonDTO(selectedDto);
                    try {
                        viewFlow.loadResource(fxmlResources.getDisplayAllPersonsResource(), fxmlResources.getDisplayPersonResource());
                    } catch (IOException ignored) {
                    }
                }
            });
            return row;
        });
        tblPersons.getItems().addAll(completeList);
        tblPersons.getSortOrder().add(colPersonId);

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            Set<PersonDTO> result = completeList.stream().filter(personDTO -> {
                String stringId = "pers" + personDTO.getId();
                return stringId.contains(newValue.toLowerCase()) || personDTO.getName().toLowerCase().contains(newValue.toLowerCase());
            }).collect(Collectors.toSet());
            tblPersons.getItems().clear();
            tblPersons.getItems().addAll(result);
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
