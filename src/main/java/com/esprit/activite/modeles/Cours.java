package com.esprit.activite.modeles;

public class Cours {
    private int id;
    private String nom;
    private String datedebut;
    private String datefin;
    private String heure;
    private String nomcoach;
    private int idcoach;
    private int nbparticipant;
    private String description;

    public Cours(int id, String nom, String datedebut, String datefin, String heure, String nomcoach, int idcoach, int nbparticipant, String description) {
        this.id = id;
        this.nom = nom;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.heure = heure;
        this.nomcoach = nomcoach;
         this.idcoach= idcoach;
        this.nbparticipant = nbparticipant;
        this.description = description;
    }

    public Cours(String nom, String datedebut, String datefin, String heure, String nomcoach,int idcoach, int nbparticipant, String description) {
        this.nom = nom;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.heure = heure;
        this.nomcoach = nomcoach;
       this.idcoach = idcoach;
        this.nbparticipant = nbparticipant;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatedebut() {
        return datedebut;
    }

    public int getIdcoach() {
        return idcoach;
    }

    public void setIdcoach(int idcoach) {
        this.idcoach = idcoach;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getNomcoach() {
        return nomcoach;
    }

    public void setNomcoach(String nomcoach) {
        this.nomcoach = nomcoach;
    }



    public int getNbparticipant() {
        return nbparticipant;
    }

    public void setNbparticipant(int nbparticipant) {
        this.nbparticipant = nbparticipant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", datedebut='" + datedebut + '\'' +
                ", datefin='" + datefin + '\'' +
                ", heure='" + heure + '\'' +
                ", nomcoach='" + nomcoach + '\'' +
                ", nbparticipant=" + nbparticipant +
                ", description='" + description + '\'' +
                '}';
    }
}
