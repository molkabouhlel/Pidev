package com.esprit.controllers;

import com.esprit.models.Club;

import com.esprit.models.Espace;
import com.esprit.models.ListCours;
import com.esprit.services.ClubService;

import com.esprit.services.EspaceService;
import com.esprit.services.ListCoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class ListeCoursController {



    @FXML
    private ComboBox<Espace> Espace;
    @FXML
    private TextField adresse_club;

    @FXML
    private TextArea description_club;

    @FXML
    private TextField id_club;

    @FXML
    private TextField image_club;

    @FXML
    private TextField nom_club;

    @FXML
    private TextField temp_ouverture;

    @FXML
    private ImageView imageClub;


///////////////////////////////////////////////////////////////////////////

    @FXML
    private Button Ajout_ListeCours;

    @FXML
    private TextField Club;

    @FXML
    private Button DeleteListeCours;

    @FXML
    private TableView<ListCours> ListeCourd_TableView;

    @FXML
    private Button ModifyListeCours;

    @FXML
    private TableColumn<ListCours, Integer> cours;

    @FXML
    private TableColumn<ListCours, Integer> id_liste;
    @FXML
    private TableColumn<ListCours, String> Clubcolonne;

    @FXML
    private TextField id_ListeCours;

    @FXML
    private ComboBox<Integer> id_cours;

    private int  List_Selected;
    private int Club_selected;
    private ListCours ListCoursToModifier;

    private Club ClubToModifier;



    public void initClub(Club C) {


        ClubToModifier = C;
        //id_club.setText(String.valueOf(C.getId_club()));
        nom_club.setText(C.getNom_club());
        adresse_club.setText(C.getAdresse_club());
        description_club.setText(C.getDescription_club());
        image_club.setText(C.getImage_club());
        temp_ouverture.setText(String.valueOf(C.getTemp_ouverture()));
        Espace.setValue(C.getEspace());

        String imageUrl = image_club.getText();
        System.out.println(imageUrl);

        Image image = new Image(new File(imageUrl).toURI().toString());
        imageClub.setImage(image);
        imageClub.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Club_selected=C.getId_club();
    }

    public void initListe(ListCours LS) {
        ListCoursService ls = new ListCoursService();
        ListCoursToModifier = LS;
        // System.out.println(ListCoursToModifier);

        if (ListCoursToModifier.getId() != 0) {
           // id_ListeCours.setText(String.valueOf(ListCoursToModifier.getId()));
            System.out.println("this is id club "+ListCoursToModifier.getClub().getId_club());
            Club.setText(String.valueOf(ListCoursToModifier.getClub().getNom_club()));
            id_cours.setValue(ListCoursToModifier.getId_cours());
        } else {
           // id_ListeCours.setText(null);
            Club.setText(null);
            id_cours.setValue(null);
        }

    }

        public void initialize(Club C) {
            ///////////////////////////CONTROLE SAISIE//////////////////////////
            Club.setEditable(false);
            Club.setText(C.getNom_club());
            //controle saisie text
            nom_club.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches(".*\\d.*")) {
                    nom_club.setText(oldValue);
                }
            });
            nom_club.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getControlNewText().length() <= 15) {
                    return change;
                }
                return null;
            }));

            adresse_club.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches(".*\\d.*")) {
                    adresse_club.setText(oldValue);
                }
            });
            adresse_club.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getControlNewText().length() <= 15) {
                    return change;
                }
                return null;
            }));

            description_club.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches(".*\\d.*")) {
                    adresse_club.setText(oldValue);
                }
            });

            //controle saisie Time
            temp_ouverture.setPromptText("hh:mm:ss");
            temp_ouverture.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d{0,2}(:\\d{0,2}(:\\d{0,2})?)?")) {
                    temp_ouverture.setText(oldValue);
                }
            });

            Espace.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    temp_ouverture.setText(String.valueOf(newValue.getHeure_debut()));
                    temp_ouverture.setEditable(false);
                }
            });


        //////////////////////////////////////////////////////////////////////////////
        ClubToModifier = C;
        ClubService cs=new ClubService();

        //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
            EspaceService es=new EspaceService();
            List<Espace> LIST=es.afficher();
            Espace.setItems(FXCollections.observableArrayList(LIST));

            //HEDHI COMBOBOX BL LES ID cours
        List<Integer> lc=cs.RecupereridCours();
        id_cours.setItems(FXCollections.observableArrayList(lc));



        ListCoursService ls=new ListCoursService();
        List listCours=ls.RecupererListeCours(ClubToModifier.getId_club());
        System.out.println(listCours);



        ObservableList<ListCours> ListCoursObservableList = FXCollections.observableArrayList(listCours);
        ListeCourd_TableView.setItems(ListCoursObservableList);
        //id_liste.setCellValueFactory(new PropertyValueFactory<>("id"));
        Clubcolonne.setCellValueFactory(new PropertyValueFactory<>("club"));
        cours.setCellValueFactory(new PropertyValueFactory<>("id_cours"));





        ListeCourd_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List_Selected = newSelection.getId();
                System.out.println("Id List selected "+List_Selected);
                ListCours l=ls.rechercheListeCours(List_Selected) ;


               initListe(l);

            } else {
                List_Selected = -1;
            }
        });


    }




    @FXML
    void Browse(ActionEvent event) {
        // select a file from the dialog box
        FileChooser fileChooser = new FileChooser();
        // image file extensions
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.gif"));
        fileChooser.setInitialDirectory(new File("C:/Users/thebe/OneDrive/Bureau/workshopjdbc/src/main/resources/Image"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String imageFile = file.toURI().toString();
            imageFile = imageFile.substring(8);
            image_club.setText(imageFile);
        }
        String imageUrl = image_club.getText();
        System.out.println(imageUrl);

        Image image = new Image(new File(imageUrl).toURI().toString());
        imageClub.setImage(image);
        imageClub.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

    }


    @FXML
    void Ajout_ListeCours(ActionEvent event) throws IOException {
        ClubService cs = new ClubService();

        Club C=cs.rechercheClub(ListCoursToModifier.getClub().getId_club());

        ListCoursService LS=new ListCoursService();

        LS.ajouter(new ListCours(C,id_cours.getValue()) );

        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("ListCours ajout");
        alerte.setContentText("ListCours bien ajoutee");
        alerte.show();



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Club.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }



    @FXML
    void DeleteListeCours(ActionEvent event) {
        if (List_Selected != -1) {
            ListCoursService LS = new ListCoursService();

            ListCours list_Selected = LS.rechercheListeCours(List_Selected);
            System.out.println(list_Selected);
            LS.supprimer(list_Selected);
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion Espace");
            alerte.setContentText("Espace  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) ListeCourd_TableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void ModifyClub(ActionEvent event) throws IOException {
        ClubService cs = new ClubService();
        Espace espace = Espace.getValue();

       // ClubToModifier.setId_club(Integer.parseInt(id_club.getText()));
        ClubToModifier.setNom_club(nom_club.getText());
        ClubToModifier.setAdresse_club(adresse_club.getText());
        ClubToModifier.setDescription_club(description_club.getText());
        ClubToModifier.setImage_club(image_club.getText());
        ClubToModifier.setTemp_ouverture(Time.valueOf(temp_ouverture.getText()));

        ClubToModifier.setEspace(espace);

        cs.modifier(ClubToModifier);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier Club");
        alert.setContentText("Modification avec succces");
        alert.showAndWait();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void ModifyListeCours(ActionEvent event) throws IOException {
        ListCoursService LS = new ListCoursService();
        ClubService cs =new ClubService();
        Club C=cs.rechercheClub(ListCoursToModifier.getClub().getId_club());
        System.out.println(ListCoursToModifier);
        //ListCoursToModifier.setId(Integer.parseInt(id_ListeCours.getText()));
        ListCoursToModifier.setClub(C);
        ListCoursToModifier.setId_cours(id_cours.getValue());


        LS.modifier(ListCoursToModifier);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier Liste");
        alert.setContentText("Modification avec succces");
        alert.showAndWait();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void RedirectToAffichageClub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ListeCourd_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));


    }
}

