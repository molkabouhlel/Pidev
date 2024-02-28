package com.esprit.tests;


import com.esprit.models.ListCours;
import com.esprit.services.EspaceService;
import com.esprit.models.Espace;
import com.esprit.services.ClubService;
import com.esprit.services.ListCoursService;
import java.sql.Time;
import com.esprit.models.Club;
import com.esprit.utils.DataSource;


public class MainProg {

    public static void main(String[] args) {
        EspaceService es = new EspaceService();

        //es.ajouter(new Espace("nom", "desc", Time.valueOf("10:30:00"), Time.valueOf("12:30:00") ));
        //es.modifier(new Espace(5,"Helo", "desc", Time.valueOf("11:30:00"), Time.valueOf("12:30:00")));

        //es.supprimer(new Espace(4));

        System.out.println("voici liste espace:");
        System.out.println(es.afficher());


        ClubService cs = new ClubService();

        //cs.ajouter(new Club("nom_club", "adresse", "desc","https://www.california-gym.com/tn/wp-content/uploads/2022/11/CG-Bardo.jpg",Time.valueOf("11:30:00"),new Espace(5)));
        //cs.modifier(new Club(9,"Yoga", "adresse", "desc","https://www.california-gym.com/tn/wp-content/uploads/2022/11/CG-Bardo.jpg",Time.valueOf("11:30:00"),new Espace(7) ));
        //cs.supprimer(new Club(6));

        /*System.out.println("voici liste club:");
        System.out.println(cs.afficher());*/


        ListCoursService lc = new ListCoursService();
        //lc.ajouter(new ListCours(new Club(9),1));
        //lc.modifier(new ListCours(7,new Club(9),2));
        //lc.supprimer(new ListCours(2));

        /*System.out.println("voici liste cours:");
        System.out.println(lc.afficher());*/

    }
}
