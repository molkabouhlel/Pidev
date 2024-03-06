package com.esprit.activite.modeles;

public class ListCours {

    private int id;
    private Club club;
    private int id_cours;

    public ListCours() {
    }

    public ListCours(int id) {
        this.id_cours = id;
    }

    public ListCours(Club club, int id_cours) {
        this.club = club;
        this.id_cours = id_cours;
    }

    public ListCours(int id, Club club, int id_cours) {
        this.id = id;
        this.club = club;
        this.id_cours = id_cours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    @Override
    public String toString() {
        return "ListCours{" +
                "id=" + id +
                ", club=" + club +
                ", id_cours=" + id_cours +
                '}' +"\n";
    }
}
