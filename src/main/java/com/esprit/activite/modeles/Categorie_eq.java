package com.esprit.activite.modeles;

public class Categorie_eq {
    private int id_ceq;
    private String type_ceq;

    public Categorie_eq(int id_ceq) {
        this.id_ceq = id_ceq;
    }
    public Categorie_eq(){};
    public Categorie_eq(int id_ceq, String type_ceq) {
        this.id_ceq = id_ceq;
        this.type_ceq = type_ceq;
    }

    public Categorie_eq(String type_ceq) {
        this.type_ceq = type_ceq;
    }

    public int getId_ceq() {
        return id_ceq;
    }

    public void setId_ceq(int id_ceq) {
        this.id_ceq = id_ceq;
    }

    public String getType_ceq() {
        return type_ceq;
    }

    public void setType_ceq(String type_ceq) {
        this.type_ceq = type_ceq;
    }

    @Override
    public String toString() {
        return "Categorie_eq{" +
                "id_ceq=" + id_ceq +
                ", type_ceq='" + type_ceq + '\'' +
                '}';
    }
}