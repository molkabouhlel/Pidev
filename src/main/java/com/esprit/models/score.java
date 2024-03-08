package com.esprit.models;

public class score {
    private int id_score ;
    private Produit id_produit;
    private Double note;

    public score(int id_score, Produit id_produit, Double note) {
        this.id_score = id_score;
        this.id_produit = id_produit;
        this.note = note;
    }

    public score(Produit id_produit, Double note) {
        this.id_produit = id_produit;
        this.note = note;
    }

    public int getId_score() {
        return id_score;
    }

    public void setId_score(int id_score) {
        this.id_score = id_score;
    }

    public Produit getId_produit() {
        return id_produit;
    }

    public void setId_produit(Produit id_produit) {
        this.id_produit = id_produit;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "score{" +
                "id_score=" + id_score +
                ", id_produit=" + id_produit +
                ", note=" + note +
                '}';
    }
}
