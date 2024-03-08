package com.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.models.Coach;
import com.esprit.models.Membre;
import com.esprit.models.User;
import com.esprit.services.UserServices;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class Gestioncoach {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private TableView<Coach> CoachTableView;

    @FXML
    private TableColumn<Coach, String> action;

    @FXML
    private TableColumn<Coach, String> adresse;

    @FXML
    private TableColumn<Coach, String> email;

    @FXML
    private TableColumn<Coach, String> fn;

    @FXML
    private TableColumn<Coach, String> ln;

    @FXML
    private TableColumn<Coach, String> pass;

    @FXML
    private TableColumn<Coach, Integer> pn;
    UserServices userServices = new UserServices();


    @FXML
    void coachI(ActionEvent event) {

    }

    @FXML
    void membreI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionMembre.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            //AddMembre addMembreController = loader.getController();

            // You can perform additional setup or pass data to the new controller if needed

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }

    @FXML
    void returnButton(ActionEvent event) {
    }



    @FXML
    void addcoach(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addCoach.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            //AddMembre addMembreController = loader.getController();

            // You can perform additional setup or pass data to the new controller if needed

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }



    }


    @FXML
    void initialize() {
        UserServices userServices = new UserServices();
        List<Coach> users = userServices.showCoach();
        System.out.println(users);
        ObservableList<Coach> u = FXCollections.observableArrayList(users);

        fn.setCellValueFactory(new PropertyValueFactory<>("prénom"));
        ln.setCellValueFactory(new PropertyValueFactory<>("nom"));
        pn.setCellValueFactory(new PropertyValueFactory<>("numT"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
       // pass.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        CoachTableView.setItems((ObservableList<Coach>) u);


        System.out.println(u);









        /////////////////////////////////////////EDITER SUR TABLE////////////////////////////////////////////
        CoachTableView.setEditable(true);
        fn.setCellFactory(TextFieldTableCell.forTableColumn());
        ln.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setCellFactory(TextFieldTableCell.forTableColumn());
       // pass.setCellFactory(TextFieldTableCell.forTableColumn());
        adresse.setCellFactory(TextFieldTableCell.forTableColumn());
        pn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));




        /////////////////////////////////////////Recupere text TABLE et modifie////////////////////////////////////////////
          UserServices Es = new  UserServices();
        // Save changes on commit
        fn.setOnEditCommit(event -> {
            Coach c = event.getRowValue();
            c.setNom(event.getNewValue());
            Es.updateCoach(c);
        });

        ln.setOnEditCommit(event -> {
            Coach c = event.getRowValue();
            c.setPrénom(event.getNewValue());
            Es.updateCoach(c);
        });
        email.setOnEditCommit(event -> {
            Coach c = event.getRowValue();
            c.setNom(event.getNewValue());
            Es.updateCoach(c);
        });




        pn.setOnEditCommit(event -> {
            Coach c = event.getRowValue();
            c.setNumT(event.getNewValue());
            Es.updateCoach(c);
        });

        adresse.setOnEditCommit(event -> {
            Coach c = event.getRowValue();
            c.setAdresse(event.getNewValue());
            Es.updateCoach(c);
        });









        /////delete
        Callback<TableColumn<Coach, String>, TableCell<Coach, String>> cellFactory =
                new Callback<TableColumn<Coach, String>, TableCell<Coach, String>>() {
                    @Override
                    public TableCell<Coach, String> call(final TableColumn<Coach, String> param) {
                        final TableCell<Coach, String> cell = new TableCell<Coach, String>() {

                            final Button deleteButton = new Button("Delete");

                            {
                                deleteButton.setOnAction((ActionEvent event) -> {
                                    User item = getTableView().getItems().get(getIndex());
                                    // Call your function to delete from the database
                                    userServices.supprimer(item);
                                    getTableView().getItems().remove(item);
                                });
                            }

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setGraphic(deleteButton);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        action.setCellFactory(cellFactory);

    }


    @FXML
    void retour(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuPrincipal.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}