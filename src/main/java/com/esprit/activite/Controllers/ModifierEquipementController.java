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
    private TextField id_eq;
    @FXML
    private TextField description_eq;
    @FXML
    private Button browse;

    @FXML
    private ChoiceBox<String> id_ceq;
  //  @FXML
   // private ChoiceBox<Integer> id_coach;



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
    public void initData(Equipement e) {
        System.out.println("hello");
        quantite_dispo.setText(String.valueOf(e.getQuantite_dispo()));
        id_eq.setText(String.valueOf(e.getId_eq()));
        System.out.println(e.getId_eq());
        nom_eq.setText(e.getNom_eq());
        ref_eq.setText(e.getRef_eq());
        description_eq.setText(e.getDescription_eq());
        image.setText(e.getImage());
        // Vérifier si getId_ceq() n'est pas null avant d'accéder à getType_ceq()
        if (e.getId_ceq() != null) {
            id_ceq.setValue(e.getId_ceq().getType_ceq());
        } else {
            // Gérer le cas où getId_ceq() est null, par exemple, en définissant une valeur par défaut
            id_ceq.setValue("Valeur par défaut");
        }
        //id_eq.setCache(e.getId_eq())
        id_espace.setValue(e.getId_espace());
        id_m.setValue(e.getId_m());
        System.out.println("hello");
    }


    @FXML
    void modifier(ActionEvent event) {
        EquipementService eq = new EquipementService();
        Equipement equipement = new Equipement();

        try {
            int id_eqq =Integer.parseInt(id_eq.getText());
            String ref = (ref_eq.getText());
            String nom = (nom_eq.getText());
            String des = (description_eq.getText());
            String im = (image.getText());
            int q=Integer.parseInt(quantite_dispo.getText());


            String cat=id_ceq.getValue();
            Categorie_eq c=eq.rechercherCatParNom(cat);

         //   Categorie_eq refEqSelected = id_ceq.getValue();
          // int idcoachSelected = id_coach.getValue();

            int idespSelected = id_espace.getValue();
            Maintenance_eq m =id_m.getValue();

            equipement.setId_eq(id_eqq);
            equipement.setRef_eq(ref);
            equipement.setNom_eq(nom);
            equipement.setDescription_eq(des);
            equipement.setImage(im);
            equipement.setQuantite_dispo(q);
            equipement.setId_ceq(c);
           // equipement.setId_coach(idcoachSelected);
            equipement.setId_espace(idespSelected);
            equipement.setId_m(m);
/////////////////
            if (!estNumerique(quantite_dispo.getText())) {
                //alerte
                Alert alerteA = new Alert(Alert.AlertType.ERROR);
                alerteA.setTitle("EQ modif");
                alerteA.setContentText("qt non valide");
                alerteA.show();}

            //////////
            if (controlSaisie(ref_eq) && controlSaisie(nom_eq) && controlSaisie(description_eq) && controlSaisie(quantite_dispo) && estNumerique(quantite_dispo.getText()) && controlSaisie(image)) {
                eq.modifier(equipement);
            }

            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("eq modifier");
            alerte.setContentText("eq bien modifier");
            alerte.show();

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Veuillez saisir un ID valide.");
            alerteErreur.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Une erreur s'est produite lors de la modification de l'eq.");
            alerteErreur.show();
        }
    }

    @FXML
    void initialize() {

        EquipementService eqs=new EquipementService();

        CategorieService es=new CategorieService();
        List<String> C=eqs.listcat();
        System.out.println(C);
        id_ceq.setItems(FXCollections.observableArrayList(C));

        MaintenanceService ms=new MaintenanceService();
        List<Maintenance_eq> M=ms.afficher();
        System.out.println(M);
        id_m.setItems(FXCollections.observableArrayList(M));
        // List<Integer> Co=eqs.RecupereridCoach();
       // id_coach.setItems(FXCollections.observableArrayList(Co));
        List<Integer> E=eqs.RecupereridEspace();
        id_espace.setItems(FXCollections.observableArrayList(E));


    }
    @FXML
    void browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        fileChooser.setInitialDirectory(new File("C:/Users/Asus/OneDrive/Desktop/crud_entite_1_v1/Pidev/src/main/resources/Image"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String imageFile = file.toURI().toURL().toString();
                imageFile = imageFile.substring(8);


                image.setText(imageFile);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }




    @FXML
    void refresh(ActionEvent event) {
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


