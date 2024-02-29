package controllers;

import com.esprit.Models.User;
import com.esprit.Services.UserServices;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
public class Backadmin {

    @FXML
    private TableView<User> adminTableView;


    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TableColumn<User, String> prenom;

    @FXML
    private TableColumn<User, String> numT;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> role;
    @FXML
    private TableColumn<User, String> deleteColumn;

    private UserServices userServices = new UserServices();

    @FXML
    void coachI(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface (Gestioncoach.fxml)
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Gestioncoach.fxml")));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }



    @FXML
    void statButton(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Stat.fxml")));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void membreI(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gestionMembre.fxml")));
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
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
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
        // Initialize TableColumn manually
        emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getEmail()));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setEditable(true); // Set editable to true
        emailColumn.setOnEditCommit(event -> {
            User editedUser = event.getRowValue();
            editedUser.setEmail(event.getNewValue());
            userServices.updateAdmin(editedUser);
        });
        // Add mouseClicked event to emailColumn

        nom = new TableColumn<>("Nom");
        nom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getNom()));
        nom.setCellFactory(TextFieldTableCell.forTableColumn());
        nom.setEditable(true); // Set editable to true
        nom.setOnEditCommit(event -> {
            User editedUser = event.getRowValue();
            editedUser.setNom(event.getNewValue());
            userServices.updateAdmin(editedUser);
        });


        prenom = new TableColumn<>("Prenom");
        prenom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getPrénom()));
        prenom.setCellFactory(TextFieldTableCell.forTableColumn());
        prenom.setEditable(true); // Set editable to true
        prenom.setOnEditCommit(event -> {
            User editedUser = event.getRowValue();
            editedUser.setPrénom(event.getNewValue());
            userServices.updateAdmin(editedUser);
        });
        numT = new TableColumn<>("NumT");
        numT.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> Integer.toString(cellData.getValue().getNumT())));
        numT.setCellFactory(TextFieldTableCell.forTableColumn());
        numT.setOnEditCommit(event -> {
            User editedUser = event.getRowValue();
            editedUser.setNumT(event.getNewValue() != null ? Integer.parseInt(event.getNewValue()) : 0);
            userServices.updateAdmin(editedUser);
        });
       numT.setEditable(true); // Set editable to true

        passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getMdp()));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setEditable(true); // Set editable to true
        passwordColumn.setOnEditCommit(event -> {
            User editedUser = event.getRowValue();
            editedUser.setMdp(event.getNewValue());
            userServices.updateAdmin(editedUser);
        });

        role = new TableColumn<>("Role");
        role.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getRole()));
        role.setCellFactory(TextFieldTableCell.forTableColumn());
        role.setEditable(true);
        role.setOnEditCommit(event -> {
            User editedUser = event.getRowValue();
            editedUser.setRole(event.getNewValue());
            userServices.updateAdmin(editedUser);
        });
        deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(new DeleteButtonCellFactory());
        deleteColumn.setPrefWidth(70.0);
        adminTableView.getColumns().add(deleteColumn);



        adminTableView.setEditable(true);

        adminTableView.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof TableColumn) {
                TableColumn<?, ?> clickedColumn = (TableColumn<?, ?>) event.getTarget();
                if (clickedColumn.getId() != null && clickedColumn.getId().equals("emailColumn")) {
                    System.out.println("Mouse clicked on Email column");
                }
            }
        });
        // Call showAdmin method during initialization
        showAdmin();
    }

    private void showAdmin() {
        List<User> adminList = userServices.showAdmin();
        ObservableList<User> adminDetails = FXCollections.observableArrayList(adminList);

        // Clear existing columns
        adminTableView.getColumns().clear();

        // Add columns for each attribute
        emailColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getEmail()));
        nom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getNom()));
        prenom.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getPrénom()));
        numT.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> Integer.toString(cellData.getValue().getNumT())));
        passwordColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getMdp()));
        role.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getRole()));

        // Add columns to the table
        adminTableView.getColumns().addAll(emailColumn, nom, prenom, numT, role, passwordColumn);
        adminTableView.getColumns().add(deleteColumn);

        // Set items to the table
        adminTableView.setItems(adminDetails);
        adminTableView.setEditable(true);


    }
    @FXML
    void addAdmin(ActionEvent event) {
        // Load and show the new interface (addAdmin.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addAdmin.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddAdmin addAdminController = loader.getController();

            // You can perform additional setup or pass data to the new controller if needed

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
    // Event handler for editing cells
    @FXML
    void onEditCommit(TableColumn.CellEditEvent<User, String> event) {
        System.out.println("here " );

        User editedUser = adminTableView.getSelectionModel().getSelectedItem();
        String newValue = event.getNewValue();
        System.out.println("Updating user: " + editedUser);

        if (editedUser != null) {
            // Update the corresponding property in the User object
            if (event.getSource() == emailColumn) {
                editedUser.setEmail(newValue);
            } else if (event.getSource() == nom) {
                editedUser.setNom(newValue);
            } else if (event.getSource() == prenom) {
                editedUser.setPrénom(newValue);
            } else if (event.getSource() == numT) {
                editedUser.setNumT(Integer.parseInt(newValue));
            } else if (event.getSource() == passwordColumn) {
                editedUser.setMdp(newValue);
            } else if (event.getSource() == role) {
                editedUser.setRole(newValue);
            }
            System.out.println("Updating user: " + editedUser);
            userServices.updateAdmin(editedUser);


            // Update the underlying data

        }
    }
}

