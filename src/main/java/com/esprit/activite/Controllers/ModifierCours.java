package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.modeles.club;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.TypecService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.List;

import javafx.scene.control.ComboBox;
public class ModifierCours {

    @FXML
    private TextArea desc;

    @FXML
    private TextField drc;


    @FXML
    private TextField idcou;


    @FXML
    private TextField imc;

    @FXML
    private TextField nomc;

    @FXML
    private ComboBox<Integer> idc;

    @FXML
    private ComboBox<String> idcat;

    @FXML
    private ComboBox<String> idcl;

    ////
    private Cours CoursToModify;
    public void initData(Cours c) {
        idcou.setText(String.valueOf(c.getId()));
        nomc.setText(c.getNom());
        desc.setText(c.getDescription());
        imc.setText(c.getImagec());
        drc.setText(String.valueOf(c.getDuree()));
        imc.setText(c.getImagec());
        idc.setValue(c.getIdcoach());
        idcl.setValue(c.getIdclub().getNom_club());
       idcat.setValue(c.getId_typec().getTypecours());
        //idcl.setValue(c.getIdclub());
        // selectedTypec = new typec();
       // selectedTypec.setTypecours(idcat.getValue());
        //idcat.setValue(c.getId_typec().getTypecours());

    }


    @FXML
    void reafficherc(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichercours.fxml"));
        try {
            desc.getScene().setRoot(loader.load());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void initialize() {
        CoursService c = new CoursService();
        List<Integer> l = c.recherchecoach();
        idc.setItems(FXCollections.observableArrayList(l));

        List<String> ev = c.listclub();
        idcl.setItems(FXCollections.observableArrayList(ev));/// ll cat ev
        EvenementService evv =new EvenementService();
        /*
        TypecService tc =new TypecService();
        List<typec> lc =tc.afficher();
        idcat.setItems(FXCollections.observableArrayList(lc));*/

        List<String> nomCoursList = c.listcategorie();
        idcat.setItems(FXCollections.observableArrayList(nomCoursList));


    }

    @FXML
    void updatec(ActionEvent event) throws IOException {
        CoursService es = new CoursService();
        Cours c = new Cours();

        int idCours = Integer.parseInt(idcou.getText());
        String nom = nomc.getText();
        String description = desc.getText();
        String image = imc.getText();
        Time duree = Time.valueOf(drc.getText());
        //
        String catSelectionne = idcat.getValue();
        String clSelectionne = idcl.getValue();
            // Rechercher la catégorie par son nom
            typec selectedTypec = es.rechercherCatParNom(catSelectionne);
        club selectedcl = es.rechercherClubparnom(clSelectionne);
        c.setId(idCours);
        c.setNom(nom);
        c.setDuree(duree);
        c.setImagec(image);
        c.setDescription(description);
        c.setId_typec(selectedTypec);
        c.setIdclub(selectedcl);
        c.setIdcoach(idc.getValue());

        es.modifier(c);
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("cours modifier");
        alerte.setContentText("cours bien modifier");
        alerte.show();

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
            imc.setText(imageFile);
            System.out.println(imageFile);
       }}

    @FXML
    public boolean validerChamps() {
        if (nomc.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        if (!nomc.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'Noneve' doit contenir uniquement des lettres.");
            return false;
        }
        if (!validerDureeFormat(drc.getText())) {
            afficherAlerte("Le format de la durée est incorrect. Utilisez hh:mm:ss");
            return false;
        }


        return true;
    }
    private boolean validerDureeFormat(String duree) {

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

