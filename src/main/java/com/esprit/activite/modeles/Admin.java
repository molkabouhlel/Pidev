package com.esprit.activite.modeles;
import com.esprit.activite.modeles.User;

public class Admin  extends User{

    public Admin(String email, String mdp, String nom, String prénom, int numT, String role) {
        super(email, mdp, nom, prénom, numT, role);
    }

    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
