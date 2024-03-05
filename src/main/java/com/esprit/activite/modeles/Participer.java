package com.esprit.activite.modeles;

import java.util.List;

public class Participer {
    private int id;
    private Cours idcours;
    private String nomCours;
    private String nom;
    private String prenom;
    private String email;
    private  int numT;
    public Participer(Cours idcours, String nomCours, String nom, String prenom, String email,int numT) {
        this.idcours = idcours;
        this.nomCours = nomCours;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numT=numT;
    }


    public Participer(int id) {
        this.id = id;
    }


    public Participer(int id, Cours idcours, String nomCours, String nom, String prenom, String email, int numT) {
        this.id = id;
        this.idcours = idcours;
        this.nomCours = nomCours;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numT=numT;
    }

    public Participer(int id, String nomCours) {
        this.id = id;
        this.nomCours = nomCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public Participer() {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cours getIdcours() {
        return idcours;
    }

    public void setIdcours(Cours idcours) {
        this.idcours = idcours;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public int getNumT() {
        return numT;
    }

    public void setNumT(int numT) {
        this.numT = numT;
    }

    @Override
    public String toString() {
        return "Participer{" +
                "id=" + id +
                ", idcours=" + idcours +
                ", nomCours='" + nomCours + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numT=" + numT +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Participer(String nomCours, String nom, String prenom, String email,int numT) {
        this.nomCours = nomCours;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numT=numT;
    }
}
