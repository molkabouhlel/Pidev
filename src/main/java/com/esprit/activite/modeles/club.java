package com.esprit.activite.modeles;

import java.sql.Time;

public class club {

   private int id_club;
    private String nom_club;
   private String adresse_club;
    private String description_club;
  private String  image_club;
    private Time temp_ouverture;
    private int id_espace;

    public club(int id_club, String nom_club, String adresse_club, String description_club, String image_club, Time temp_ouverture, int id_espace) {
        this.id_club = id_club;
        this.nom_club = nom_club;
        this.adresse_club = adresse_club;
        this.description_club = description_club;
        this.image_club = image_club;
        this.temp_ouverture = temp_ouverture;
        this.id_espace = id_espace;
    }

    public club(String nom_club, String adresse_club, String description_club, String image_club, Time temp_ouverture, int id_espace) {
        this.nom_club = nom_club;
        this.adresse_club = adresse_club;
        this.description_club = description_club;
        this.image_club = image_club;
        this.temp_ouverture = temp_ouverture;
        this.id_espace = id_espace;
    }

    public club() {

    }

    public int getId_club() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    public String getAdresse_club() {
        return adresse_club;
    }

    public void setAdresse_club(String adresse_club) {
        this.adresse_club = adresse_club;
    }

    public String getImage_club() {
        return image_club;
    }

    public void setImage_club(String image_club) {
        this.image_club = image_club;
    }

    public int getId_espace() {
        return id_espace;
    }

    public void setId_espace(int id_espace) {
        this.id_espace = id_espace;
    }

    public String getNom_club() {
        return nom_club;
    }

    public void setNom_club(String nom_club) {
        this.nom_club = nom_club;
    }

    public String getDescription_club() {
        return description_club;
    }

    public void setDescription_club(String description_club) {
        this.description_club = description_club;
    }

    public Time getTemp_ouverture() {
        return temp_ouverture;
    }

    public void setTemp_ouverture(Time temp_ouverture) {
        this.temp_ouverture = temp_ouverture;
    }

    @Override
    public String toString() {
        return "club{" +
                "id_club=" + id_club +
                ", adresse_club='" + adresse_club + '\'' +
                ", image_club='" + image_club + '\'' +
                ", id_espace=" + id_espace +
                ", nom_club='" + nom_club + '\'' +
                ", description_club='" + description_club + '\'' +
                ", temp_ouverture=" + temp_ouverture +
                '}';
    }
}
