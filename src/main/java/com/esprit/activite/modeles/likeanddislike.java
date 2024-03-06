package com.esprit.activite.modeles;

public class likeanddislike {
   private int id_l;
  private   int like_cours;
    private int dislike;
   private Cours id_cours;

    public likeanddislike(int id_l, int like_cours, int dislike, Cours id_cours) {
        this.id_l = id_l;
        this.like_cours = like_cours;
        this.dislike = dislike;
        this.id_cours = id_cours;
    }

    public likeanddislike(int like_cours, int dislike, Cours id_cours) {
        this.like_cours = like_cours;
        this.dislike = dislike;
        this.id_cours = id_cours;
    }

    public likeanddislike(int id_l) {
        this.id_l = id_l;
    }

    public likeanddislike() {

    }

    public int getId_l() {
        return id_l;
    }

    public void setId_l(int id_l) {
        this.id_l = id_l;
    }

    public int getLike_cours() {
        return like_cours;
    }

    public void setLike_cours(int like_cours) {
        this.like_cours = like_cours;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public Cours getId_cours() {
        return id_cours;
    }

    public void setId_cours(Cours id_cours) {
        this.id_cours = id_cours;
    }

    @Override
    public String toString() {
        return "likeanddislike{" +
                "id_l=" + id_l +
                ", like_cours=" + like_cours +
                ", dislike=" + dislike +
                ", id_cours=" + id_cours +
                '}';
    }

}
