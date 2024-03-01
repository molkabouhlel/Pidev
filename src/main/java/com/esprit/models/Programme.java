package com.esprit.models;

import javafx.collections.ObservableList;

import java.util.Date;

public class Programme {
    private int ID_prog;
    private String nom_prog;
    private String desc_prog;
    private float rate;
    private String etat_initial;
    private String etat_final;
    private Date date_debut ;
    private Date date_fin;
    private int ID_user;

    public Programme(int ID_prog, String desc_prog, float rate, String etat_initial, String etat_final, Date date_debut, Date date_fin, int ID_user) {
        this.ID_prog = ID_prog;
        this.desc_prog = desc_prog;
        this.rate = rate;
        this.etat_initial = etat_initial;
        this.etat_final = etat_final;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.ID_user = ID_user;
    }

    public Programme(String nom_prog, String desc_prog, String etat_initial, Date date_debut, Date date_fin, int ID_user) {
        this.nom_prog = nom_prog;
        this.desc_prog = desc_prog;
        this.etat_initial = etat_initial;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.ID_user = ID_user;
    }

    public Programme(int ID_prog, String nom_prog, String desc_prog, String etat_initial, Date date_debut, Date date_fin, int ID_user) {
        this.ID_prog = ID_prog;
        this.nom_prog = nom_prog;
        this.desc_prog = desc_prog;
        this.etat_initial = etat_initial;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.ID_user = ID_user;
    }

    public Programme(int ID_prog, String nom_prog , String desc_prog, float rate, String etat_initial, String etat_final, Date date_debut, Date date_fin, int ID_user) {
        this.ID_prog = ID_prog;
        this.nom_prog = nom_prog;
        this.desc_prog = desc_prog;
        this.rate = rate;
        this.etat_initial = etat_initial;
        this.etat_final = etat_final;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.ID_user = ID_user;
    }

    public Programme(String nom_prog , String desc_prog, float rate, String etat_initial, String etat_final, Date date_debut, Date date_fin, int ID_user) {
        this.nom_prog = nom_prog;
        this.desc_prog = desc_prog;
        this.rate = rate;
        this.etat_initial = etat_initial;
        this.etat_final = etat_final;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.ID_user = ID_user;
    }

    public Programme() {
    }

    public Programme(int ID_prog) {
        this.ID_prog = ID_prog;
    }

    public static void setItems(ObservableList<Programme> programmes) {
    }

    public int getID_prog() {
        return ID_prog;
    }

    public void setID_prog(int ID_prog) {
        this.ID_prog = ID_prog;
    }

    public String getDesc_prog() {
        return desc_prog;
    }

    public String getNom_prog() {
        return nom_prog;
    }

    public void setNom_prog(String nom_prog) {
        this.nom_prog = nom_prog;
    }

    public void setDesc_prog(String desc_prog) {
        this.desc_prog = desc_prog;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getEtat_initial() {
        return etat_initial;
    }

    public void setEtat_initial(String etat_initial) {
        this.etat_initial = etat_initial;
    }

    public String getEtat_final() {
        return etat_final;
    }

    public void setEtat_final(String etat_final) {
        this.etat_final = etat_final;
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

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }





    @Override
    public String toString() {
        return "Programme{" +
                "ID_prog=" + ID_prog +
                ", nom_prog ='" + nom_prog + '\'' +
                ", desc_prog='" + desc_prog + '\'' +
                ", rate=" + rate +
                ", etat_initial='" + etat_initial + '\'' +
                ", etat_final='" + etat_final + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", ID_user=" + ID_user +
                '}'+"\n";
    }
}
