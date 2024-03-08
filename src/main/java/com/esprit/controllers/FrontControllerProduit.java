package com.esprit.controllers;

import com.esprit.models.PDFGeneratorService;
import com.esprit.models.Produit;
import com.esprit.services.ProduitService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FrontControllerProduit {
      /*  @FXML
        private TableColumn<Produit, Void> action;*/
       @FXML
        private TableColumn<Produit, Void> action1;
        @FXML
        private TableColumn<Produit, String> id_categorie;
        @FXML
        private TableColumn<Produit, String> description_produit;
        @FXML
        private TableColumn<Produit, String> image;
        @FXML
        private TableColumn<Produit, String> marque;
        @FXML
        private TableColumn<Produit, Float> prix;
        @FXML
        private TableColumn<Produit, Integer> quantite_produit;
        @FXML
        private TableColumn<Produit, String> sku;
        @FXML
        private TableView<Produit> tableview;
        @FXML
        private RadioButton filter1;
        @FXML
        private Button extrapdf;
        @FXML
        private RadioButton filter2;
        @FXML
        private CheckBox TriM;
        @FXML
        private RadioButton TriDesc;
        @FXML
        private TextField rechP;
        @FXML
        private TextField TriP;
        @FXML
        private RadioButton trimm;
        ObservableList<Produit> produitList = FXCollections.observableArrayList();
        public FrontControllerProduit() {
        }
        @FXML
        void initialize() {
            ProduitService p = new ProduitService();
            List<Produit> produitt = p.afficher();
            ObservableList<Produit> observableList = FXCollections.observableList(produitt);
            this.tableview.setItems(observableList);
            id_categorie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_categorie().getNom_cat()));
            // id_categorie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_categorie().getNom_cat()));
            // this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));
            // this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));

            this.id_categorie.setCellFactory(TextFieldTableCell.forTableColumn());
            this.description_produit.setCellValueFactory(new PropertyValueFactory("desc"));
            this.image.setCellValueFactory(new PropertyValueFactory("image"));
            this.marque.setCellValueFactory(new PropertyValueFactory("marque"));
            this.prix.setCellValueFactory(new PropertyValueFactory("prix"));
            this.quantite_produit.setCellValueFactory(new PropertyValueFactory("quant"));
            this.sku.setCellValueFactory(new PropertyValueFactory("sku"));
            this.image.setCellFactory(column -> {
                return new TableCell<Produit, String>() {
                    private final ImageView imageView = new ImageView();

                    {
                        // Set the size of the ImageView as needed
                        imageView.setFitWidth(150);
                        imageView.setFitHeight(130);
                        setGraphic(imageView);
                    }

                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);
                        if (imagePath == null || empty) {
                            imageView.setImage(null);
                        } else {
                            try {
                                // Load the image from the imagePath and set it in the ImageView
                                Image image = new Image(new File(imagePath).toURI().toString());
                                imageView.setImage(image);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            });
            tableview.setEditable(true);


          /*  this.description_produit.setCellFactory(TextFieldTableCell.forTableColumn());
            this.sku.setCellFactory(TextFieldTableCell.forTableColumn());
            this.marque.setCellFactory(TextFieldTableCell.forTableColumn());
            this.quantite_produit.setCellFactory(TextFieldTableCell.forTableColumn((new StringConverter<Integer>() {
                @Override
                public String toString(Integer object) {
                    return object.toString();
                }

                @Override
                public Integer fromString(String string) {
                    try {
                        return Integer.parseInt(string);
                    } catch (NumberFormatException e) {
                        // Handle the case where the input is not a valid integer
                        // You may choose to display an error message or return null
                        return null;
                    }
                }
            })));
            this.prix.setCellFactory(TextFieldTableCell.forTableColumn((new StringConverter<Float>() {
                @Override
                public String toString(Float object) {
                    return object.toString();
                }

                @Override
                public Float fromString(String string) {
                    try {
                        return Float.parseFloat(string);
                    } catch (NumberFormatException e) {
                        // Handle the case where the input is not a valid integer
                        // You may choose to display an error message or return null
                        return null;
                    }
                }
            })));


            // Save changes on commit description
            this.description_produit.setOnEditCommit(event -> {

                String newSujet = event.getNewValue();
                if (newSujet.trim().isEmpty()) {
                    // Display error message for empty text
                    Alert alertVide = new Alert(Alert.AlertType.ERROR);
                    alertVide.setTitle("Erreur de Saisie");
                    alertVide.setHeaderText("Titre vide!");
                    alertVide.setContentText("Veuillez saisir le titre du sujet.");
                    alertVide.show();
                    return;
                } else if (newSujet.length() > 30) {
                    // Display error message for exceeding length
                    Alert alertLength = new Alert(Alert.AlertType.ERROR);
                    alertLength.setTitle("Erreur de Saisie");
                    alertLength.setHeaderText("Titre de sujet est trop longue!");
                    alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                    alertLength.show();
                    return;
                }

                Produit pr = event.getRowValue();
                pr.setDesc(event.getNewValue());
                p.modifier(pr);
            });
            // Save changes on commit marque
            this.marque.setOnEditCommit(event -> {

                String newSujet = event.getNewValue();
                if (newSujet.trim().isEmpty()) {
                    // Display error message for empty text
                    Alert alertVide = new Alert(Alert.AlertType.ERROR);
                    alertVide.setTitle("Erreur de Saisie");
                    alertVide.setHeaderText("Titre vide!");
                    alertVide.setContentText("Veuillez saisir le titre du sujet.");
                    alertVide.show();
                    return;
                } else if (newSujet.length() > 30) {
                    // Display error message for exceeding length
                    Alert alertLength = new Alert(Alert.AlertType.ERROR);
                    alertLength.setTitle("Erreur de Saisie");
                    alertLength.setHeaderText("Titre de sujet est trop longue!");
                    alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                    alertLength.show();
                    return;
                }


                Produit pr = event.getRowValue();
                pr.setMarque(event.getNewValue());
                p.modifier(pr);
            });
            // Save changes on commit sku
            this.sku.setOnEditCommit(event -> {

                String newSujet = event.getNewValue();
                if (newSujet.trim().isEmpty()) {
                    // Display error message for empty text
                    Alert alertVide = new Alert(Alert.AlertType.ERROR);
                    alertVide.setTitle("Erreur de Saisie");
                    alertVide.setHeaderText("Titre vide!");
                    alertVide.setContentText("Veuillez saisir le titre du sujet.");
                    alertVide.show();
                    return;
                } else if (newSujet.length() > 30) {
                    // Display error message for exceeding length
                    Alert alertLength = new Alert(Alert.AlertType.ERROR);
                    alertLength.setTitle("Erreur de Saisie");
                    alertLength.setHeaderText("Titre de sujet est trop longue!");
                    alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                    alertLength.show();
                    return;
                }

                Produit pr = event.getRowValue();
                pr.setSku(event.getNewValue());
                p.modifier(pr);
            });
            this.quantite_produit.setOnEditCommit(event -> {

                int newQuantite = event.getNewValue();
                if (String.valueOf(newQuantite).trim().isEmpty()) {
                    // Display error message for empty text
                    Alert alertVide = new Alert(Alert.AlertType.ERROR);
                    alertVide.setTitle("Erreur de Saisie");
                    alertVide.setHeaderText("Titre vide!");
                    alertVide.setContentText("Veuillez saisir le titre du sujet.");
                    alertVide.show();
                    return;
                } else if (String.valueOf(newQuantite).trim().length() > 30) {
                    // Display error message for exceeding length
                    Alert alertLength = new Alert(Alert.AlertType.ERROR);
                    alertLength.setTitle("Erreur de Saisie");
                    alertLength.setHeaderText("Titre de sujet est trop longue!");
                    alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                    alertLength.show();
                    return;
                }
                Produit pr = event.getRowValue();
                pr.setQuant(event.getNewValue());
                p.modifier(pr);
            });
            this.prix.setOnEditCommit(event -> {

                float newQuantite = event.getNewValue();
                if (String.valueOf(newQuantite).trim().isEmpty()) {
                    // Display error message for empty text
                    Alert alertVide = new Alert(Alert.AlertType.ERROR);
                    alertVide.setTitle("Erreur de Saisie");
                    alertVide.setHeaderText("Titre vide!");
                    alertVide.setContentText("Veuillez saisir le titre du sujet.");
                    alertVide.show();
                    return;
                } else if (String.valueOf(newQuantite).trim().length() > 30) {
                    // Display error message for exceeding length
                    Alert alertLength = new Alert(Alert.AlertType.ERROR);
                    alertLength.setTitle("Erreur de Saisie");
                    alertLength.setHeaderText("Titre de sujet est trop longue!");
                    alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                    alertLength.show();
                    return;
                }
                Produit pr = event.getRowValue();
                pr.setPrix(event.getNewValue());
                p.modifier(pr);
            });
            boutonsupp();*/
            boutonpdf();
        }

        @FXML
        void returnMe(ActionEvent event) {
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


        @FXML
        public void SearchByContent(javafx.scene.input.KeyEvent keyEvent) {
            try {
                ProduitService p = new ProduitService();
                String content = rechP.getText();
                List<Produit> SearchResults = p.SearchByContent(content);
                ObservableList<Produit> observableList = FXCollections.observableList(SearchResults);
                this.tableview.setItems(observableList);
                //this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));

                this.id_categorie.setCellFactory(TextFieldTableCell.forTableColumn());
                this.description_produit.setCellValueFactory(new PropertyValueFactory("desc"));
                this.image.setCellValueFactory(new PropertyValueFactory("image"));
                this.marque.setCellValueFactory(new PropertyValueFactory("marque"));
                this.prix.setCellValueFactory(new PropertyValueFactory("prix"));
                this.quantite_produit.setCellValueFactory(new PropertyValueFactory("quant"));
                this.sku.setCellValueFactory(new PropertyValueFactory("sku"));
                this.image.setCellFactory(column -> {
                    return new TableCell<Produit, String>() {
                        private final ImageView imageView = new ImageView();

                        {
                            // Set the size of the ImageView as needed
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(130);
                            setGraphic(imageView);
                        }

                        @Override
                        protected void updateItem(String imagePath, boolean empty) {
                            super.updateItem(imagePath, empty);
                            if (imagePath == null || empty) {
                                imageView.setImage(null);
                            } else {
                                try {
                                    // Load the image from the imagePath and set it in the ImageView
                                    Image image = new Image(new File(imagePath).toURI().toString());
                                    imageView.setImage(image);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                });

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

        //TODO **************BOUTON SUPPRIMER cree tableview
      /*  private void boutonsupp() {
            action.setCellFactory(col -> new TableCell<Produit, Void>() {
                private final Button participerButton = new Button("supprimer");

                {
                    participerButton.setOnAction(event -> {
                        //   tableview.edit(-1, null);
                        Produit produit = getTableView().getItems().get(getIndex());
                        supprimer(produit);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(participerButton);
                    }
                }
            });
        }

        private void supprimer(Produit ev) {
            ProduitService p = new ProduitService();
            p.supprimer(ev);
            // Actualisez la TableView pour refléter la suppression
            tableview.getItems().remove(ev);
        }*/
        //TODO **************BOUTON PDF  tableview
        private void boutonpdf() {
            action1.setCellFactory(col -> new TableCell<Produit, Void>() {
                private final Button participerButton = new Button("pdf");

                {
                    participerButton.setOnAction(event -> {
                        //   tableview.edit(-1, null);
                        Produit produit = getTableView().getItems().get(getIndex());
                        PDFGeneratorService pd = new PDFGeneratorService();
                        pd.generatePDFProduit(produit);
                        // pd.generatePDF(tableview);
                        //supprimer(produit);
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(participerButton);
                    }
                }
            });
        }
        /* @FXML
         void pdff(ActionEvent event) {
             PDFGeneratorService pd = new PDFGeneratorService();
             pd.generatePDF(tableview);
         }*/
        @FXML
        void TriMa(ActionEvent event) {
            try {
                ProduitService p = new ProduitService();
                List<Produit> SearchResults = p.trimarque();
                ObservableList<Produit> observableList = FXCollections.observableList(SearchResults);
                this.tableview.setItems(observableList);
                // this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));

                this.id_categorie.setCellFactory(TextFieldTableCell.forTableColumn());
                this.description_produit.setCellValueFactory(new PropertyValueFactory("desc"));
                this.image.setCellValueFactory(new PropertyValueFactory("image"));
                this.marque.setCellValueFactory(new PropertyValueFactory("marque"));
                this.prix.setCellValueFactory(new PropertyValueFactory("prix"));
                this.quantite_produit.setCellValueFactory(new PropertyValueFactory("quant"));
                this.sku.setCellValueFactory(new PropertyValueFactory("sku"));
                this.image.setCellFactory(column -> {
                    return new TableCell<Produit, String>() {
                        private final ImageView imageView = new ImageView();

                        {
                            // Set the size of the ImageView as needed
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(130);
                            setGraphic(imageView);
                        }

                        @Override
                        protected void updateItem(String imagePath, boolean empty) {
                            super.updateItem(imagePath, empty);
                            if (imagePath == null || empty) {
                                imageView.setImage(null);
                            } else {
                                try {
                                    // Load the image from the imagePath and set it in the ImageView
                                    Image image = new Image(new File(imagePath).toURI().toString());
                                    imageView.setImage(image);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                });

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }
        @FXML
        void TriDesc(ActionEvent event) {
            try {
                ProduitService p = new ProduitService();
                List<Produit> SearchResults = p.triProduit();
                ObservableList<Produit> observableList = FXCollections.observableList(SearchResults);
                this.tableview.setItems(observableList);
                //  this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));

                this.id_categorie.setCellFactory(TextFieldTableCell.forTableColumn());
                this.description_produit.setCellValueFactory(new PropertyValueFactory("desc"));
                this.image.setCellValueFactory(new PropertyValueFactory("image"));
                this.marque.setCellValueFactory(new PropertyValueFactory("marque"));
                this.prix.setCellValueFactory(new PropertyValueFactory("prix"));
                this.quantite_produit.setCellValueFactory(new PropertyValueFactory("quant"));
                this.sku.setCellValueFactory(new PropertyValueFactory("sku"));
                this.image.setCellFactory(column -> {
                    return new TableCell<Produit, String>() {
                        private final ImageView imageView = new ImageView();

                        {
                            // Set the size of the ImageView as needed
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(130);
                            setGraphic(imageView);
                        }

                        @Override
                        protected void updateItem(String imagePath, boolean empty) {
                            super.updateItem(imagePath, empty);
                            if (imagePath == null || empty) {
                                imageView.setImage(null);
                            } else {
                                try {
                                    // Load the image from the imagePath and set it in the ImageView
                                    Image image = new Image(new File(imagePath).toURI().toString());
                                    imageView.setImage(image);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                });

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }
        @FXML
        void filter1(ActionEvent event) {
            try {
                ProduitService p = new ProduitService();
                List<Produit> SearchResults = p.filter1();
                ObservableList<Produit> observableList = FXCollections.observableList(SearchResults);
                this.tableview.setItems(observableList);
                // this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));

                this.id_categorie.setCellFactory(TextFieldTableCell.forTableColumn());
                this.description_produit.setCellValueFactory(new PropertyValueFactory("desc"));
                this.image.setCellValueFactory(new PropertyValueFactory("image"));
                this.marque.setCellValueFactory(new PropertyValueFactory("marque"));
                this.prix.setCellValueFactory(new PropertyValueFactory("prix"));
                this.quantite_produit.setCellValueFactory(new PropertyValueFactory("quant"));
                this.sku.setCellValueFactory(new PropertyValueFactory("sku"));
                this.image.setCellFactory(column -> {
                    return new TableCell<Produit, String>() {
                        private final ImageView imageView = new ImageView();

                        {
                            // Set the size of the ImageView as needed
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(130);
                            setGraphic(imageView);
                        }

                        @Override
                        protected void updateItem(String imagePath, boolean empty) {
                            super.updateItem(imagePath, empty);
                            if (imagePath == null || empty) {
                                imageView.setImage(null);
                            } else {
                                try {
                                    // Load the image from the imagePath and set it in the ImageView
                                    Image image = new Image(new File(imagePath).toURI().toString());
                                    imageView.setImage(image);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                });

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }

        @FXML
        void filter2(ActionEvent event) {
            try {
                ProduitService p = new ProduitService();
                List<Produit> SearchResults = p.filter20();
                ObservableList<Produit> observableList = FXCollections.observableList(SearchResults);
                this.tableview.setItems(observableList);
                // this.id_categorie.setCellValueFactory(new PropertyValueFactory("id_categorie"));

                this.id_categorie.setCellFactory(TextFieldTableCell.forTableColumn());
                this.description_produit.setCellValueFactory(new PropertyValueFactory("desc"));
                this.image.setCellValueFactory(new PropertyValueFactory("image"));
                this.marque.setCellValueFactory(new PropertyValueFactory("marque"));
                this.prix.setCellValueFactory(new PropertyValueFactory("prix"));
                this.quantite_produit.setCellValueFactory(new PropertyValueFactory("quant"));
                this.sku.setCellValueFactory(new PropertyValueFactory("sku"));
                this.image.setCellFactory(column -> {
                    return new TableCell<Produit, String>() {
                        private final ImageView imageView = new ImageView();

                        {
                            // Set the size of the ImageView as needed
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(130);
                            setGraphic(imageView);
                        }

                        @Override
                        protected void updateItem(String imagePath, boolean empty) {
                            super.updateItem(imagePath, empty);
                            if (imagePath == null || empty) {
                                imageView.setImage(null);
                            } else {
                                try {
                                    // Load the image from the imagePath and set it in the ImageView
                                    Image image = new Image(new File(imagePath).toURI().toString());
                                    imageView.setImage(image);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                });

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }
        @FXML
        void rateshow(ActionEvent event) {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Affichagescore.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
        }

        @FXML
        void rateadd(ActionEvent event) {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AjoutScore.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
        }
        @FXML
        void statshow(ActionEvent event) {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Afficherbarchart.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
        }


    @FXML
    void retour(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceMenuCoach.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    }

