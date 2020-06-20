package buildingProject.controllers;

import buildingProject.toolkit.MyDoubleArea;
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
public class AddAreaController {

    @FXML
    private TextField tfArea;

    private static TableView<MyDoubleArea> tblArea;

    @FXML
    void onCancel(ActionEvent event) {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (!tfArea.getText().isEmpty()) {
            long nextId = tblArea.getItems().size() + 1;
            tblArea.getItems().add(new MyDoubleArea(nextId,Double.parseDouble(tfArea.getText())));
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

    public static void setTblArea(TableView<MyDoubleArea> tblArea) {
        AddAreaController.tblArea = tblArea;
    }
}
