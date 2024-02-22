package com.esprit.activite.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.modeles.Maintenance_eq;
import com.esprit.activite.modeles.Rendez_vous;
import com.esprit.activite.services.CategorieService;
import com.esprit.activite.services.EquipementService;
import com.esprit.activite.services.MaintenanceService;
import com.esprit.activite.services.Rendez_vousService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModifierEquipementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField description_eq;
    @FXML
    private Button browse;

    @FXML
    private ChoiceBox<Categorie_eq> id_ceq;

    @FXML
    private ChoiceBox<Integer> id_coach;

    @FXML
    private ChoiceBox<Integer> id_espace;

    @FXML
    private ChoiceBox<Maintenance_eq> id_m;

    @FXML
    private TextField image;

    @FXML
    private Button modifiereq;

    @FXML
    private TextField nom_eq;

    @FXML
    private TextField quantite_dispo;

    @FXML
    private TextField ref_eq;
    private Equipement equipementtomodifier;
    public void initData(Equipement e){
        System.out.println("hello");
        equipementtomodifier=e;

       quantite_dispo.setText(String.valueOf(e.getQuantite_dispo()));
        nom_eq.setText(e.getNom_eq());
        ref_eq.setText(e.getRef_eq());
        description_eq.setText(e.getDescription_eq());
        image.setText(e.getImage());
        id_coach.setValue(e.getId_coach());
        id_ceq.setValue(e.getId_ceq());
        id_espace.setValue(e.getId_espace());
        id_m.setValue(e.getId_m());
        System.out.println("hello");

    }

    @FXML
    void modifier(ActionEvent event) {
        EquipementService eq = new EquipementService();
        Equipement equipement = new Equipement();

        try {
            String ref = (ref_eq.getText());
            String nom = (nom_eq.getText());
            String des = (description_eq.getText());
            String im = (image.getText());
            int q=Integer.parseInt(quantite_dispo.getText());

            // Supposons que ref_eq et id_coach sont des ComboBox
            Categorie_eq refEqSelected = id_ceq.getValue();
            int idCoachSelected = id_coach.getValue();
            int idespSelected = id_espace.getValue();
            Maintenance_eq m =id_m.getValue();

            // Configuration des propriétés de l'objet Rendez_vous
            equipement.setRef_eq(ref);
            equipement.setNom_eq(nom);
            equipement.setDescription_eq(des);
            equipement.setImage(im);
            equipement.setId_coach(idCoachSelected);
            equipement.setId_ceq(refEqSelected);
            equipement.setId_espace(idespSelected);
            equipement.setId_m(m);
            // Appel à la méthode ajouter du service
            eq.modifier(equipement);

            // Affichage d'une alerte de succès
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Rendez-vous modifier");
            alerte.setContentText("Rendez-vous bien modifier");
            alerte.show();

        } catch (NumberFormatException e) {
            // Gestion d'une exception si la conversion de l'ID échoue
            System.out.println(e.getMessage());
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Veuillez saisir un ID valide.");
            alerteErreur.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            // Gestion d'autres exceptions possibles
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Une erreur s'est produite lors de la modification du rendez-vous.");
            alerteErreur.show();
        }
    }

    @FXML
    void initialize() {

        EquipementService eqs=new EquipementService();

        CategorieService es=new CategorieService();
        List<Categorie_eq> C=es.afficher();
        System.out.println(C);
        id_ceq.setItems(FXCollections.observableArrayList(C));

        MaintenanceService ms=new MaintenanceService();
        List<Maintenance_eq> M=ms.afficher();
        System.out.println(M);
        id_m.setItems(FXCollections.observableArrayList(M));

        List<Integer> E=eqs.RecupereridEspace();
        id_espace.setItems(FXCollections.observableArrayList(E));

        List<Integer> Co=eqs.RecupereridCoach();
        id_coach.setItems(FXCollections.observableArrayList(Co));

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




    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipement.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any}
        }

    }
}


