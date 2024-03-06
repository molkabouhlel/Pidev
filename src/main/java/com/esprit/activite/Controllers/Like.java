package com.esprit.activite.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Like {

    @FXML
    private Button dislikeButton;

    @FXML
    private Button likeButton;

    @FXML
    private Text likesDislikesLabel;
    private int likes = 0;
    private int dislikes = 0;

    public Button getDislikeButton() {
        return dislikeButton;
    }

    public Button getLikeButton() {
        return likeButton;
    }

    private long lastClickTime = 0;
   // private int count;
    @FXML
    void dislikeAction(ActionEvent event) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            if (dislikes >0)
            dislikes--;
            else
                dislikes=0;
        } else {
            dislikes++;
        }
        lastClickTime = currentTime;
        updateLikesDislikesLabel();
    }

    @FXML
    void likeAction(ActionEvent event) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 1000) {
            if (likes>0)
            likes--;
            else
                likes=0;
        } else {
            likes++;
        }
        lastClickTime = currentTime;
        updateLikesDislikesLabel();
    }
    private void updateLikesDislikesLabel() {
        likesDislikesLabel.setText("Likes: " + likes + " Dislikes: " + dislikes);

    }
}
