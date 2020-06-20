/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author YASMINE
 */
@Component
public class HelpController implements Initializable {


    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final WebEngine web = webView.getEngine();
        String urlWeb = "https://byaschou.wixsite.com/website";
        web.load(urlWeb);
    }

}
