package buildingProject.controllers;

import buildingProject.model.embeddables.AdditionalRoom;
import buildingProject.toolkit.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class AddAdditionalRoomController {

    private static TableView<AdditionalRoom> tblAdditionalRoom;

    @FXML
    private TextField tfAdditionalRoom;

    @FXML
    private TextField tfArea;

    @FXML
    void onCancel(ActionEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (!tfAdditionalRoom.getText().isEmpty() && !tfArea.getText().isEmpty()) {
            AdditionalRoom additionalRoom = new AdditionalRoom();
            additionalRoom.setName(Tools.formatString(tfAdditionalRoom.getText()));
            additionalRoom.setArea(Double.parseDouble(tfArea.getText()));
            tblAdditionalRoom.getItems().add(additionalRoom);
            onCancel(event);
        }
    }

    @FXML
    public void initialize() {
        tfArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([.]\\d*)?")) {
                    tfArea.setText(oldValue);
                }
            }
        });
    }

    public static void setTblAdditionalRoom(TableView<AdditionalRoom> tblAdditionalRoom) {
        AddAdditionalRoomController.tblAdditionalRoom = tblAdditionalRoom;
    }
}
