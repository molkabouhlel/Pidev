package com.esprit.models;

import java.sql.Time;

public class Espace {
    private int id_espace;
    private String nom_espace;
    private String description_espace;
    private Time heure_debut;
    private Time heure_fin;

    public Espace() {
    }

    public Espace(int id_espace) {
        this.id_espace = id_espace;
    }

    public Espace(int id_espace, String nom_espace) {
        this.id_espace = id_espace;
        this.nom_espace = nom_espace;
    }

    public Espace(int id_espace, String nom_espace, String description_espace, Time heure_debut, Time heure_fin) {
        this.id_espace = id_espace;
        this.nom_espace = nom_espace;
        this.description_espace = description_espace;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public Espace(String nom_espace, String description_espace, Time heure_debut, Time heure_fin) {
        this.nom_espace = nom_espace;
        this.description_espace = description_espace;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public int getId_espace() {
        return id_espace;
    }

    public void setId_espace(int id_espace) {
        this.id_espace = id_espace;
    }

    public String getNom_espace() {
        return nom_espace;
    }

    public void setNom_espace(String nom_espace) {
        this.nom_espace = nom_espace;
    }

    public String getDescription_espace() {
        return description_espace;
    }

    public void setDescription_espace(String description_espace) {
        this.description_espace = description_espace;
    }

    public Time getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Time heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Time getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Time heure_fin) {
        this.heure_fin = heure_fin;
    }

    @Override
    public String toString() {
        return "Espace{" +
                "id_espace=" + id_espace +
                ", nom_espace='" + nom_espace + '\'' +
                ", description_espace='" + description_espace + '\'' +
                ", heure_debut=" + heure_debut +
                ", heure_fin=" + heure_fin +
                '}' +"\n";
    }
}