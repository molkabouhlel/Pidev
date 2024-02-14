package com.esprit.activite.modeles;

import java.sql.Time;
import java.util.Date;

public class Rendez_vous {
    private Date date_rv;
    private Time heure_rv;
    private  int id_rv;
     private int  id_eq ;

    public Rendez_vous(int id_rv) {
        this.id_rv = id_rv;
    }

    public Rendez_vous(Date date_rv, Time heure_rv, int id_eq, int id_rv) {
        this.date_rv = date_rv;
        this.heure_rv = heure_rv;
        this.id_eq = id_eq;
        this.id_rv = id_rv;
    }

    public Rendez_vous(Date date_rv, Time heure_rv, int id_eq) {
        this.date_rv = date_rv;
        this.heure_rv = heure_rv;
        this.id_eq = id_eq;
    }

    public Date getDate_rv() {
        return date_rv;
    }

    public void setDate_rv(Date date_rv) {
        this.date_rv = date_rv;
    }

    public Time getHeure_rv() {
        return heure_rv;
    }

    public void setHeure_rv(Time heure_rv) {
        this.heure_rv = heure_rv;
    }

    public int getId_rv() {
        return id_rv;
    }

    public void setId_rv(int id_rv) {
        this.id_rv = id_rv;
    }

    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq = id_eq;
    }

    @Override
    public String toString() {
        return "Rendez_vous{" +
                "date_rv=" + date_rv +
                ", heure_rv=" + heure_rv +
                ", id_rv=" + id_rv +
                ", id_eq=" + id_eq +
                '}';
    }
}
