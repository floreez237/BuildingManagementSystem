package buildingProject.controllers;

import buildingProject.toolkit.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class AddFurnitureController {

    private static ListView<String> lvFurniture;

    @FXML
    private TextField tfFurniture;

    @FXML
    void onCancel(ActionEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (!tfFurniture.getText().isEmpty()) {
            lvFurniture.getItems().add(Tools.formatString(tfFurniture.getText()));
            onCancel(event);
        }
    }

    public static void setLvFurniture(ListView<String> lvFurniture) {
        AddFurnitureController.lvFurniture = lvFurniture;
    }
}
