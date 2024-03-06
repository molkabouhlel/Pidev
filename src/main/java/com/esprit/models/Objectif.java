package com.esprit.models;

import javafx.collections.ObservableList;

public class Objectif {
    private int ID_obj;
    private int ID_cours;
    private Programme programme ;
    private String description_obj ;

    public Objectif() {
    }

    public Objectif(int ID_obj, int ID_cours, Programme programme, String description_obj) {
        this.ID_obj = ID_obj;
        this.ID_cours = ID_cours;
        this.programme = programme;
        this.description_obj = description_obj;
    }

    public Objectif(int ID_cours, Programme programme, String description_obj) {
        this.ID_cours = ID_cours;
        this.programme = programme;
        this.description_obj = description_obj;
    }

    public Objectif(int ID_obj) {
        this.ID_obj = ID_obj;
    }

    public Objectif(String text) {
    }

    public static void setItems(ObservableList<Objectif> objectifs) {
    }


    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public int getID_obj() {
        return ID_obj;
    }

    public void setID_obj(int ID_obj) {
        this.ID_obj = ID_obj;
    }

    public int getID_cours() {
        return ID_cours;
    }

    public void setID_cours(int ID_cours) {
        this.ID_cours = ID_cours;
    }



    public String getDescription_obj() {
        return description_obj;
    }

    public void setDescription_obj(String description_obj) {
        this.description_obj = description_obj;
    }

    @Override
    public String toString() {
        return "Objectif{" +
                "ID_obj=" + ID_obj +
                ", ID_cours=" + ID_cours +
                ", programme=" + programme +
                ", description_obj='" + description_obj + '\'' +
                '}';
    }
}

