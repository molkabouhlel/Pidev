package com.esprit.tests;

import com.esprit.models.Categorie;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import com.esprit.models.Produit;
import com.esprit.utils.DataSource;

public class MainProg {

    public static void main(String[] args) {
       Categorie cat = new Categorie(11,"prot","test333");
        CategorieService cs = new CategorieService();
      // cs.ajouter(new Categorie("pppppp", "test222 "));
        ProduitService ps = new ProduitService();
     //ps.ajouter(new Produit("pppp",14,15,"tsts",cat,"url_url","skusku"));
       ps.modifier(new Produit(11, "desc",1,23,"fitfit",cat,"urur","456"));
        System.out.println(ps.afficher());
        System.out.println(cs.afficher());
       // cs.afficher();
//ps.ajouter(new Produit(8,"descripppp", 22, 11, "fitnezz", cat,"url"));
//ps.modifier(new Produit(10,"description", 10, 12, "fitfit", cat,"url_url"));
//ps.afficher();
//ps.supprimer(new Produit(9,"descripppp", 22, 11, "fitnezz", cat,"url"));
    }
}
