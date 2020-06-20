package buildingProject.controllers;

import buildingProject.dto.BuildingLevelDTO;
import buildingProject.dto.rooms.AppartmentDTO;
import buildingProject.dto.rooms.BedroomDTO;
import buildingProject.dto.rooms.RoomDTO;
import buildingProject.services.PersonService;
import buildingProject.services.rooms.AppartmentService;
import buildingProject.services.rooms.BedroomService;
import buildingProject.services.rooms.RoomService;
import buildingProject.services.rooms.StudioService;
import buildingProject.toolkit.FXMLResources;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType;

@Component
public class DisplayLevelController {
    private static BuildingLevelDTO levelDTO;
    private final FXMLResources fxmlResources;
    private final ApplicationContext applicationContext;
    private final RoomService roomService;
    private final PersonService personService;
    private final AppartmentService appartmentService;
    private final StudioService studioService;
    private final BedroomService bedroomService;

    @Value("classpath:/images/icons8-building-with-top-view-24.png")
    private Resource logo;

    @FXML
    private JFXTextField tfLevelNumber;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<RoomDTO> tblRooms;

    @FXML
    private TableColumn<RoomDTO, String> colRoomId;

    @FXML
    private TableColumn<RoomDTO, String> colRoomType;

    @FXML
    private TableColumn<RoomDTO, String> colStatus;

    @FXML
    private JFXComboBox<String> cmbRoomType;

    @FXML
    private JFXComboBox<String> cmbStatus;

    private Set<RoomDTO> completeList;


    public DisplayLevelController(FXMLResources fxmlResources, ApplicationContext applicationContext, RoomService roomService, PersonService personService,
                                  AppartmentService appartmentService, StudioService studioService, BedroomService bedroomService) {
        this.fxmlResources = fxmlResources;
        this.applicationContext = applicationContext;
        this.roomService = roomService;
        this.personService = personService;
        this.appartmentService = appartmentService;
        this.studioService = studioService;
        this.bedroomService = bedroomService;
    }

    @FXML
    void handleAddRoom(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getAddRoomResource().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().addAll(new Image(logo.getURL().toString()));
        stage.showAndWait();

    }

    @FXML
    void onDisplayPersons(ActionEvent event) throws IOException {
        RoomDTO selectedRoomDTO = tblRooms.getSelectionModel().getSelectedItem();
        if (selectedRoomDTO != null) {
            DisplayAllPersonsController.initializeCompleteList(personService.findAllByRoomId(selectedRoomDTO.getId()));
            DisplayAllPersonsController.setRoomId(selectedRoomDTO.getId());
            FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayAllPersonsResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            MainViewController.getGlobalMainPage().setCenter(loader.load());
            DisplayAllPersonsController controller = loader.getController();
            controller.setAddButtonVisible(true);
        }
    }

