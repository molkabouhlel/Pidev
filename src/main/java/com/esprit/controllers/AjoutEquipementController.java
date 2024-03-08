package com.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.models.*;
import com.esprit.services.EvenementService;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import com.esprit.services.CategorieService;
import com.esprit.services.EquipementService;
import com.esprit.services.MaintenanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AjoutEquipementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ajoutereq;

    @FXML
    private TextField description_eq;

    @FXML
    private ChoiceBox<String> id_ceq;
  //  @FXML
//    private ChoiceBox<Integer> id_coach;


    @FXML
    private ChoiceBox<String> id_espace;

    @FXML
    private ChoiceBox<etat_m> id_m;
    @FXML
    private Button browse;


    @FXML
    private TextField image;

    @FXML
    private TextField nom_eq;

    @FXML
    private TextField quantite_dispo;

    @FXML
    private TextField ref_eq;

    @FXML
    void ajouerEquipement(ActionEvent event) throws IOException {
        EquipementService es = new EquipementService();
        CategorieService cs = new CategorieService();
        MaintenanceService ms = new MaintenanceService();
        if (!estNumerique(quantite_dispo.getText())) {
     //alerte
            Alert alerteA = new Alert(Alert.AlertType.ERROR);
            alerteA.setTitle("EQ ajout");
            alerteA.setContentText("qt non valide");
            alerteA.show();}

if (controlSaisie(ref_eq) && controlSaisie(nom_eq) && controlSaisie(description_eq) && controlSaisie(quantite_dispo) && estNumerique(quantite_dispo.getText()) && controlSaisie(image)) {
    String cat=id_ceq.getValue();
    Categorie_eq eq=es.rechercherCatParNom(cat);
    etat_m m=id_m.getValue();
    Maintenance_eq mq=es.rechercherMParNom(m);
    EvenementService e=new EvenementService();
    String espp= id_espace.getValue();
    Espace  esppp=e.rechercherespaceparnom(espp);


    try {
        boolean containsBadWords = BadWordsChecker.checkForBadWords(description_eq.getText());
        if (containsBadWords) {
            Alert alerteA = new Alert(Alert.AlertType.ERROR);
            alerteA.setTitle("bad words");
            alerteA.setContentText("bad words");
            alerteA.show();
        } else {
    es.ajouter(new Equipement(ref_eq.getText(), nom_eq.getText(), description_eq.getText(), Integer.parseInt(quantite_dispo.getText()),esppp, eq, mq, image.getText()));
    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
    alerte.setTitle("EQ ajout");
    alerte.setContentText("EQ bien ajoutee");
    alerte.show();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
    Parent root = loader.load();
    Stage currentStage = (Stage) nom_eq.getScene().getWindow();
    currentStage.setScene(new Scene(root));
        }
    } catch (IOException ex) {
        System.err.println("An error occurred while checking for bad words: " + ex.getMessage());
    }
}




    }

    @FXML
    void initialize() {
        EquipementService eqs=new EquipementService();

        CategorieService es=new CategorieService();
       // List<Categorie_eq> C=es.afficher();
        List<String> nomcatList = eqs.listcat();
        id_ceq.setItems(FXCollections.observableArrayList(nomcatList));
        System.out.println(nomcatList);
       // id_ceq.setItems(FXCollections.observableArrayList(nomcatList));

        MaintenanceService ms=new MaintenanceService();
        List<etat_m> M=eqs.listmnom();
        System.out.println(M);
        id_m.setItems(FXCollections.observableArrayList(M));
         EvenementService ev=new EvenementService();

        List<String> E=ev.listespace();
        id_espace.setItems(FXCollections.observableArrayList(E));
       // List<Integer> Co=eqs.RecupereridCoach();
        //id_coach.setItems(FXCollections.observableArrayList(Co));

    }

    private final EquipementService es = new EquipementService();
    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Principale_eq.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }}
    @FXML
    void afficher(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipement.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }


    }

    @FXML
    void browse(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));


        //  fileChooser.setInitialDirectory(new File("C:/xampp/htdocs/Image"));

        File file = fileChooser.showOpenDialog(null);
        String xamppHtdocsPath = "C:/xampp/htdocs/Image/";
        File destinationFile = new File(xamppHtdocsPath + file.getName());
        try {
            // Copy the selected file to the htdocs directory
            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully to: " + destinationFile.getAbsolutePath());
        }catch (IOException e){
            System.out.println("Error copying file: " + e.getMessage());
        }

        if (destinationFile != null) {
            String imageFile = file.toURI().toString();
            imageFile = imageFile.substring(8);
            image.setText(imageFile);
            System.out.println(imageFile);


        }
    }


    public boolean controlSaisie (TextField field){
        if (field.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Form not full");
            alert.setContentText("you must fill all the Form");
            alert.show();
            return false;
        }
        return true;
    }


        public static boolean estNumerique(String input) {

        return input.matches("\\d+");
        }


    }


