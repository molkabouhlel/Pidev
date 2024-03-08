package com.esprit.models;

public class type_ev {
    private int id_typeev;
    private String type_ev;
    public type_ev(){}

    public type_ev(int id_typeev, String type_ev) {
        this.id_typeev = id_typeev;
        this.type_ev = type_ev;
    }


    public type_ev(String type_ev) {
        this.type_ev = type_ev;
    }

    public type_ev(int id_typeev) {
        this.id_typeev = id_typeev;
    }

    public int getId_typeev() {
        return id_typeev;
    }

    public void setId_typeev(int id_typeev) {
        this.id_typeev = id_typeev;
    }

    public String getType_ev() {
        return type_ev;
    }

    public void setType_ev(String type_ev) {
        this.type_ev = type_ev;
    }

    @Override
    public String toString() {
        return "type_ev{" +
                "id_typeev=" + id_typeev +
                ", type_ev='" + type_ev + '\'' +
                '}';
    }

}
