package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.type_ev;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.type_evService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class InterfacecoursController {

    @FXML
    private TextArea description_c;

    @FXML
    private TextField duree;

    @FXML
    private ComboBox<Integer> id_club;

    @FXML
    private ComboBox<Integer> id_coach;

    @FXML
    private ComboBox<typec> id_typec;


    @FXML
    private TextField image_c;

    @FXML
    private TextField nom_c;

    @FXML
    void addcours(ActionEvent event) {
        if (validerChamps()) {
            CoursService es = new CoursService();
            es.ajouter(new Cours(nom_c.getText(), description_c.getText(), image_c.getText(), Time.valueOf(duree.getText()), id_coach.getValue(), id_club.getValue(), id_typec.getValue()));
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("cours ajout");
            alerte.setContentText("cours bien ajoutee");
            alerte.show();
        }
    }

    @FXML
    void afficher(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Affichercours.fxml"));
        try{
            description_c.getScene().setRoot(loader.load());

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void retourner(ActionEvent event) {
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
            //
        }
    }

////image
@FXML
void browse(ActionEvent event) {

    FileChooser fileChooser = new FileChooser();


    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));


    fileChooser.setInitialDirectory(new File("C:/Users/molka/Desktop/crud_entite_1_v1/crud_entite_1_v1/src/main/resources"));

    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
        String imageFile = file.toURI().toString();
        imageFile = imageFile.substring(8);
        image_c.setText(imageFile);
        System.out.println(imageFile);


}}
    @FXML
    void initialize() {
        CoursService c = new CoursService();
        List<Integer> l = c.recherchecoach();
        id_coach.setItems(FXCollections.observableArrayList(l));

        List<Integer> ev = c.rechercheclub();
        id_club.setItems(FXCollections.observableArrayList(ev));/// ll cat ev
        EvenementService evv =new EvenementService();
        TypecService tc =new TypecService();
        List<typec> lc =tc.afficher();
        id_typec.setItems(FXCollections.observableArrayList(lc));

    }
    @FXML
    public boolean validerChamps() {
        if (nom_c.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        if (!nom_c.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'Noneve' doit contenir uniquement des lettres.");
            return false;
        }
        if (!validerDureeFormat(duree.getText())) {
            afficherAlerte("Le format de la durée est incorrect. Utilisez hh:mm:ss");
            return false;
        }


        return true;
    }
    private boolean validerDureeFormat(String duree) {
        // Expression régulière pour le format "hh:mm:ss"
        String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        return duree.matches(regex);
    }
    private void afficherAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de saisie");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }

}

