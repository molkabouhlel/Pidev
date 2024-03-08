package com.esprit.models;

public class Produit {
    private int id;
    private String desc;
    private float prix;
    private int quant;
    private String marque;
    private Categorie id_categorie;
    private String image;
    private String sku;
    public Produit(){}

    public Produit(int id, String desc, float prix, int quant, String marque, Categorie id_categorie, String image, String sku) {
        this.id = id;
        this.desc = desc;
        this.prix = prix;
        this.quant = quant;
        this.marque = marque;
        this.id_categorie = id_categorie;
        this.image = image;
        this.sku = sku;
    }

    public Produit(String desc, float prix, int quant, String marque, Categorie id_categorie, String image, String sku) {
        this.desc = desc;
        this.prix = prix;
        this.quant = quant;
        this.marque = marque;
        this.id_categorie = id_categorie;
        this.image = image;
        this.sku = sku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Categorie getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(Categorie id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", prix=" + prix +
                ", quant=" + quant +
                ", marque='" + marque + '\'' +
                ", id_categorie=" + id_categorie +
                ", image='" + image + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }
}

