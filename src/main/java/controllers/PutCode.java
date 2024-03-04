package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PutCode {
    public  void initCode(int code,String email)
    {
        this.coder=code;
        this.email=email;
    }
    private int coder;

    private String email;

    @FXML
    private TextField codeconfirm;

    @FXML
    void confirmer(ActionEvent event) {
        if(Integer.parseInt(codeconfirm.getText()) != coder)
        {


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("wrong code");
            alert.show();
            Stage stage=(Stage)codeconfirm.getScene().getWindow();
            stage.close();
            Stage primaryStage=new Stage();
            Parent root= null;
            try {
                root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            primaryStage.setTitle("Virtuel Bank");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();


        }
        else
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChangePassword.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ChangePassword updateController = loader.getController();
            Stage stage1=(Stage)codeconfirm.getScene().getWindow();
            stage1.close();
            updateController.initData(email);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();



        }
    }

}