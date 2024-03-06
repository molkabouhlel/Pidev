package com.esprit.models;

public class ParticipantClub {
    private int id_user;
    private Club nom_club;

    public ParticipantClub(int id_user, Club nom_club) {
        this.id_user = id_user;
        this.nom_club = nom_club;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Club getNom_club() {
        return nom_club;
    }

    public void setNom_club(Club nom_club) {
        this.nom_club = nom_club;
    }
}
