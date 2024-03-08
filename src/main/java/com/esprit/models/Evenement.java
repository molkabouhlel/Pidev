package com.esprit.models;

import com.esprit.models.Espace;
import com.esprit.models.typec;

import java.sql.Timestamp;

public class Evenement {
    private int  id_ev	;
    private String nom_ev;
    private String description_ev;
    private String image_ev	;
    private Timestamp date_debut;
    private Timestamp date_fin	;
    private int capacite_max;
    private Espace id_espace;
    private typec id_typec;
    private type_ev id_type_ev;
    public Evenement(){}

    public Evenement(int id_ev, String nom_ev, String description_ev, String image_ev, Timestamp date_debut, Timestamp date_fin, int capacite_max,Espace id_espace, typec id_typec, type_ev id_type_ev) {
        this.id_ev = id_ev;
        this.nom_ev = nom_ev;
        this.description_ev = description_ev;
        this.image_ev = image_ev;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.capacite_max = capacite_max;
        this.id_espace = id_espace;
        this.id_typec = id_typec;
        this.id_type_ev = id_type_ev;
    }

    public Evenement(String nom_ev, String description_ev, String image_ev, Timestamp date_debut, Timestamp date_fin, int capacite_max, Espace id_espace, typec id_typec, type_ev id_type_ev) {
        this.nom_ev = nom_ev;
        this.description_ev = description_ev;
        this.image_ev = image_ev;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.capacite_max = capacite_max;
        this.id_espace = id_espace;
        this.id_typec = id_typec;
        this.id_type_ev = id_type_ev;
    }

    public Evenement(int id_ev) {
        this.id_ev = id_ev;
    }


    public int getId_ev() {
        return id_ev;
    }

    public void setId_ev(int id_ev) {
        this.id_ev = id_ev;
    }

    public String getNom_ev() {
        return nom_ev;
    }

    public void setNom_ev(String nom_ev) {
        this.nom_ev = nom_ev;
    }

    public String getDescription_ev() {
        return description_ev;
    }

    public void setDescription_ev(String description_ev) {
        this.description_ev = description_ev;
    }

    public String getImage_ev() {
        return image_ev;
    }

    public void setImage_ev(String image_ev) {
        this.image_ev = image_ev;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    public int getCapacite_max() {
        return capacite_max;
    }

    public void setCapacite_max(int capacite_max) {
        this.capacite_max = capacite_max;
    }


    public Espace getId_espace() {
        return id_espace;
    }

    public void setId_espace(Espace id_espace) {
        this.id_espace = id_espace;
    }

    public typec getId_typec() {
        return id_typec;
    }

    public void setId_typec(typec id_typec) {
        this.id_typec = id_typec;
    }

    public type_ev getId_type_ev() {
        return id_type_ev;
    }

    public void setId_type_ev(type_ev id_type_ev) {
        this.id_type_ev = id_type_ev;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_ev=" + id_ev +
                ", nom_ev='" + nom_ev + '\'' +
                ", description_ev='" + description_ev + '\'' +
                ", image_ev='" + image_ev + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", capacite_max=" + capacite_max +
                ", id_espace=" + id_espace +
                ", id_typec=" + id_typec +
                ", id_type_ev=" + id_type_ev +
                '}'+"\n";
    }
}
