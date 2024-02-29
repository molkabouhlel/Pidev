package com.esprit.controllers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import com.esprit.models.Categorie;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import com.esprit.models.Produit;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ModifierProduitController implements Initializable {
    @FXML
    private ComboBox<Categorie> id_categ;
    @FXML
    private TextField description_produit;

    @FXML
    private TextField id_categorie;

    @FXML
    private TextField image;

    @FXML
    private TextField marque;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite_produit;

    @FXML
    private TextField sku;
    private Produit reponseToModify;

    public ModifierProduitController() {
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategorieService es = new CategorieService();
        List<Categorie> l=es.afficher();                //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        id_categ.setItems(FXCollections.observableArrayList(l));
       /* List<Integer> l=es.Return_idCategorie();                //HEDHI COMBOBOX BL ID
        id_categorie.setItems(FXCollections.observableArrayList()); }*/
    }
    public void setReponseToModify(Produit Produit1) {
        this.reponseToModify = Produit1;
        this.populateTextFields();
    }

    private void populateTextFields() {
        if (this.reponseToModify != null) {
            this.description_produit.setText(this.reponseToModify.getDesc());
            this.prix.setText(String.valueOf(this.reponseToModify.getPrix()));
            this.quantite_produit.setText(String.valueOf(this.reponseToModify.getQuant()));
            this.marque.setText(this.reponseToModify.getMarque());
            //this.id_categorie.setText(String.valueOf(this.reponseToModify.getId_categorie()));
            this.image.setText(this.reponseToModify.getImage());
            this.sku.setText(this.reponseToModify.getSku());
            id_categ.setValue(this.reponseToModify.getId_categorie());
        } else {
            this.description_produit.clear();
            this.marque.clear();
            this.image.clear();
            this.sku.clear();
        }
    }
    @FXML
    void reafficherc(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AffichageProduit.fxml"));

        try {
            this.description_produit.getScene().setRoot((Parent) loader.load());
        } catch (IOException var4) {
            System.err.println(var4.getMessage());
        }
    }
  @FXML
  void updatec(ActionEvent event) {
        ProduitService es = new ProduitService();
      Categorie categ = id_categ.getValue();
      //int numberOfQuestions = Integer.parseInt(nbr_questionstf.getText());
      reponseToModify.setDesc(description_produit.getText());
      reponseToModify.setPrix(Float.parseFloat(prix.getText()));
      reponseToModify.setQuant(Integer.parseInt(quantite_produit.getText()));
      reponseToModify.setMarque(marque.getText());
      reponseToModify.setImage(image.getText());
      reponseToModify.setSku(sku.getText());
      reponseToModify.setId_categorie(categ);
      es.modifier(reponseToModify);
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Modifier Produit");
      alert.setContentText("Modification avec succces");
      alert.show();
    }

    }
