package com.esprit.activite.tests;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.*;
import com.esprit.activite.utils.DataSource;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class MainProg {
    public static void main(String[] args) {


        type_evService tev= new type_evService();
       // tev.ajouter(new type_ev("prive"));
        //tev.modifier(new type_ev(3,"privetev"));
        //tev.supprimer(new type_ev("3"));
//        System.out.println(tev.afficher());


        ///////////////typec////////////////////

        TypecService tc=new TypecService();
          // tc.ajouter(new typec("dance"));
       // tc.modifier(new typec(2,"danceupdatetypec"));
       // tc.supprimer(new typec("3"));
       // System.out.println(tc.afficher());


        ///////////:  cours////////////////////////:

        CoursService ps = new CoursService();
       // ps.ajouter(new Cours("dansec" ,"tttttt", "C:/Users/molka/Desktop/R.jpg", Time.valueOf("12:34:56"),1,5,new typec(2)));
        //ps.supprimer(new Cours(11));
       //  ps.modifier(new Cours(12,"dansec" ,"tttttt", "C:/Users/molka/Desktop/R.jpg", Time.valueOf("12:34:56"),1,5,new typec(2)));
       // System.out.println(ps.afficher());
//ps.rechercheCours(16);
        System.out.println(ps.rechercheclub(5));
/////////////////:evenement ////////////////////////////

        EvenementService ev = new EvenementService();

    //   ev.ajouter(new Evenement("dansec" ,"tttttt", "C:/Users/molka/Desktop/R.jpg", Timestamp.valueOf("2024-02-17 12:34:56"),Timestamp.valueOf("2024-02-17 12:34:56") ,1,4,new typec(2),new type_ev(2)));
      //  ev.modifier(new Evenement(9,"danseclassique" ,"tttttt", "test", Timestamp.valueOf("2024-02-17 12:34:56"),Timestamp.valueOf("2024-02-17 12:34:56") ,1,4,new typec(2),new type_ev(2)));
      //  ev.supprimer(new Evenement(8));
    //   System.out.println(ev.afficher());
      //  System.out.println(ev.rechercheIdEspace());
       participerService s=new participerService();
     //   s.ajouter(new Participer(new Cours(24),"Bouhlel","molka","test"));
       // System.out.println( s.participantrecherche("test","test"));
       // s.supprimerParticipation("test");;
      //  System.out.println(s.afficher());
        //s.supprimer(new Participer(8));
      // s.modifier(new Participer(6,"test"));
        System.out.println(   s.rechercherCoursParNom("yoga"));
       // System.out.println(s.rechercheparticipant("testtttt","update"));
        System.out.println( ev.rechercheIdEspace());

    }


}
