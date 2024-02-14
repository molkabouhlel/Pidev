package com.esprit.models;

import java.sql.Time;

public class Espace {
    public Espace(int id_espace, String nom_espace, String categorie, String description_espace, Time heure_overture, String disponibilite) {
        this.id_espace = id_espace;
        this.nom_espace = nom_espace;
        this.categorie = categorie;
        this.description_espace = description_espace;
        this.heure_overture = heure_overture;
        this.disponibilite = disponibilite;
    }

    private int id_espace;
    private String nom_espace;
    private String categorie;
    private String description_espace;
    private Time heure_overture;
    private String disponibilite;

    public Espace(String nom_espace, String categorie, String description_espace, Time heure_overture, String disponibilite) {
        this.nom_espace = nom_espace;
        this.categorie = categorie;
        this.description_espace = description_espace;
        this.heure_overture = heure_overture;
        this.disponibilite = disponibilite;
    }

    public Espace(int id_espace) {
        this.id_espace = id_espace;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription_espace() {
        return description_espace;
    }

    public void setDescription_espace(String description_espace) {
        this.description_espace = description_espace;
    }

    public Time getHeure_overture() {
        return heure_overture;
    }

    public void setHeure_overture(Time heure_overture) {
        this.heure_overture = heure_overture;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Override
    public String toString() {
        return "\n" +"Espace{" +
                "id_espace=" + id_espace +
                ", nom_espace='" + nom_espace + '\'' +
                ", categorie='" + categorie + '\'' +
                ", description_espace='" + description_espace + '\'' +
                ", heure_overture=" + heure_overture +
                ", disponibilite='" + disponibilite + '\'' +
                '}';
    }
}
