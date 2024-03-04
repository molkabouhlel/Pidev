package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
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

public class Imageeve {
    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> trie;
    private int likes = 0;
    private int dislikes = 0;
    private long lastClickTime = 0;
    @FXML
    private FlowPane flowPane;
    private List<Node> originalNodes = new ArrayList<>();

    public void initialize() {
        trie.setValue("nom");
        trie.getItems().addAll("nom","date_debut");
        EvenementService evenementService = new EvenementService();
        List<Evenement> evenementliste = evenementService.afficher();
        flowPane.setHgap(30);
        flowPane.setVgap(30);

        //  crée des VBox pour chaque image
        for (Evenement evenement : evenementliste) {
            VBox vBox = createImageWithButtonVBox(evenement);
            vBox.setUserData(evenement);

            flowPane.getChildren().add(vBox);
        }
        originalNodes.addAll(flowPane.getChildren());
        ////recherche ///
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercher();
        });
        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && search.getText().isEmpty()) {
                flowPane.getChildren().setAll(originalNodes);
            }
        });
    }

    private VBox createImageWithButtonVBox(Evenement evenement) {
        VBox vBox = new VBox();
        //vbox style
        vBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Crée l'ImageView pour l'image
        String imageUrl = evenement.getImage_ev();//rcupere limage
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
        Label nomev = new Label(evenement.getNom_ev());
        //style
        nomev.setAlignment(Pos.CENTER);
        //stackpane
        StackPane labelPane = new StackPane();
        labelPane.getChildren().add(nomev);
        //label description
        Label label2 = new Label("Description: " + evenement.getDescription_ev());



        Button like = new Button("like");
        like.setOnAction(e -> likeaction(e, vBox));

        Button dislike = new Button("dislike");
        dislike.setOnAction(e -> dislikeaction(e, vBox));
        HBox likeDislikeBox = new HBox(10, like, dislike);
        Label likesDislikesLabel = new Label("");
//style

        like.setStyle("-fx-content-display: CENTER;");
        like.setStyle("-fx-background-radius: 12; -fx-background-color: pink;");

        dislike.setStyle("-fx-content-display: CENTER;");
        dislike.setStyle("-fx-background-radius: 15;-fx-background-color: #87CEEB;");

        //style

        // Ajoute l'ImageView et le bouton
        vBox.getChildren().addAll(labelPane, separator1, imageView, separator2, label2,separator3,likeDislikeBox,separator4, likesDislikesLabel);

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
        String searchText = search.getText().toLowerCase();

        List<Node> filteredNodes = flowPane.getChildren().stream()
                .filter(node -> {
                    if (node instanceof VBox) {
                        VBox vBox = (VBox) node;

                        // Check if the first child is a StackPane
                        if (vBox.getChildren().get(0) instanceof StackPane) {
                            StackPane stackPane = (StackPane) vBox.getChildren().get(0);

                            // Check if the StackPane contains a Label
                            if (stackPane.getChildren().get(0) instanceof Label) {
                                Label courNameLabel = (Label) stackPane.getChildren().get(0);
                                String courName = courNameLabel.getText().toLowerCase();
                                return courName.startsWith(searchText);
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(filteredNodes);
    }
    @FXML
    void trier(ActionEvent event) {

        ObservableList<Node> children = flowPane.getChildren();

        //trie
        Comparator<Evenement> comparator = null;

        if ("nom".equals(trie.getValue())) {
            comparator = Comparator.comparing(Evenement::getNom_ev);
        }

        if ("date_debut".equals(trie.getValue())) {
            comparator = Comparator.comparing(Evenement::getDate_debut);
        }

        List<Node> sortedChildren = new ArrayList<>(children);
        Comparator<Evenement> finalComparator = comparator;
        sortedChildren.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                Evenement c1 = (Evenement) n1.getUserData();
                Evenement c2 = (Evenement) n2.getUserData();
                return finalComparator.compare(c1, c2);
            }
        });

        flowPane.getChildren().setAll(sortedChildren);
    }
}
