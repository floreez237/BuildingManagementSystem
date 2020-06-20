package buildingProject.controllers;

import buildingProject.MainApp;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author YASMINE
 */
@Component
public class LoginController implements Initializable {
    // Do not delete this anchor  i use it for css
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lbl;

    @FXML
    private JFXCheckBox chkPassword;

    @FXML
    private TextField tfPassword;

    @FXML
    private PasswordField pfPassword;
    //retrieve the password
    private String password = "yasmina";

    @FXML
    void handleExit(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        handlePassword(event);
    }

    private void handlePassword(Event event) throws IOException {
        if (pfPassword.getText().equals(getPw())) {
            lbl.setText("Successful login");
            lbl.setTextFill(Color.GREEN);

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            Parent root1 = FXMLLoader.load(getClass().getResource("/resources/fxml/MainView.fxml"));
            Stage stage1 = new Stage();
            Scene scene = new Scene(root1);
            stage1.getIcons().add(new Image(MainApp.class.getResourceAsStream("/resources/images/icons8-building-with-top-view-24.png")));
            stage1.setTitle("BGESTIO");
            stage1.setScene(scene);
            stage1.show();
        } else if (pfPassword.getText().equals("")) {
            lbl.setText("Please enter your password");
            lbl.setTextFill(Color.RED);
        } else {
            lbl.setText("Sorry! the password entered is incorrect.");
            lbl.setTextFill(Color.RED);
        }
    }

    @FXML
    void handleLogin2(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            handlePassword(event);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfPassword.setVisible(false);
        chkPassword.selectedProperty().addListener((ObservableValue<? extends Boolean> Observable, Boolean oldValue, Boolean newValue) -> {
            if (chkPassword.isSelected()) {
                tfPassword.setText(pfPassword.getText());
                tfPassword.setVisible(true);
                pfPassword.setVisible(false);

            } else {
                pfPassword.setText(tfPassword.getText());
                pfPassword.setVisible(true);
                tfPassword.setVisible(false);
            }
        });
    }

    public String getPw() {
        return password;
    }

    public void setPw(String pw) {
        this.password = pw;
    }

}
