package buildingProject.controllers;

import buildingProject.security.PasswordUtils;
import buildingProject.toolkit.FXMLResources;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
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
    private final ApplicationContext applicationContext;
    private final FXMLResources fxmlResources;
    @Value("classpath:/images/icons8-building-with-top-view-24.png")
    private Resource logo;

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

    public LoginController(ApplicationContext applicationContext, FXMLResources fxmlResources) {
        this.applicationContext = applicationContext;
        this.fxmlResources = fxmlResources;
    }


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
        if (PasswordUtils.checkPassword(pfPassword.getText())) {
            lbl.setText("Successful login");
            lbl.setTextFill(Color.GREEN);

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(fxmlResources.getMainViewResource().getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Stage stage1 = new Stage();
            Parent root1 = loader.load();
            Scene scene = new Scene(root1);
            stage1.getIcons().add(new Image(logo.getURL().toString()));
            stage1.setTitle("BGESTIO");
            stage1.setScene(scene);
            stage1.setResizable(false);
            stage1.show();
        } else if (pfPassword.getText().isEmpty()) {
            lbl.setText("Please enter your password");
            lbl.setTextFill(Color.RED);
        } else {
            lbl.setText("Sorry! the password entered is incorrect.");
            lbl.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfPassword.setVisible(false);
        tfPassword.textProperty().bindBidirectional(pfPassword.textProperty());
        chkPassword.selectedProperty().addListener((ObservableValue<? extends Boolean> Observable, Boolean oldValue, Boolean newValue) -> {
            if (chkPassword.isSelected()) {
                tfPassword.setVisible(true);
                pfPassword.setVisible(false);

            } else {
                pfPassword.setVisible(true);
                tfPassword.setVisible(false);
            }
        });
    }

}
