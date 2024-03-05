package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.modeles.Participer;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.participerService;
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
import javafx.stage.Stage;

public class Categories {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ChoiceBox<String> choice;
    private ObservableList<typec> observableList1 = FXCollections.observableArrayList();
    @FXML
    private TextField idcatec;
    @FXML
    private TextField recherche;

   // @FXML
   // private TableColumn<?, ?> idtypec;


    @FXML
    private TableView<typec> tableview;

    @FXML
    private TableColumn<typec, String> typecours;

    @FXML
    private TextField typectext;
    private int idcatsselected;
    @FXML
    private TableColumn<typec, Void> action;
    @FXML


    public void initData(typec c) {
        idcatec.setText(String.valueOf(c.getIdtypec()));
        typectext.setText(c.getTypecours());

    }
    @FXML
    void ajoutercatcours(ActionEvent event) throws IOException {
        if (validerChamps()) {
            TypecService es = new TypecService();
            es.ajouter(new typec(typectext.getText()));
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("cours ajout");
            alerte.setContentText("cours bien ajoutee");
            alerte.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));

        }
    }
    @FXML
    void modifiercatcours(ActionEvent event) {
        if(validerChamps()) {
            TypecService es = new TypecService();
           // typec catselected = es.recherchecat(idcatsselected);
            //typec c = new typec();
            int idCat = Integer.parseInt(idcatec.getText());
            String cat = typectext.getText();

            es.modifier(new typec(idCat, cat));
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/categories.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    void retourner(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/principale.fxml"));
        try {
            typectext.getScene().setRoot(loader.load());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void supprimercategoriecours(ActionEvent event) throws IOException {
        TypecService c=new TypecService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            typec catASupprimer =  tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);


            tableview.getItems().remove(selectedID);
        } else {

            Alert alerte= new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("erreur");
            alerte.setContentText("categorie nest pas selectioner");
            alerte.show();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) tableview.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void initialize() {
        TypecService c = new TypecService();
        List<typec> cat = c.afficher();
        ObservableList<typec> observableList = FXCollections.observableList(cat);
        observableList1 = FXCollections.observableList(cat);

        tableview.setItems(observableList);
       // idtypec.setCellValueFactory(new PropertyValueFactory<>("idtypec"));
        typecours.setCellValueFactory(new PropertyValueFactory<>("typecours"));

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcatsselected = newSelection.getIdtypec();
                //
                idcatec.setText(String.valueOf(newSelection.getIdtypec()));
                typectext.setText(newSelection.getTypecours());
            } else {
                idcatsselected = -1;
            }
        });
        setupActionColumn();
        //
        tableview.setEditable(true);


        typecours.setCellFactory(TextFieldTableCell.forTableColumn());

        TypecService ss = new TypecService();




        typecours.setOnEditCommit(event -> {
            typec s = event.getRowValue();
            s.setTypecours(event.getNewValue());
            ss.modifier(s);
        });
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call a method to handle real-time search
            handleSearch(newValue);
        });

        //filtre
        ObservableList<String> categories = getCategorieList();
        choice.setItems(categories);
        choice.setValue(null);

        choice.setOnAction(event -> {
            String selectedCategorie = choice.getValue();
            if (selectedCategorie == null) {
                tableview.setItems(observableList);
            } else {
                ObservableList<typec> filteredList = observableList.filtered(cc -> cc.getTypecours().equals(selectedCategorie));
                tableview.setItems(filteredList);
            }
        });
    }
    private ObservableList<String> getCategorieList() {
        Set<String> categorieSet = observableList1.stream()
                .map(typec -> typec.getTypecours())
                .collect(Collectors.toSet());
        return FXCollections.observableArrayList(categorieSet);
    }
    private void setupActionColumn() {
        action.setCellFactory(col -> new TableCell<typec, Void>() {
            private final Button catButton = new Button("supprimer");

            {
                catButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    typec e = getTableView().getItems().get(getIndex());
                    supprimer(e);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(catButton);
                }
            }
        });
    }
    private void supprimer(typec ev) {
         TypecService p = new TypecService();
        p.supprimer(ev);

        tableview.getItems().remove(ev);
    }
    @FXML
    public boolean validerChamps() {
        if (typectext.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        if (!typectext.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'type' doit contenir uniquement des lettres.");
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

    //petit prob de retour
    private void handleSearch(String searchText) {
        // Create a filtered list based on the search text
        ObservableList<typec> filteredList = tableview.getItems().filtered(typec -> typec.getTypecours().toLowerCase().contains(searchText.toLowerCase()) );

        // Update the TableView with the filtered list
        tableview.setItems(filteredList);
    }

}