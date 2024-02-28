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
    private ComboBox<type_ev> comboidcatevup;

    @FXML
    private ComboBox<typec> comboidcatup;
   // @FXML
   // private ComboBox<espace> comboidesup;

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
    @FXML
    private ListView<espace> comboidesup;
    private Evenement tomodify;

    public void initData(Evenement c) {
        tomodify =c;
        idev.setText(String.valueOf(c.getId_ev()));
        System.out.println(c);
        nomeveup.setText(c.getNom_ev());
        deseveup.setText(c.getDescription_ev());
        imeveup.setText(c.getImage_ev());
        comboidcatup.setValue(c.getId_typec());
        comboidcatevup.setValue(c.getId_type_ev());
        comboidesup.getItems().clear(); // Effacez les anciennes données
        comboidesup.getItems().addAll(c.getId_espace());
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
        TypecService ct = new TypecService();
        List<typec> l = ct.afficher();
        comboidcatup.setItems(FXCollections.observableArrayList(l));//combo box ll att mta3 type cours

        type_evService s = new type_evService();
        List<type_ev> ev = s.afficher();
        comboidcatevup.setItems(FXCollections.observableArrayList(ev));/// ll cat ev

        EvenementService evs = new EvenementService();
        List<espace> lc = evs.rechercheIdEspace();

        // Utilisez FXCllections.observableArrayList pour créer une ObservableList d'objets espace
        ObservableList<espace> espaceList = FXCollections.observableArrayList(lc);
        comboidesup.setItems(espaceList);

        timedebup.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        timedebupmin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        timefinup.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        timefinminup.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

    }

    @FXML
    void modifier(ActionEvent event) {
        if (validerChamps()){
            EvenementService es = new EvenementService();
        Evenement e =new Evenement();
        //1
        LocalDate date = datedebcombo.getValue();
        int selectedHour = timedebup.getValue();
        int selectedMinute = timedebupmin.getValue();
        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
        LocalDateTime localDateTime = LocalDateTime.of(date, selectedTime);
        // Convertir LocalDateTime en Timestamp
        Timestamp t = Timestamp.valueOf(localDateTime);
          e.setDate_debut(t);
          //2
        LocalDate datef = datefincombo.getValue();
        int selectedHour2 = timedebup.getValue();
        int selectedMinute2 = timedebupmin.getValue();
        LocalTime selectedTime2 = LocalTime.of(selectedHour2, selectedMinute2);
        LocalDateTime localDateTime2 = LocalDateTime.of(datef, selectedTime2);
        // Convertir LocalDateTime en Timestamp
        Timestamp tf = Timestamp.valueOf(localDateTime2);
        e.setDate_fin(tf);
        ///int
        int capa = Integer.parseInt(capeveup.getText());
        int ice= Integer.parseInt(idev.getText());
        String nomev =nomeveup.getText();
        String des= deseveup.getText();
        String img=imeveup.getText();
        e.setImage_ev(img);
        e.setCapacite_max(capa);
        e.setNom_ev(nomev);
        e.setDescription_ev(des);
        List<espace> selectedEspaces = comboidesup.getSelectionModel().getSelectedItems();
        for (espace selectedEspace : selectedEspaces) {
            e.setId_espace(selectedEspace);
        }
        e.setId_type_ev(comboidcatevup.getValue());
        e.setId_typec(comboidcatup.getValue());
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
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherevent.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Image file extensions
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        // Set initial directory
        fileChooser.setInitialDirectory(new File("C:/Users/molka/Desktop/crud_entite_1_v1/crud_entite_1_v1/src/main/resources"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
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

