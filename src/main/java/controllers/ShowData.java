
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.Models.Coach;
import com.esprit.Models.User;
import com.esprit.Services.UserServices;
import controllers.upcontroller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShowData {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button retouraff;

    @FXML
    private URL location;


    @FXML
    private Button update;
    @FXML
    private Button delete;


    @FXML
    private TableColumn<?, ?> abn0;

    @FXML
    private TableColumn<?, ?> adr0;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> nom;

    @FXML
    private TableColumn<?, ?> numt0;
    @FXML
    private TableColumn<?, ?> role;

    @FXML
    private TableColumn<User, String> prenom;

    @FXML
    private TableView<User> showtab;
    public int idusrselected;

    @FXML
    void initialize() {

        UserServices c = new UserServices();
        List<User> users = c.show();
        System.out.println(c.show());
        ObservableList<User> observableList = FXCollections.observableList(users);
        showtab.setItems(observableList);
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prénom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        adr0.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        numt0.setCellValueFactory(new PropertyValueFactory<>("numT"));
        abn0.setCellValueFactory(new PropertyValueFactory<>("abonnement"));
        //affichage des colonnes:
        showtab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idusrselected = newSelection.getCin();
            } else {
                idusrselected = -1;
            }
        });


    }
    @FXML
    void supp(ActionEvent event) {


        UserServices us = new UserServices();

        int selectedID = showtab.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            User user = showtab.getItems().get(selectedID);

            us.supp(user);

            // Mettez à jour la TableView
            showtab.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("supp test");
            alerte.show();
        }

    }


    @FXML
    void retouraff(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any

        }

    }



    public void update(ActionEvent actionEvent) throws IOException {

       if (idusrselected != -1) {
            UserServices us = new UserServices();
            Object o =new User();
             o=new User();
            Coach c =new Coach();
            //TODO: changer méthode recherche
            // User_Selected = us.rechercheUser(idusrselected);
           o =us.rechercheUser(idusrselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateForm.fxml"));
            Parent root = loader.load();
            upcontroller  upcontroller= loader.getController();

            upcontroller.initDataU((User) o);
           System.out.println(o);
           // upcontroller.initDataC(c);
            Stage currentStage = (Stage) showtab.getScene().getWindow();
            currentStage.setScene(new Scene(root));//reload page
        }

    }
}
