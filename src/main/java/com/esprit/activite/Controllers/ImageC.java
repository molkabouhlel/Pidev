package com.esprit.activite.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ImageC {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgview;

    @FXML
    void initialize() {
        assert imgview != null : "fx:id=\"imgview\" was not injected: check your FXML file 'imageC.fxml'.";

    }

}