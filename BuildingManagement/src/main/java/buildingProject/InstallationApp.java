package buildingProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class InstallationApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(BootStrap.class.getResource("/fxml/Installation.fxml"));
        try {
            Parent root = loader.load();
            Scene scene1 = new Scene(root);
//            stage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image(BootStrap.class.getResourceAsStream("/images/icons8-building-with-top-view-24.png")));
            primaryStage.setScene(scene1);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
