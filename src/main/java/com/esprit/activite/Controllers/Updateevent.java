package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.type_evService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Updateevent {

    @FXML
    private TextField capeveup;

    @FXML
    private ComboBox<String> comboidcatevup;

    @FXML
    private ComboBox<String> comboidcatup;
    @FXML
    private ComboBox<String> listeespace;

    @FXML
    private DatePicker datedebcombo;

    @FXML
    private DatePicker datefincombo;

    @FXML
    private TextArea deseveup;

    @FXML
    private TextField imeveup;

    @FXML
    private TextField idev;

    @FXML
    private TextField nomeveup;

    @FXML
    private Spinner<Integer> timedebup;

    @FXML
    private Spinner<Integer> timedebupmin;

    @FXML
    private Spinner<Integer> timefinminup;

    @FXML
    private Spinner<Integer> timefinup;

    private Evenement tomodify;

    public void initData(Evenement c) {
        tomodify =c;
        idev.setText(String.valueOf(c.getId_ev()));
        System.out.println(c);
        nomeveup.setText(c.getNom_ev());
        deseveup.setText(c.getDescription_ev());
        imeveup.setText(c.getImage_ev());

        comboidcatup.setValue(c.getId_typec().getTypecours());
        comboidcatevup.setValue(c.getId_type_ev().getType_ev());
        listeespace.setValue(c.getId_espace().getNom_espace());

        capeveup.setText(String.valueOf(c.getCapacite_max()));
        //
        timedebup.getValueFactory().setValue(c.getDate_debut().toLocalDateTime().getHour());
        timedebupmin.getValueFactory().setValue(c.getDate_debut().toLocalDateTime().getMinute());

        // Remplir les valeurs des spinners avec les heures et minutes de la date de fin
        timefinup.getValueFactory().setValue(c.getDate_fin().toLocalDateTime().getHour());
        timefinminup.getValueFactory().setValue(c.getDate_fin().toLocalDateTime().getMinute());

        // Remplir les DatePicker avec les dates de début et de fin
        datedebcombo.setValue(c.getDate_debut().toLocalDateTime().toLocalDate());
        datefincombo.setValue(c.getDate_fin().toLocalDateTime().toLocalDate());
    }
    @FXML
    void initialize() {
        CoursService c = new CoursService();

        EvenementService ev=new EvenementService();

        List<String> l = c.listcategorie();
        comboidcatup.setItems(FXCollections.observableArrayList(l));//combo box ll att mta3 type cours

        List<String> cateve = ev.listcategorieevent();
        comboidcatevup.setItems(FXCollections.observableArrayList(cateve));/// ll cat ev

        List<String> le = ev.listespace();
        listeespace.setItems(FXCollections.observableArrayList(le));


        timedebup.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        timedebupmin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        timefinup.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        timefinminup.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

    }

    @FXML
    void modifier(ActionEvent event) {
        if (validerChamps()){
            EvenementService es = new EvenementService();
        Evenement e =new Evenement();
        CoursService cours =new CoursService();
        //1
        LocalDate date = datedebcombo.getValue();
        int selectedHour = timedebup.getValue();
        int selectedMinute = timedebupmin.getValue();
        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
        LocalDateTime localDateTime = LocalDateTime.of(date, selectedTime);

        Timestamp t = Timestamp.valueOf(localDateTime);
        //setdate
          e.setDate_debut(t);
          //2
        LocalDate datef = datefincombo.getValue();
        int selectedHour2 = timedebup.getValue();
        int selectedMinute2 = timedebupmin.getValue();
        LocalTime selectedTime2 = LocalTime.of(selectedHour2, selectedMinute2);
        LocalDateTime localDateTime2 = LocalDateTime.of(datef, selectedTime2);
        Timestamp tf = Timestamp.valueOf(localDateTime2);
        //setdate
        e.setDate_fin(tf);
        ///int
        int capa = Integer.parseInt(capeveup.getText());
        int ice= Integer.parseInt(idev.getText());
        String nomev =nomeveup.getText();
        String des= deseveup.getText();
        String img=imeveup.getText();
            String catCSelectionne = comboidcatup.getValue();
            String CatEveSelectionne = comboidcatevup.getValue();
            String espaceSelectionne = listeespace.getValue();

            // Rechercher la catégorie par son nom
            typec selectedTypec = cours.rechercherCatParNom(catCSelectionne);
            Espace selectedespaceid = es.rechercherespaceparnom(espaceSelectionne);
            type_ev catEveid = es.rechercherCateveParNom(CatEveSelectionne);

        //set
        e.setImage_ev(img);
        e.setCapacite_max(capa);
        e.setNom_ev(nomev);
        e.setDescription_ev(des);
      e.setId_type_ev(catEveid);
      e.setId_typec(selectedTypec);
      e.setId_espace(selectedespaceid);
        e.setId_ev(ice);

//
        es.modifier(e);
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle(" confirmation");
        alerte.setContentText("event bien modifier");
        alerte.show();

    }}
    @FXML
    void retourner(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherevent.fxml"));
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
            imeveup.setText(imageFile);
            System.out.println(imageFile);
        }}
    @FXML
    public boolean validerChamps() {
        if (nomeveup.getText().isEmpty() || datedebcombo.getValue() == null || datefincombo.getValue() == null) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        LocalDate dateActuelle = LocalDate.now();
        LocalDate dateDebut = datedebcombo.getValue();
        LocalDate dateFin = datefincombo.getValue();

        if (dateDebut.isBefore(dateActuelle) || dateFin.isBefore(dateActuelle) || dateFin.isBefore(dateDebut)) {
            afficherAlerte("Les dates doivent être postérieures à la date actuelle.");
            return false;
        }

        if (!capeveup.getText().matches("\\d*")) {
            afficherAlerte("Le champ 'Capeve' doit contenir uniquement des chiffres.");
            return false;
        }
        if (!nomeveup.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'Noneve' doit contenir uniquement des lettres.");
            return false;
        }

        return true;
    }
    private void afficherAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de saisie");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }
    }

