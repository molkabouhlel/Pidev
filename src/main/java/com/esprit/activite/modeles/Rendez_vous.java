package com.esprit.activite.modeles;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Rendez_vous {
    private Timestamp date_rv;
    private  int id_rv;
    private Equipement id_eq;
     private int  id_coach;

    public Rendez_vous(Timestamp date_rv, int id_rv, Equipement id_eq, int id_coach) {
        this.date_rv = date_rv;
        this.id_rv = id_rv;
        this.id_eq = id_eq;
        this.id_coach = id_coach;
    }

    public Rendez_vous(Timestamp date_rv, Equipement id_eq, int id_coach) {
        this.date_rv = date_rv;
        this.id_eq = id_eq;
        this.id_coach = id_coach;
    }

    public Rendez_vous(int id_rv) {
        this.id_rv = id_rv;
    }

    public Rendez_vous() {

    }

    public Timestamp getDate_rv() {
        return date_rv;
    }

    public void setDate_rv(Timestamp date_rv) {
        this.date_rv = date_rv;
    }

    public int getId_rv() {
        return id_rv;
    }

    public void setId_rv(int id_rv) {
        this.id_rv = id_rv;
    }

    public Equipement getId_eq() {
        return id_eq;
    }

    public void setId_eq(Equipement ref_eq) {
        this.id_eq = ref_eq;
    }

    public int getId_coach() {
        return id_coach;
    }

    public void setId_coach(int id_coach) {
        this.id_coach = id_coach;
    }

    @Override
    public String toString() {
        return "Rendez_vous{" +
                "date_rv=" + date_rv +
                ", id_rv=" + id_rv +
                ", id_eq=" + id_eq +
                ", id_coach=" + id_coach +
                '}';
    }
}
