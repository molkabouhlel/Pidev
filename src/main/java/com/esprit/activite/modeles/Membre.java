package com.esprit.activite.modeles;
import com.esprit.activite.modeles.*;
public class Membre extends User {

    public Membre(int cin, String email, String mdp, String nom, String prénom, int numT, String role, String abonnement) {
        super(cin, email, mdp, nom, prénom, numT, role);
        this.abonnement = abonnement;
    }

    public Membre(String email, String mdp, String nom, String prénom, int numT, String role, String abonnement) {
        super(email, mdp, nom, prénom, numT, role);
        this.abonnement = abonnement;
    }

    public Membre(String firstName, String lastName, int i, String email, String password, String membre) {
    }

    private String abonnement ;

    public Membre(String email, String password, String firstName, String lastName, int phoneNumber, String role) {

    }
    public Membre(String LN, String FN, int TTF, String mail, String mdp, String role , String abonement) {
    }

    public Membre() {
        super();
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
