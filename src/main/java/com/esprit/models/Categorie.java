package com.esprit.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private int id_cat;
    private String nom_cat;
    private String descr;

    public Categorie(){}
    public Categorie(int id_cat, String nom_cat, String descr) {
        this.id_cat = id_cat;
        this.nom_cat = nom_cat;
        this.descr = descr;
    }

    public Categorie(String nom_cat, String descr) {
        this.nom_cat = nom_cat;
        this.descr = descr;
    }

    public int getId_cat() {
        return this.id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNom_cat() {
        return this.nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String toString() {
        return "Categorie{id_cat=" + this.id_cat + ", nom_cat='" + this.nom_cat + "', descr='" + this.descr + "'}";
    }


}
