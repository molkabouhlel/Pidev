package com.esprit.controllers;
import com.esprit.models.User;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import com.esprit.services.UserServices;

public class DeleteButtonCellFactory implements Callback<TableColumn<User, String>, TableCell<User, String>> {

    @Override
    public TableCell<User, String> call(TableColumn<User, String> param) {
        return new DeleteButtonCell();
    }

    private static class DeleteButtonCell extends TableCell<User, String> {
        private final Button deleteButton = new Button("Delete");
        private UserServices userServices = new UserServices();

        DeleteButtonCell() {
            deleteButton.setOnAction(event -> {
                User user = getTableView().getItems().get(getIndex());
                // Call your delete method in UserServices
                userServices.supprimer(user);// Replace 'id' with the actual identifier property
                getTableView().getItems().remove(user);
                getTableView().refresh();
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }
}
