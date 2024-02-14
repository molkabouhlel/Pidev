package com.esprit.models;

import java.util.Date;

public class Objectif {
    private int id_obj;
    private String description_obj;

    public Objectif(int id_obj) {
        this.id_obj = id_obj;
    }

    private Date date_debut;
    private Date date_fin;
    private String statut_obj;
    private String progression;
    private int id_usr;

    public Objectif(int id_obj, String description_obj, Date date_debut, Date date_fin, String statut_obj, String progression, int id_usr) {
        this.id_obj = id_obj;
        this.description_obj = description_obj;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut_obj = statut_obj;
        this.progression = progression;
        this.id_usr = id_usr;
    }

    public Objectif(String description_obj, Date date_debut, Date date_fin, String statut_obj, String progression, int id_usr) {
        this.description_obj = description_obj;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut_obj = statut_obj;
        this.progression = progression;
        this.id_usr = id_usr;
    }

    public int getId_obj() {
        return id_obj;
    }

    public void setId_obj(int id_obj) {
        this.id_obj = id_obj;
    }

    public String getDescription_obj() {
        return description_obj;
    }

    public void setDescription_obj(String description_obj) {
        this.description_obj = description_obj;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getStatut_obj() {
        return statut_obj;
    }

    public void setStatut_obj(String statut_obj) {
        this.statut_obj = statut_obj;
    }

    public String getProgression() {
        return progression;
    }

    public void setProgression(String progression) {
        this.progression = progression;
    }

    public int getId_usr() {
        return id_usr;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }

    @Override
    public String toString() {
        return "Objectif{" +
                "id_obj=" + id_obj +
                ", description_obj='" + description_obj + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", statut_obj='" + statut_obj + '\'' +
                ", progression='" + progression + '\'' +
                ", id_usr=" + id_usr +
                '}';
    }
}

