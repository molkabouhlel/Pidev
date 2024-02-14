package com.esprit.activite.tests;

//import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Equipement;
//import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.modeles.Rendez_vous;
//import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EquipementService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.Rendez_vousService;
import com.esprit.activite.utils.DataSource;

import java.sql.Date;
import java.sql.Time;



public class MainProg {
    public static void main(String[] args) {
        //cours et activite
      // CoursService ps = new CoursService();
     //ps.ajouter(new Cours("tessssttt" ,"12/1/2024", "30/1/2024", "12h","momo",6,7,"test"));
      // ps.supprimer(new Cours(1,"molka" ,"Ahmed", "12/1/2002", "12/10/2003","14",5,"test"));
      //  ps.modifier(new Cours(2, "test", "1111111111","111111111111","1111111","test1",4,"hello"));
        //ps.ajouter(new Cours(3,"molka" ,"13/02/2024", "12/10/2024", "11h","mimi",5,"test"));
       // System.out.println(ps.afficher());
       // ps.supprimer(new Cours(7,"molka" ,"12/1/2024", "30/1/2024", "12h","momo",6,7,"test"));
       // ps.modifier(new Cours(9,"tesssstttesst" ,"12/1/2024", "30/1/2024", "12h","momo",6,7,"test"));
        //ps.supprimer(new Cours(9,"tessssttt" ,"12/1/2024", "30/1/2024", "12h","momo",6,7,"test"));
       // EvenementService ev = new EvenementService();

     //   ev.ajouter(new Evenement("danse" ,"tttttt", "30/1/2024", "12",6,7));
       //ev.supprimer(new Evenement(1,"molka" ,"tttttt", "30/1/2024", "12",6,7));
       // ev.ajouter(new Evenement("molka111111" ,"tttttt", "30/1/2024", "12",6,7));
       // ev.modifier(new Evenement(4,"molka" ,"tttttt", "30/1/2024", "12",6,7));

        //  ev.modifier(new Evenement(2,"molka111111" ,"tttttt", "30/1/2024", "12",6,7));
       //  ev.supprimer(new Evenement(2,"molka111111" ,"tttttt", "30/1/2024", "12",6,7));

        //System.out.println(ev.afficher());
        /////////////////////////////////////////////////////////////////////////
EquipementService eq = new EquipementService();
       // eq.ajouter(new Equipement( "tapis", "descc1",5, "cv", Date.valueOf("2021-05-22") ,5,1));
      // eq.modifier(new Equipement( 3,"bike", "descc1",5, "cv", Date.valueOf("2021-05-22") ,5,1));
      // eq.supprimer(new Equipement( 3));
       // System.out.println(eq.afficher());

    Rendez_vousService rv = new Rendez_vousService();
    rv.ajouter(new Rendez_vous(Date.valueOf("2023-06-15"),Time.valueOf("7:20:20"),2));
    //rv.modifier(new Rendez_vous(Date.valueOf("2021-05-22"),Time.valueOf("5:30:20"),2,1));
    rv.supprimer(new Rendez_vous( 1));
    //
    }
}
