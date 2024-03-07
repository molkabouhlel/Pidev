package com.esprit.equipement.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.equipement.modeles.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import com.esprit.equipement.services.Categorie_eqService;
import com.esprit.equipement.services.EquipementService;
import com.esprit.equipement.services.MaintenanceService;
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
    private ChoiceBox<Integer> id_espace;

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
        Categorie_eqService cs = new Categorie_eqService();
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
    try {
        boolean containsBadWords = BadWordsChecker.checkForBadWords(description_eq.getText());
        if (containsBadWords) {
            Alert alerteA = new Alert(Alert.AlertType.ERROR);
            alerteA.setTitle("bad words");
            alerteA.setContentText("bad words");
            alerteA.show();
        } else {
    es.ajouter(new Equipement(ref_eq.getText(), nom_eq.getText(), description_eq.getText(), Integer.parseInt(quantite_dispo.getText()), id_espace.getValue(), eq, mq, image.getText()));
    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
    alerte.setTitle("EQ ajout");
    alerte.setContentText("EQ bien ajoutee");
    alerte.show();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
    Parent root = loader.load();
    Stage currentStage = (Stage) nom_eq.getScene().getWindow();
    currentStage.setScene(new Scene(root));
        }
    } catch (IOException e) {
        System.err.println("An error occurred while checking for bad words: " + e.getMessage());
    }
}




    }

    @FXML
    void initialize() {
        EquipementService eqs=new EquipementService();

        Categorie_eqService es=new Categorie_eqService();
       // List<Categorie_eq> C=es.afficher();
        List<String> nomcatList = eqs.listcat();
        id_ceq.setItems(FXCollections.observableArrayList(nomcatList));
        System.out.println(nomcatList);
       // id_ceq.setItems(FXCollections.observableArrayList(nomcatList));

        MaintenanceService ms=new MaintenanceService();
        List<etat_m> M=eqs.listmnom();
        System.out.println(M);
        id_m.setItems(FXCollections.observableArrayList(M));

        List<Integer> E=eqs.RecupereridEspace();
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
            Parent root = FXMLLoader.load(getClass().getResource("/Principale.fxml"));
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
        // Sélectionnez un fichier à partir de la boîte de dialogue
        FileChooser fileChooser = new FileChooser();

        // Ajoutez un filtre pour les fichiers d'image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        // Définissez le répertoire initial
        fileChooser.setInitialDirectory(new File("C:/Users/Asus/OneDrive/Desktop/crud_entite_1_v1/Pidev/src/main/resources/Image"));

        // Affichez la boîte de dialogue et obtenez le fichier sélectionné
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Obtenez le chemin absolu du fichier
                String imageFile = file.toURI().toURL().toString();
                imageFile = imageFile.substring(8);

                // Mettez à jour le texte ou l'image comme nécessaire
                // Par exemple, si vous avez un objet ImageView nommé "imageView" dans votre FXML :
                // imageView.setImage(new Image(imageFile));

                // Si vous mettez à jour un composant texte (par exemple, un Label ou un Text)
                image.setText(imageFile);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Gérez l'exception en conséquence (par exemple, affichez un message d'erreur)
            }
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


