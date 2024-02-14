package com.esprit.models;

public class Club {

    private int id_club;
    private String nom_club;
    private String description_club;
    private int capacite;
    private int id_cours;
    private int id_espace;
    private int id_usr;

    public Club(int id_club, String nom_club, String description_club, int capacite, int id_cours, int id_espace, int id_usr) {
        this.id_club = id_club;
        this.nom_club = nom_club;
        this.description_club = description_club;
        this.capacite = capacite;
        this.id_cours = id_cours;
        this.id_espace = id_espace;
        this.id_usr = id_usr;
    }

    public Club(String nom_club, String description_club, int capacite, int id_cours, int id_espace, int id_usr) {
        this.nom_club = nom_club;
        this.description_club = description_club;
        this.capacite = capacite;
        this.id_cours = id_cours;
        this.id_espace = id_espace;
        this.id_usr = id_usr;
    }

    public Club(int id_club) {
        this.id_club = id_club;
    }

    public int getId_club() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    public String getNom_club() {
        return nom_club;
    }

    public void setNom_club(String nom_club) {
        this.nom_club = nom_club;
    }

    public String getDescription_club() {
        return description_club;
    }

    public void setDescription_club(String description_club) {
        this.description_club = description_club;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public int getId_espace() {
        return id_espace;
    }

    public void setId_espace(int id_espace) {
        this.id_espace = id_espace;
    }

    public int getId_usr() {
        return id_usr;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }

    @Override
    public String toString() {
        return "\n"+ "Club{" +
                "id_club=" + id_club +
                ", nom_club='" + nom_club + '\'' +
                ", description_club='" + description_club + '\'' +
                ", capacite=" + capacite +
                ", id_cours=" + id_cours +
                ", id_espace=" + id_espace +
                ", id_usr=" + id_usr +
                '}';
    }
}
