package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.modeles.type_ev;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.type_evService;
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

public class Catevent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField catev;
    @FXML
    private ChoiceBox<String> choice;
    private ObservableList<type_ev> observableList1 = FXCollections.observableArrayList();
    @FXML
    private TableColumn<type_ev, String> catevent;

    //@FXML
   // private TableColumn<?, ?> id_event;
    @FXML
    private TableColumn<type_ev, Void> action;


    @FXML
    private TextField iev;

    @FXML
    private TableView<type_ev> viewevent;
    private int idcatsselected;
    @FXML
    private TextField recherche;

    @FXML
    void retourner(ActionEvent event) {


        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        if(validerChamps()){
        type_evService es = new type_evService();

        es.ajouter(new type_ev(catev.getText()));

        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("event ajout");
        alerte.setContentText("event bien ajoutee");
        alerte.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/catevent.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) viewevent.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }}

    @FXML
    void delete(ActionEvent event) {
        type_evService c=new type_evService();


        int selectedID = viewevent.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            type_ev catASupprimer =  viewevent.getItems().get(selectedID);


            c.supprimer(catASupprimer);


            viewevent.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("categorie nest pas selectioner");
            alerte.show();
        }

    }

    @FXML
    void update(ActionEvent event) {
        if(validerChamps()) {
            type_evService es = new type_evService();
           // type_ev catselected = es.recherchecatev(idcatsselected);
            //typec c = new typec();
            int idCat = Integer.parseInt(iev.getText());
            String cat = catev.getText();

            es.modifier(new type_ev(idCat, cat));
            //
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/catevent.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    void initialize() {
        type_evService c = new type_evService();
        List<type_ev> cat = c.afficher();
        ObservableList<type_ev> observableList = FXCollections.observableList(cat);
        observableList1 = FXCollections.observableList(cat);
        viewevent.setItems(observableList);
       // id_event.setCellValueFactory(new PropertyValueFactory<>("id_typeev"));
        catevent.setCellValueFactory(new PropertyValueFactory<>("type_ev"));
        //System.out.println(c.afficher());
        viewevent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcatsselected = newSelection.getId_typeev();
                //
                iev.setText(String.valueOf(newSelection.getId_typeev()));
                catev.setText(newSelection.getType_ev());
            } else {
                idcatsselected = -1;
            }
        });
        setupActionColumn();
        //
        viewevent.setEditable(true);
        catevent.setCellFactory(TextFieldTableCell.forTableColumn());

        type_evService ss = new type_evService();




        catevent.setOnEditCommit(event -> {
            type_ev s = event.getRowValue();
            s.setType_ev(event.getNewValue());
            ss.modifier(s);
        });
        //search
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
                viewevent.setItems(observableList);
            } else {
                ObservableList<type_ev> filteredList = observableList.filtered(cc -> cc.getType_ev().equals(selectedCategorie));
                viewevent.setItems(filteredList);
            }
        });
    }
    private ObservableList<String> getCategorieList() {
        Set<String> categorieSet = observableList1.stream()
                .map(type_ev -> type_ev.getType_ev())
                .collect(Collectors.toSet());
        return FXCollections.observableArrayList(categorieSet);
    }
    private void setupActionColumn() {
        action.setCellFactory(col -> new TableCell<type_ev, Void>() {
            private final Button eveButton = new Button("supprimer");

            {
                eveButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    type_ev e = getTableView().getItems().get(getIndex());
                    supprimer(e);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(eveButton);
                }
            }
        });
    }
    private void supprimer(type_ev ev) {
        type_evService p = new type_evService();
        p.supprimer(ev);

        viewevent.getItems().remove(ev);
    }
    @FXML
    public boolean validerChamps() {
        if (catev.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        if (!catev.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'Noneve' doit contenir uniquement des lettres.");
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
        ObservableList<type_ev> filteredList = viewevent.getItems().filtered(type_ev ->
                type_ev.getType_ev().toLowerCase().contains(searchText.toLowerCase()) );

        // Update the TableView with the filtered list
        viewevent.setItems(filteredList);
    }

}
