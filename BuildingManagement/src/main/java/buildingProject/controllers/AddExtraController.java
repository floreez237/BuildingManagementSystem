package buildingProject.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import static buildingProject.toolkit.Tools.formatString;

@SuppressWarnings("ALL")
@Component
public class AddExtraController {

    public static JFXListView lvExtra;

    @FXML
    private TextField tfExtra;

    @FXML
    void handleCancel(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void handleSave(ActionEvent event) {
        String extra = formatString(tfExtra.getText());
        if (!extra.isEmpty()) {
            lvExtra.getItems().add(extra);
            handleCancel(event);
        }
    }

    public static void setLvExtra(JFXListView lvExtra) {
        AddExtraController.lvExtra = lvExtra;
    }
}
