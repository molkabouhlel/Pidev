package Models;

import java.util.Objects;

public class User {
   private int cin ;
   private String email ;
    private String mdp ;
    private String nom ;
    private String prénom ;
    private int numT ;
    private String role ;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "cin=" + cin +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nom='" + nom + '\'' +
                ", prénom='" + prénom + '\'' +
                ", numT=" + numT +
                ", role='" + role + '\'' +
                '}' +"\n";
    }

    public User(int cin) {
        this.cin = cin;
    }

    public User(int cin, String email, String mdp, String nom, String prénom, int numT, String role) {
        this.cin = cin;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.prénom = prénom;
        this.numT = numT;
        this.role = role;
    }

    public User(String email, String mdp, String nom, String prénom, int numT, String role) {
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.prénom = prénom;
        this.numT = numT;
        this.role = role;
    }


    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrénom() {
        return prénom;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public int getNumT() {
        return numT;
    }

    public void setNumT(int numT) {
        this.numT = numT;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return cin == user.cin && numT == user.numT && Objects.equals(email, user.email) && Objects.equals(mdp, user.mdp) && Objects.equals(nom, user.nom) && Objects.equals(prénom, user.prénom) && Objects.equals(role, user.role);
    }


}
