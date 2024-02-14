package com.esprit.activite.modeles;

import java.sql.Date;
import java.sql.Time;

public class Evenement {
    private int id_ev;
    private String nom_ev;
    private String description_ev;
    private String date_ev;
    private String heure_ev;
    private int  id_espace;
    private int nb_participant;

    public Evenement(int id_ev, String nom_ev, String description_ev, String date_ev, String heure_ev, int id_espace, int nb_participant) {
        this.id_ev = id_ev;
        this.nom_ev = nom_ev;
        this.description_ev = description_ev;
        this.date_ev = date_ev;
        this.heure_ev = heure_ev;
        this.id_espace = id_espace;
        this.nb_participant = nb_participant;
    }

    public Evenement(String nom_ev, String description_ev, String date_ev, String heure_ev, int id_espace, int nb_participant) {
        this.nom_ev = nom_ev;
        this.description_ev = description_ev;
        this.date_ev = date_ev;
        this.heure_ev = heure_ev;
        this.id_espace = id_espace;
        this.nb_participant = nb_participant;
    }

    public String getNom_ev() {
        return nom_ev;
    }

    public void setNom_ev(String nom_ev) {
        this.nom_ev = nom_ev;
    }

    public int getId_ev() {
        return id_ev;
    }

    public void setId_ev(int id_ev) {
        this.id_ev = id_ev;
    }

    public String getDescription_ev() {
        return description_ev;
    }

    public void setDescription_ev(String description_ev) {
        this.description_ev = description_ev;
    }

    public String getDate_ev() {
        return date_ev;
    }

    public void setDate_ev(String date_ev) {
        this.date_ev = date_ev;
    }

    public String getHeure_ev() {
        return heure_ev;
    }

    public void setHeure_ev(String heure_ev) {
        this.heure_ev = heure_ev;
    }

    public int getId_espace() {
        return id_espace;
    }

    public void setId_espace(int id_espace) {
        this.id_espace = id_espace;
    }

    public int getNb_participant() {
        return nb_participant;
    }

    public void setNb_participant(int nb_participant) {
        this.nb_participant = nb_participant;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_ev=" + id_ev +
                ", description_ev='" + description_ev + '\'' +
                ", date_ev=" + date_ev +
                ", heure_ev=" + heure_ev +
                ", id_espace=" + id_espace +
                ", nb_participant=" + nb_participant +
                '}';
    }
}
