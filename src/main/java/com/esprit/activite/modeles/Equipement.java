package com.esprit.activite.modeles;


import java.util.Date;

public class Equipement {
    private int id_eq;
    private String ref_eq;
    private String nom_eq;
    private String description_eq;
    private int quantite_dispo;
    //private int id_coach;
    private int id_espace;
   private Categorie_eq id_ceq;
   private Maintenance_eq id_m;
   private String image;

public Equipement(){}
    //constructeur ajout
    public Equipement(String ref_eq, String nom_eq, String description_eq, int quantite_dispo,  int id_espace, Categorie_eq id_ceq, Maintenance_eq id_m, String image) {
        this.ref_eq = ref_eq;
        this.nom_eq = nom_eq;
        this.description_eq = description_eq;
        this.quantite_dispo = quantite_dispo;
      //  this.id_coach= id_coach;
        this.id_espace = id_espace;
        this.id_ceq = id_ceq;
        this.id_m = id_m;
        this.image = image;
    }

    public Equipement(int id_eq, String ref_eq, String nom_eq, String description_eq, int quantite_dispo, int id_espace, Categorie_eq id_ceq, Maintenance_eq id_m, String image) {
       this.id_eq= id_eq;
        this.ref_eq = ref_eq;
        this.nom_eq = nom_eq;
        this.description_eq = description_eq;
        this.quantite_dispo = quantite_dispo;
      //  this.id_coach=id_coach;
        this.id_espace = id_espace;
        this.id_ceq = id_ceq;
        this.id_m = id_m;
        this.image = image;
    }

    public Equipement(int id_eq) {
        this.id_eq = id_eq;
    }

    public Equipement(int id_eq, String nom_eq) {
        this.id_eq = id_eq;
        this.nom_eq = nom_eq;
    }



    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq= id_eq;
    }
    public String getRef_eq() {
        return ref_eq;
    }

    public void setRef_eq(String ref_eq) {
        this.ref_eq = ref_eq;
    }

    public String getNom_eq() {
        return nom_eq;
    }

    public void setNom_eq(String nom_eq) {
        this.nom_eq = nom_eq;
    }

    public String getDescription_eq() {
        return description_eq;
    }

    public void setDescription_eq(String description_eq) {
        this.description_eq = description_eq;
    }

    public int getQuantite_dispo() {
        return quantite_dispo;
    }

    public void setQuantite_dispo(int quantite_dispo) {
        this.quantite_dispo = quantite_dispo;
    }


    public int getId_espace() {
        return id_espace;
    }

    public void setId_espace(int id_espace) {
        this.id_espace = id_espace;
    }

    public Categorie_eq getId_ceq() {
        return id_ceq;
    }

    public void setId_ceq(Categorie_eq id_ceq) {
        this.id_ceq = id_ceq;
    }

    public Maintenance_eq getId_m() {
        return id_m;
    }

    public void setId_m(Maintenance_eq id_m) {
        this.id_m = id_m;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "id_eq="+ id_eq +
                "ref_eq=" + ref_eq +
                ", nom_eq='" + nom_eq + '\'' +
                ", description_eq='" + description_eq + '\'' +
                ", quantite_dispo=" + quantite_dispo +
              //  ",id_coach=" + id_coach +
                ", id_espace=" + id_espace +
                ", id_ceq=" + id_ceq +
                ", id_m=" + id_m +
                ", image='" + image + '\'' +
                '}';
    }
}
