package com.esprit.activite.modeles;

import java.sql.Date;
import java.sql.Time;

public class Cours {
   private int id;
   private String nom;
   private String description;
   private String imagec;
   private Time duree;
  private int idcoach;
  private int   idclub;
  private  typec id_typec;
  public Cours(){}

    public Cours(int id, String nom, String description, String imagec, Time duree, int idcoach, int idclub, typec id_typec) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.imagec = imagec;
        this.duree = duree;
        this.idcoach = idcoach;
        this.idclub = idclub;
        this.id_typec = id_typec;
    }

    public Cours(String nom, String description, String imagec, Time duree, int idcoach, int idclub, typec id_typec) {
        this.nom = nom;
        this.description = description;
        this.imagec = imagec;
        this.duree = duree;
        this.idcoach = idcoach;
        this.idclub = idclub;
        this.id_typec = id_typec;
    }

    public Cours(int id, String nom, String description, String imagec, Time duree, int idcoach, int idclub) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.imagec = imagec;
        this.duree = duree;
        this.idcoach = idcoach;
        this.idclub = idclub;
    }

    public Cours(int id) {
        this.id = id;
    }


    public Cours(int id, String nomCours) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public typec getId_typec() {
        return id_typec;
    }

    public void setId_typec(typec id_typec) {
        this.id_typec = id_typec;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagec() {
        return imagec;
    }

    public void setImagec(String imagec) {
        this.imagec = imagec;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public int getIdcoach() {
        return idcoach;
    }

    public void setIdcoach(int idcoach) {
        this.idcoach = idcoach;
    }

    public int getIdclub() {
        return idclub;
    }

    public void setIdclub(int idclub) {
        this.idclub = idclub;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", imagec='" + imagec + '\'' +
                ", duree=" + duree +
                ", idcoach=" + idcoach +
                ", idclub=" + idclub +
                ", id_typec=" + id_typec +
                '}'+"\n";
    }
}
