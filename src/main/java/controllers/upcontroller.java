package controllers;

import com.esprit.Models.Coach;
import com.esprit.Models.Membre;
import com.esprit.Models.User;
import com.esprit.Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Member;

public class upcontroller {


    @FXML
    private TextField cin;

        private  User userupdate;
        private Coach coach;
    private Membre m;
    @FXML
    private Button retourup;
        @FXML
        private TextField abup;

        @FXML
        private TextField adrup;

        @FXML
        private TextField emailup;

        @FXML
        private TextField nomup;

        @FXML
        private TextField prenomup;

        @FXML
        private TextField tup;

        @FXML
        private Button upp;

        @FXML
        private ComboBox<User> userup;

    public void initDataU(User E) {//afficher les valeurs a partir de textfield
UserServices es =new UserServices();

        userupdate = E;
       // cin.setText(String.valueOf(E.getCin()));

        nomup.setText(E.getNom());
        prenomup.setText(E.getPrénom());
        emailup.setText(E.getEmail());
        tup.setText(String.valueOf(E.getNumT()));
        if((E.getRole())!=null && (E.getRole()).equals("Coach")) {
        System.out.println("test");
        adrup.setText( "dddd");
        System.out.println(((Coach) E).getAdresse());}
    if((E.getRole()!=null) && (E.getRole()).equals("Membre")) {
        abup.setText(String.valueOf(((Membre) E).getAbonnement()));
    }
    }
    /*public void initDataC(Coach E) {
        userupdate = E;
        cin.setText(String.valueOf(E.getCin()));
        nomup.setText(E.getNom());
        prenomup.setText(E.getPrénom());
        emailup.setText(E.getEmail());
        tup.setText(String.valueOf(E.getNumT()));
        adrup.setText(E.getAdresse());

    }*/



    @FXML
        void update_user(ActionEvent event) throws IOException {

            UserServices us = new UserServices();
             if ((userupdate.getRole().equals("Admin"))){
            //int numberOfQuestions = Integer.parseInt(nbr_questionstf.getText());
            userupdate.setCin(Integer.parseInt(cin.getText()));
            userupdate.setNom(nomup.getText());
            userupdate.setPrénom(prenomup.getText());
            userupdate.setEmail(emailup.getText());
            userupdate.setNumT(Integer.parseInt(tup.getText()));
                 us.update(userupdate);
             }
else if (userupdate.getRole().equals("Coach")) {
    coach.setAdresse(adrup.getText());
    coach.setCin(Integer.parseInt(cin.getText()));
    coach.setNom(nomup.getText());
    coach.setPrénom(prenomup.getText());
    coach.setEmail(emailup.getText());
    coach.setNumT(Integer.parseInt(tup.getText()));
                 us.update(userupdate);

}
else if (userupdate.getRole().equals("Membre")){
            m.setAbonnement(abup.getText());
            m.setCin(Integer.parseInt(cin.getText()));
            m.setNom(nomup.getText());
            m.setPrénom(prenomup.getText());
            m.setEmail(emailup.getText());
            m.setNumT(Integer.parseInt(tup.getText()));
                 us.update(userupdate);
        }
          // m.setAbonnement(abup.getText());

           // us.update(userupdate);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modifier User");
            alert.setContentText("Modification avec succces");
            alert.showAndWait();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showData.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
         }



    @FXML
    void retourup(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any

        }

    }

}










