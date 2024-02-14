package com.esprit.activite.modeles;


import java.util.Date;

public class Equipement {
    private int id_eq;
    private String nom_eq;
    private String description_eq;
    private int quantite_dispo;
    private String etat;
    private Date date_maintenance;
    private int id_coach;
    private int id_espace;

    public Equipement(int id_eq, String nom_eq, String description_eq, int quantite_dispo, String etat, Date date_maintenance, int id_coach, int id_espace) {
        this.id_eq = id_eq;
        this.nom_eq = nom_eq;
        this.description_eq = description_eq;
        this.quantite_dispo = quantite_dispo;
        this.etat = etat;
        this.date_maintenance = date_maintenance;
        this.id_coach = id_coach;
        this.id_espace = id_espace;
    }

    public Equipement(String nom_eq, String description_eq, int quantite_dispo, String etat, Date date_maintenance, int id_coach, int id_espace) {
        this.nom_eq = nom_eq;
        this.description_eq = description_eq;
        this.quantite_dispo = quantite_dispo;
        this.etat = etat;
        this.date_maintenance = date_maintenance;
        this.id_coach = id_coach;
        this.id_espace = id_espace;
    }

    public Equipement(int id_eq) {
        this.id_eq = id_eq;
    }

    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq = id_eq;
    }

    public String getNom_eq() {
        return nom_eq;
    }

    public void setNom_eq(String nom_eq) {
        this.nom_eq = nom_eq;
    }

    public String getDescription_eq() {
        return description_eq;
    }

    public void setDescription_eq(String description_eq) {
        this.description_eq = description_eq;
    }

    public int getQuantite_dispo() {
        return quantite_dispo;
    }

    public void setQuantite_dispo(int quantite_dispo) {
        this.quantite_dispo = quantite_dispo;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDate_maintenance() {
        return date_maintenance;
    }

    public void setDate_maintenance(Date date_maintenance) {
        this.date_maintenance = date_maintenance;
    }

    public int getId_coach() {
        return id_coach;
    }

    public void setId_coach(int id_coach) {
        this.id_coach = id_coach;
    }

    public int getId_espace() {
        return id_espace;
    }

    public void setId_espace(int id_espace) {
        this.id_espace = id_espace;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "id_eq=" + id_eq +
                ", nom_eq='" + nom_eq + '\'' +
                ", description_eq='" + description_eq + '\'' +
                ", quantite_dispo=" + quantite_dispo +
                ", etat='" + etat + '\'' +
                ", date_maintenance=" + date_maintenance +
                ", id_coach=" + id_coach +
                ", id_espace=" + id_espace +
                '}';
    }
}
