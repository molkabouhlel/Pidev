package com.esprit.Models;

public class Membre extends User{

    public Membre(int cin, String email, String mdp, String nom, String prénom, int numT, String role, String abonnement) {
        super(cin, email, mdp, nom, prénom, numT, role);
        this.abonnement = abonnement;
    }

    public Membre(String email, String mdp, String nom, String prénom, int numT, String role, String abonnement) {
        super(email, mdp, nom, prénom, numT, role);
        this.abonnement = abonnement;
    }

    public Membre() {
    }

    private String abonnement ;

    public Membre(String LN, String FN, int TTF, String mail, String mdp, String role , String abonement) {
    }

    public String getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = abonnement;
    }

    @Override
    public String toString() {
        return  super.toString() + "Membre{" +
                "abonnement='" + abonnement + '\'' +
                '}';
    }
}
