package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.services.CoursService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImageC {

    @FXML
    private ComboBox<String> filtre;
    @FXML
    private TextField recherche;
    private int likes = 0;
    private int dislikes = 0;
    private long lastClickTime = 0;
    @FXML
    private FlowPane flowPane;
    private List<Node> originalNodes = new ArrayList<>();

    public void initialize() {
        filtre.setValue("nom");
        filtre.getItems().addAll("nom", "duree");
        CoursService coursService = new CoursService();
        List<Cours> coursList = coursService.afficher();
        flowPane.setHgap(30); // Set horizontal gap
        flowPane.setVgap(30);

        // Parcours la liste d'images et crée des VBox pour chaque image
        for (Cours cours : coursList) {
            VBox vBox = createImageWithButtonVBox(cours);
            vBox.setUserData(cours);

            flowPane.getChildren().add(vBox);

        }
        originalNodes.addAll(flowPane.getChildren());
        ////recherche ///
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercher();
        });
        recherche.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && recherche.getText().isEmpty()) {
                flowPane.getChildren().setAll(originalNodes);
            }
        });
    }

    private VBox createImageWithButtonVBox(Cours cours) {
        VBox vBox = new VBox();
        //vbox style
        vBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Crée l'ImageView pour l'image
        String imageUrl = cours.getImagec();//rcupere limage
        ImageView imageView = createImageView(imageUrl);//cree image view
        //image style
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);
        //
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator4 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);
        //label nom cours
        Label nomcours = new Label(cours.getNom());
        //style
        nomcours.setAlignment(Pos.CENTER);
        //stackpane

        //label description


        // Crée le bouton "Détails" pour chaque image
        Button detailsButton = new Button("PARTICIPER");
        detailsButton.setOnAction(this::showDetails);
        //
        // Crée le bouton "Détails" pour chaque image
        Button like = new Button("like");
        like.setOnAction(e -> likeaction(e, vBox));
        // Crée le bouton "Détails" pour chaque image
        Button dislike = new Button("dislike");
        dislike.setOnAction(e -> dislikeaction(e, vBox));
        HBox likeDislikeBox = new HBox(10, like, dislike);
        Label likesDislikesLabel = new Label("");
        //
        // Bouton QR Code
        Button qrCodeButton = new Button("plus de detaille ");
        qrCodeButton.setOnAction(event -> generateQRCode(cours));
        HBox qrCodeBox = new HBox(qrCodeButton);
        HBox buttonsBox = new HBox(10, detailsButton, qrCodeBox);
//style

        like.setStyle("-fx-content-display: CENTER;");
        like.setStyle("-fx-background-radius: 12; -fx-background-color: pink;");

        dislike.setStyle("-fx-content-display: CENTER;");
        dislike.setStyle("-fx-background-radius: 15;-fx-background-color: #87CEEB;");

        //style
        detailsButton.setPrefWidth(90);
        detailsButton.setStyle("-fx-content-display: LEFT;");
        detailsButton.setStyle("-fx-background-radius: 15;");

        //qr style
        qrCodeButton.setPrefWidth(90);
       // detailsButton.setStyle("-fx-content-display: LEFT;");
        qrCodeButton.setStyle("-fx-background-radius: 15;");
        // Ajoute l'ImageView et le bouton "Détails" à la VBox
        vBox.getChildren().addAll(nomcours, separator1, imageView, separator2, buttonsBox,separator3,likeDislikeBox,separator4, likesDislikesLabel);

        return vBox;

    }

    private void dislikeaction(ActionEvent e, VBox vBox) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            if (dislikes > 0)
                dislikes--;
            else
                dislikes = 0;
        } else {
            dislikes++;
        }
        lastClickTime = currentTime;
        updateLikesDislikesLabel(vBox);

    }

    private void likeaction(ActionEvent e, VBox vBox) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            if (likes > 0)
                likes--;
            else
                likes = 0;
        } else {
            likes++;
        }
        lastClickTime = currentTime;
        updateLikesDislikesLabel(vBox);
    }

    private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(130);

        try {
            File file = new File(imageUrl);
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageView;
    }

    private void showDetails(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/participer.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void goback(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void updateLikesDislikesLabel(VBox vBox) {
        Label likesDislikesLabel = (Label) vBox.getChildren().get(vBox.getChildren().size() - 1);
        likesDislikesLabel.setText("Likes: " + likes + " Dislikes: " + dislikes);
    }
    private void rechercher() {
        String searchText = recherche.getText().toLowerCase();

        List<Node> filteredNodes = flowPane.getChildren().stream()
                .filter(node -> {
                    if (node instanceof VBox) {
                        VBox vBox = (VBox) node;
                        Label courNameLabel = (Label) vBox.getChildren().get(0);
                        String courName = courNameLabel.getText().toLowerCase();
                        return courName.startsWith(searchText);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(filteredNodes);
    }
    //trie

    @FXML
    void trier(ActionEvent event) {

        ObservableList<Node> children = flowPane.getChildren();

      //trie
        Comparator<Cours> comparator = null;

        if ("nom".equals(filtre.getValue())) {
            comparator = Comparator.comparing(Cours::getNom);
        }

        if ("duree".equals(filtre.getValue())) {
            comparator = Comparator.comparing(Cours::getDuree);
        }

        List<Node> sortedChildren = new ArrayList<>(children);
        Comparator<Cours> finalComparator = comparator;
        sortedChildren.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                Cours c1 = (Cours) n1.getUserData();
                Cours c2 = (Cours) n2.getUserData();
                return finalComparator.compare(c1, c2);
            }
        });

        flowPane.getChildren().setAll(sortedChildren);
    }
    private void generateQRCode(Cours cours) {
        try {
            int coursId = cours.getId();

            // Récupérez la description du cours à partir de la base de données
            String description = affiche(coursId);

            // Génération du QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(description, BarcodeFormat.QR_CODE, 200, 200);

            // Convertit le BitMatrix en une image JavaFX
            Image qrCodeImage = matrixToImage(bitMatrix);

            // Affiche l'image dans une nouvelle fenêtre
            showQRCodeImage(qrCodeImage, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showQRCodeImage(Image qrCodeImage, String description) {
        // Affiche l'image du QR Code dans une nouvelle fenêtre
        Stage qrCodeStage = new Stage();
        ImageView imageView = new ImageView(qrCodeImage);
        StackPane root = new StackPane(imageView);
       // root.getChildren().add(new Label("Description: " + description)); // Ajoutez une étiquette pour afficher la description
        Scene scene = new Scene(root, 220, 250);
        qrCodeStage.setScene(scene);
        qrCodeStage.setTitle("QR Code");
        qrCodeStage.show();
    }
    private Image matrixToImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        WritableImage writableImage = new WritableImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writableImage.getPixelWriter().setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return writableImage;
    }



    private String affiche(int coursId) {
        CoursService coursService=new CoursService();
        String description = coursService.getDescriptionAndDureeById(coursId);


        if (description != null) {

            System.out.println("Description du Cours : " + description);
        } else {
            System.out.println("Description du Cours non trouvée");
        }
        return description;
    }
    }

