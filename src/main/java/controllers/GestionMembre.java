package controllers;

import com.esprit.Models.Coach;
import com.esprit.Models.Membre;
import com.esprit.Models.User;
import com.esprit.Services.UserServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestionMembre {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Membre, String> action;

    @FXML
    private TableColumn<Membre,String> fn;

    @FXML
    private TableColumn<Membre,String> ln;

    @FXML
    private TableColumn<Membre,String> mail;

    @FXML
    private TableColumn<Membre,String> mdp;

    @FXML
    private TableColumn<Membre, Integer> pn;

    @FXML
    private TableView<Membre> MemberTableView;

    UserServices userServices = new UserServices();
    List<Membre> users = userServices.showMembre();

    ObservableList<User> u = FXCollections.observableArrayList(userServices.showMembre());

    ObservableList<User> Newu = FXCollections.observableArrayList();



    @FXML
    void coachI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gestioncoach.fxml"));
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
    void membreI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMembre.fxml"));
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


    public void showuser() {


    }

    @FXML
    void ajoutMembre(ActionEvent event) {
        // Load and show the new interface (addAdmin.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addMembre.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddMembre addMembreController = loader.getController();

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
        List<Membre> users = userServices.showMembre();
        ObservableList<Membre> u = FXCollections.observableArrayList(users);
        fn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ln.setCellValueFactory(new PropertyValueFactory<>("prénom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("email"));
       // mdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        pn.setCellValueFactory(new PropertyValueFactory<>("numT"));

        MemberTableView.setItems(u);

        System.out.println(u);



/////////////////////////////////////////EDITER SUR TABLE////////////////////////////////////////////
        MemberTableView.setEditable(true);
        fn.setCellFactory(TextFieldTableCell.forTableColumn());
        ln.setCellFactory(TextFieldTableCell.forTableColumn());
        mail.setCellFactory(TextFieldTableCell.forTableColumn());
        //mdp.setCellFactory(TextFieldTableCell.forTableColumn());
        pn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));




        /////////////////////////////////////////Recupere text TABLE et modifie////////////////////////////////////////////
        UserServices Es = new  UserServices();
        // Save changes on commit
        fn.setOnEditCommit(event -> {
            Membre m = event.getRowValue();
            m.setNom(event.getNewValue());
            Es.updateMembre(m);
        });

        ln.setOnEditCommit(event -> {
            Membre m = event.getRowValue();
            m.setPrénom(event.getNewValue());
            Es.updateMembre(m);
        });
        mail.setOnEditCommit(event -> {
            Membre m = event.getRowValue();
            m.setEmail(event.getNewValue());
            Es.updateMembre(m);
        });


       /* mdp.setOnEditCommit(event -> {
            Membre m = event.getRowValue();
            m.setMdp(event.getNewValue());
            Es.updateMembre(m);
        });*/


        pn.setOnEditCommit(event -> {
            Membre m = event.getRowValue();
            m.setNumT(event.getNewValue());
            Es.updateMembre(m);
        });





        /////delete
        Callback<TableColumn<Membre, String>, TableCell<Membre, String>> cellFactory =
                new Callback<TableColumn<Membre, String>, TableCell<Membre, String>>() {
                    @Override
                    public TableCell<Membre, String> call(final TableColumn<Membre, String> param) {
                        final TableCell<Membre, String> cell = new TableCell<Membre, String>() {

                            final Button deleteButton = new Button("Delete");

                            {
                                deleteButton.setOnAction((ActionEvent event) -> {
                                    User item = getTableView().getItems().get(getIndex());
                                    // Call your function to delete from the database
                                    userServices.supp(item);
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
    void returnButton(ActionEvent event) {

    }

}
