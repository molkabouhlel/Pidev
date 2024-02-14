package com.esprit.tests;


import com.esprit.services.EspaceService;
import com.esprit.models.Espace;
import com.esprit.services.ClubService;
import com.esprit.models.Club;
import com.esprit.utils.DataSource;

public class MainProg {

    public static void main(String[] args) {
        EspaceService es = new EspaceService();
/*
        es.ajouter(new Espace("nom", "categorie", "description_espace", Time.valueOf("10:30:00") ,"disponible"));
        es.modifier(new Espace(1, "nom", "categorie", "description_espace", Time.valueOf("10:30:00") ," non disponible"));

        es.supprimer(new Espace(1));

        System.out.println("voici liste espace:");
        System.out.println(es.afficher());
*/

        ClubService cs = new ClubService();
        /*
        cs.ajouter(new Club("nom_club", "description_club", 10,9,3,1));
        cs.modifier(new Club(3, "NOM", "description_club", 10,9,3,1));
        cs.supprimer(new Club(3));
*/
        System.out.println("voici liste club:");
        System.out.println(cs.afficher());

    }
}
