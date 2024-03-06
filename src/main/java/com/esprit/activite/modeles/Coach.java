package com.esprit.activite.modeles;
import com.esprit.activite.modeles.User;
public class Coach extends User {
    public Coach(int cin, String email, String mdp, String nom, String prénom, int numT, String role, String adresse) {
        super(cin, email, mdp, nom, prénom, numT, role);
        this.adresse = adresse;
    }

    public Coach() {

    }

    public Coach(String LN, String FN, int TTF, String mail, String mdp, String role, String adresse) {
    }


    @Override
    public String toString() {
        return  super.toString()   + "Coach{" +
                "adresse='" + adresse + '\'' +
                '}' ;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    private String adresse;

    public Coach(String email, String mdp, String nom, String prénom, int numT, String role, String adresse) {
        super(email, mdp, nom, prénom, numT, role);
        this.adresse = adresse;
    }
}

