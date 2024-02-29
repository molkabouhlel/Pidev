package com.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainProggui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/MenuGen.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout produit");
        primaryStage.show();
    }
}
