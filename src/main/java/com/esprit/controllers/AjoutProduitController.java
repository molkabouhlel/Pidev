package com.esprit.controllers;

import com.esprit.models.Categorie;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import com.esprit.models.Produit;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AjoutProduitController implements Initializable {
    @FXML
    private ComboBox<Categorie> id_categorie;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfMarque;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfQuantite;

    @FXML
    private TextField tfcategorie;

    @FXML
    private TextField tfsku;

    @FXML
    private TextField tfurl;
   /* void addProduit(ActionEvent event) throws IOException {
        ProduitService ps = new ProduitService();
        float tf_Prix = Integer.parseInt(this.tfPrix.getText());
        int tf_Quantite = Integer.parseInt(this.tfQuantite.getText());
        String description = tfDescription.getText();
        String marque = tfMarque.getText();
        String url = tfurl.getText();
        String sku = tfsku.getText();
        ps.ajouter(new Produit(tfDescription.getText(), tf_Prix, tf_Quantite, tfMarque.getText(), id_categorie.getValue(), tfurl.getText(), tfsku.getText()));
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setTitle("produit ajout");
        alerte.setContentText("produit bien ajoute");
        alerte.show();
    }*/
    @FXML
    void addProduit(ActionEvent event) throws IOException {
        ProduitService ps = new ProduitService();
        float tf_Prix = Integer.parseInt(this.tfPrix.getText());
        int tf_Quantite = Integer.parseInt(this.tfQuantite.getText());
        String description = tfDescription.getText().trim();
        String marque = tfMarque.getText().trim();
        String url = tfurl.getText().trim();
        String sku = tfsku.getText().trim();
        Categorie categorie = id_categorie.getValue();
        // String titreQuestion = tfQuestionTitre.getText().trim(); // Trim leading/trailing whitespaces

        if (description.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Description vide!");
            alertVide.setContentText("Veuillez saisir la description du produit.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        if (marque.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Marque vide!");
            alertVide.setContentText("Veuillez saisir la marque du produit.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        if (url.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("url vide!");
            alertVide.setContentText("Veuillez saisir l url du produit.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        if (sku.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("sku vide!");
            alertVide.setContentText("Veuillez saisir le sku du produit.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        if (categorie==null) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("categorie vide!");
            alertVide.setContentText("Veuillez saisir la categorie du produit.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        ps.ajouter(new Produit(tfDescription.getText(), tf_Prix, tf_Quantite, tfMarque.getText(), id_categorie.getValue(), tfurl.getText(), tfsku.getText()));
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setTitle("produit ajout");
        alerte.setContentText("produit bien ajoute");
        alerte.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategorieService es = new CategorieService();
        List<Categorie> l=es.afficher();                //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        id_categorie.setItems(FXCollections.observableArrayList(l));
    }
    @FXML
    void browse(ActionEvent event) {
        // Sélectionnez un fichier à partir de la boîte de dialogue
        FileChooser fileChooser = new FileChooser();

        // Ajoutez un filtre pour les fichiers d'image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        // Définissez le répertoire initial
        fileChooser.setInitialDirectory(new File("C:/Users/Soumaya/Desktop/workshopjdbc/src/main/resources/images"));

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
                tfurl.setText(imageFile);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Gérez l'exception en conséquence (par exemple, affichez un message d'erreur)
            }
        }
    }
    @FXML
    void returnM(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Menu.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

}




