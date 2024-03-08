package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePassword {
    public String hashPassword(String password) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");


            md.update(password.getBytes());


            byte[] bytes = md.digest();


            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    private String email;
    public void initData(String email) {
        this.email=email;
    }


    @FXML
    private PasswordField newpassword;

    @FXML
    void updatepassword(ActionEvent event) {

        UserServices us=new UserServices();
        User u = new User();
        u=us.findemail(email);
        System.out.println(u);
        try {
            u.setMdp(hashPassword(newpassword.getText()));
            us.updatepassword(u);
            Stage stage = (Stage) newpassword.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = null;
            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            primaryStage.setTitle("Connectez");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }






    }


}