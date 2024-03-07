package com.esprit.equipement.modeles;

import java.sql.Timestamp;

public class Maintenance_eq {
    private int id_m;
    private Timestamp date_m	;
        private etat_m etat_m;
   public  Maintenance_eq(){}
    public Maintenance_eq(int id_m, Timestamp date_m, com.esprit.equipement.modeles.etat_m etat_m) {
        this.id_m = id_m;
        this.date_m = date_m;
        this.etat_m = etat_m;
    }

    public Maintenance_eq(Timestamp date_m, com.esprit.equipement.modeles.etat_m etat_m) {
        this.date_m = date_m;
        this.etat_m = etat_m;
    }

    public Maintenance_eq(int id_m) {
        this.id_m = id_m;
    }

    public Maintenance_eq(int id_m, com.esprit.equipement.modeles.etat_m etat_m) {
        this.id_m = id_m;
        this.etat_m = etat_m;
    }

    public int getId_m() {
        return id_m;
    }

    public void setId_m(int id_m) {
        this.id_m = id_m;
    }

    public Timestamp getDate_m() {
        return date_m;
    }

    public void setDate_m(Timestamp date_m) {
        this.date_m = date_m;
    }

    public com.esprit.equipement.modeles.etat_m getEtat_m() {
        return etat_m;
    }

    public void setEtat_m(com.esprit.equipement.modeles.etat_m etat_m) {
        this.etat_m = etat_m;
    }

    @Override
    public String toString() {
        return "Maintenance_eq{" +
                "id_m=" + id_m +
                ", date_m=" + date_m +
                ", etat_m=" + etat_m +
                '}';
    }
}


