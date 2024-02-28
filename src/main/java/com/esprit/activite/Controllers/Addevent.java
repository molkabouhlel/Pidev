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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Addevent {

    @FXML
    private TextField capeve;


    @FXML
    private ListView<typec> listcat;

    @FXML
    private ListView<espace> listespace;

    @FXML
    private ListView<type_ev> listev;


    @FXML
    private TextArea deseve;

    @FXML
    private TextField imeve;

    @FXML
    private TextField nomeve;

    @FXML
    private DatePicker datedebcomb;

    @FXML
    private DatePicker datefincom;
    @FXML
    private Spinner<Integer> mindeb;

    @FXML
    private Spinner<Integer> timedeb;

    @FXML
    private Spinner<Integer> timefin;

    @FXML
    private Spinner<Integer> timefinmin;
    private int  idSelectionneev;
    private int idSelectionne;
    private  int idSelectionnees;

    @FXML
    void initialize() {
        TypecService c = new TypecService();

       // List<typec> l = c.afficher();
        //comboidcat.setItems(FXCollections.observableArrayList(l));//combo box ll att mta3 type cours

        type_evService s = new type_evService();
       // List<type_ev> ev = s.afficher();
       // comboidcatev.setItems(FXCollections.observableArrayList(ev));/// ll cat ev
            EvenementService evv =new EvenementService();
        timedeb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        mindeb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        timefin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        timefinmin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        //
        List<typec> list = c.afficher();
        ObservableList<typec> items = FXCollections.observableArrayList(list);
        listcat.setItems(items);

        List<espace> lc =evv.rechercheIdEspace();
        ObservableList<espace> items2 = FXCollections.observableArrayList(lc);
        listespace.setItems(items2);

        List<type_ev> ev = s.afficher();
        ObservableList<type_ev> items3 = FXCollections.observableArrayList(ev);
        listev.setItems(items3);
        listespace.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérez l'ID de l'élément sélectionné
                idSelectionnees = newValue.getId_espace();

            }
        });
        listev.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérez l'ID de l'élément sélectionné
                idSelectionneev = newValue.getId_typeev();
              //  System.out.println("ID sélectionné : " + idSelectionne);
            }
        });
        listcat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérez l'ID de l'élément sélectionné
                idSelectionne = newValue.getIdtypec();
               // System.out.println("ID sélectionné : " + idSelectionne);
            }
        });

    }

    @FXML
    void addev(ActionEvent event) {
        EvenementService es = new EvenementService();
        if (validerChamps()) {
            //1  /// debut
            int selectedHour = timedeb.getValue();
            int selectedMinute = mindeb.getValue();
            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);

            LocalDate date = datedebcomb.getValue();
            // Créer un objet LocalDateTime en combinant la date et l'heure
            LocalDateTime localDateTime = LocalDateTime.of(date, selectedTime);
            // Convertir LocalDateTime en Timestamp
            Timestamp t = Timestamp.valueOf(localDateTime);
            ////fin
            LocalDate datef = datefincom.getValue();
            //2
            int selectedHour2 = timefin.getValue();
            int selectedMinute2 = timefinmin.getValue();
            LocalTime selectedTime2 = LocalTime.of(selectedHour2, selectedMinute2);
            LocalDateTime localDateTime2 = LocalDateTime.of(datef, selectedTime);
            Timestamp tf = Timestamp.valueOf(localDateTime2);
            ///int
            EvenementService esss = new EvenementService();
            espace ess = esss.recherchees(idSelectionnees);
            type_ev evvvv = esss.recherchetyeev(idSelectionneev);
            typec tcc = esss.recherchetypec(idSelectionne);

            int capa = Integer.parseInt(capeve.getText());
            es.ajouter(new Evenement(nomeve.getText(), deseve.getText(), imeve.getText(), t, tf, capa, ess, tcc, evvvv));
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("confirmation");
            alerte.setContentText("evenement ajoutee");
            alerte.show();
//
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();


            try {
                Parent root = FXMLLoader.load(getClass().getResource("/addevent.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }

    @FXML
    void afficher(ActionEvent event) throws IOException {
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
    void retourner(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
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


        fileChooser.setInitialDirectory(new File("C:/Users/molka/Desktop/crud_entite_1_v1/crud_entite_1_v1/src/main/resources"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String imageFile = file.toURI().toString();
            imageFile = imageFile.substring(8);
            imeve.setText(imageFile);
            System.out.println(imageFile);


        }}

    @FXML
    public boolean validerChamps() {
        if (nomeve.getText().isEmpty() || datedebcomb.getValue() == null || datefincom.getValue() == null) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        LocalDate dateActuelle = LocalDate.now();
        LocalDate dateDebut = datedebcomb.getValue();
        LocalDate dateFin = datefincom.getValue();

        if (dateDebut.isBefore(dateActuelle) || dateFin.isBefore(dateActuelle) || dateFin.isBefore(dateDebut)) {
            afficherAlerte("Les dates doivent être postérieures à la date actuelle.");
            return false;
        }

        if (!capeve.getText().matches("\\d*")) {
            afficherAlerte("Le champ 'Capeve' doit contenir uniquement des chiffres.");
            return false;
        }
        if (!nomeve.getText().matches("[a-zA-Z]+")) {
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




