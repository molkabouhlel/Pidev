package com.esprit.models;

public class Image {
    private int id_image;
    private String url_image;
    private Produit id_produit;

    public Image(int id_image, String url_image, Produit id_produit) {
        this.id_image = id_image;
        this.url_image = url_image;
        this.id_produit = id_produit;
    }

    public Image(String url_image, Produit id_produit) {
        this.url_image = url_image;
        this.id_produit = id_produit;
    }

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public Produit getId_produit() {
        return id_produit;
    }

    public void setId_produit(Produit id_produit) {
        this.id_produit = id_produit;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id_image=" + id_image +
                ", url_image='" + url_image + '\'' +
                ", id_produit=" + id_produit +
                '}';
    }
}