    @FXML
    void handleGoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayAllLevels().getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }


    @FXML
    void handleDelete(ActionEvent event) {
        RoomDTO selectedRoomDTO = tblRooms.getSelectionModel().getSelectedItem();
        if (selectedRoomDTO != null) {
            Alert deleteAlert = new Alert(AlertType.WARNING, "This will delete all associated bills,persons and contracts.\n" +
                    "Do you want to continue?", ButtonType.YES, ButtonType.NO);
            deleteAlert.resultProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (deleteAlert.getResult() == ButtonType.YES) {
                        roomService.deleteRoom(selectedRoomDTO.getId());
                        tblRooms.getItems().remove(selectedRoomDTO);
                        tblRooms.refresh();
                    }
                }
            });
            deleteAlert.showAndWait();
        }
    }

    @FXML
    void handleDisplayRoom(ActionEvent event) throws IOException {
        RoomDTO selectedRoomDTO = tblRooms.getSelectionModel().getSelectedItem();
        if (selectedRoomDTO != null) {
            String roomType = getRoomType(selectedRoomDTO);
            switch (roomType) {
                case "Appartment":
                    DisplayAppartmentController.setAppartmentDTO((appartmentService.getAppartment(selectedRoomDTO.getId())));
                    loadResource(fxmlResources.getDisplayAppartmentResource());
                    break;
                case "Studio":
                    DisplayStudioController.setStudioDTO(studioService.getStudio(selectedRoomDTO.getId()));
                    loadResource(fxmlResources.getDisplayStudioResource());
                    break;
                case "Bedroom":
                    DisplayBedRoomController.setBedroomDTO(bedroomService.getBedroom(selectedRoomDTO.getId()));
                    loadResource(fxmlResources.getDisplayBedRoomResource());
                    break;
            }
        }
    }

    private void loadResource(Resource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(resource.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    @FXML
    void handleRoomTypeFilter(ActionEvent event) {
        Set<RoomDTO> setToSearch = performStatusFilter(performSearch(completeList, tfSearch.getText()),cmbStatus.getSelectionModel().getSelectedItem());
        String choice = cmbRoomType.getSelectionModel().getSelectedItem();
        Set<RoomDTO> result = performRoomTypeFilter(setToSearch, choice);
        tblRooms.getItems().clear();
        tblRooms.getItems().addAll(result);
    }

    @FXML
    void handleStatusFilter(ActionEvent event) {
        Set<RoomDTO> setToSearch = performRoomTypeFilter(performSearch(completeList, tfSearch.getText()),cmbRoomType.getSelectionModel().getSelectedItem());
        String choice = cmbStatus.getSelectionModel().getSelectedItem();
        Set<RoomDTO> result = performStatusFilter(setToSearch, choice);
        tblRooms.getItems().clear();
        tblRooms.getItems().addAll(result);
    }

    @FXML
    void handleDisplayBills(ActionEvent event) throws IOException {
        RoomDTO selectedRoomDTO = tblRooms.getSelectionModel().getSelectedItem();
        if (selectedRoomDTO != null) {
            DisplayAllBillsController.setRoomDTO(selectedRoomDTO.getId());
            FXMLLoader loader = new FXMLLoader(fxmlResources.getDisplayAllBillsResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            MainViewController.getGlobalMainPage().setCenter(loader.load());
        }

    }

    @FXML
    public void initialize() {
        colRoomType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(getRoomType(param.getValue())));
        colStatus.setCellValueFactory(param -> {
            String status = param.getValue().isOccupied() ? "Occupied" : "Available";
            return new ReadOnlyObjectWrapper<>(status);
        });
        colRoomId.setCellValueFactory(param -> {
            String roomId = "ROOM" + param.getValue().getId();
            return new ReadOnlyObjectWrapper<>(roomId);
        });

        colRoomId.setComparator((roomId1, roomId2) -> {
            long id1 = Long.parseLong(roomId1.substring(4));
            long id2 = Long.parseLong(roomId2.substring(4));
            return Long.compare(id1, id2);
        });

        completeList = roomService.findAllInRoomsInLevel(levelDTO.getId());
        tblRooms.getItems().addAll(completeList);
        tblRooms.getSortOrder().add(colRoomId);



        tfLevelNumber.setText("" + levelDTO.getLevelNumber());

        cmbRoomType.getItems().addAll("ALL", "Appartment", "Studio", "Bedroom");
        cmbRoomType.getSelectionModel().select(0);

        cmbStatus.getItems().addAll("ALL", "Available", "Occupied");
        cmbStatus.getSelectionModel().select(0);

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            //Filter before searching


            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 final Set<RoomDTO> setToSearch = performStatusFilter(performRoomTypeFilter(completeList, cmbRoomType.getSelectionModel().getSelectedItem()),
                        cmbStatus.getSelectionModel().getSelectedItem());
                Set<RoomDTO> result = performSearch(setToSearch, newValue);
                tblRooms.getItems().clear();
                tblRooms.getItems().addAll(result);
            }
        });

    }

    private Set<RoomDTO> performSearch(Set<RoomDTO> completeList, String toSearch) {
        return completeList.stream().filter(roomDTO -> {
            String roomId = "room" + roomDTO.getId();
            return roomId.contains(toSearch.toLowerCase());
        }).collect(Collectors.toSet());
    }

    private Set<RoomDTO> performRoomTypeFilter(Set<RoomDTO> completeList, String choice) {
        if (choice.equals("ALL")) {
            return completeList;
        }
        return completeList.stream().filter(roomDTO -> getRoomType(roomDTO).equals(choice)).collect(Collectors.toSet());
    }

    private Set<RoomDTO> performStatusFilter(Set<RoomDTO> completeList, String choice) {
        if (choice.equals("ALL")) {
            return completeList;
        }
        return completeList.stream().filter(roomDTO -> {
            String status = roomDTO.isOccupied() ? "Occupied" : "Available";
            return status.equals(choice);
        }).collect(Collectors.toSet());
    }

    private String getRoomType(RoomDTO roomDTO) {
        if (roomDTO instanceof AppartmentDTO) {
            return "Appartment";
        } else if (roomDTO instanceof BedroomDTO) {
            return "Bedroom";
        } else {
            return "Studio";
        }
    }


    public static void setLevelDTO(BuildingLevelDTO levelDTO) {
        DisplayLevelController.levelDTO = levelDTO;
    }

    public static BuildingLevelDTO getLevelDTO() {
        return levelDTO;
    }
}
