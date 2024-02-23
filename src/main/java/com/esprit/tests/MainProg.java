package com.esprit.tests;


import java.sql.Date;
import com.esprit.models.Objectif;
import com.esprit.models.Programme;
import com.esprit.services.ObjectifServices;
import com.esprit.services.ObjectifServices;
import com.esprit.services.ProgrammeServices;
import com.esprit.services.ProgrammeServices;


public class MainProg {

   public static void main(String[] args) {
        ProgrammeServices pg = new ProgrammeServices();

       pg.ajouter(new Programme("kk",54,"ll","oo", Date.valueOf("2010-06-20"), Date.valueOf("2011-06-20"),1));
       // pg.modifier(new Programme(1, kkpLLp,54,loffl,oohh, Date.valueOf("2010-06-20"), Date.valueOf("2011-06-20"),1));

       // pg.supprimer(new Programme(1));

        System.out.println("voici liste programmme:");
        System.out.println(pg.afficher());


        ObjectifServices os = new ObjectifServices();

       //os.ajouter(new Objectif(1,new Programme(1),"MMMMM")) ;


        //os.modifier(new Objectif(1,"kk",Date.valueOf("2010-06-20"),Date.valueOf( "2012-06-20"),"ocupee","progression",1));
        //os.supprimer(new Objectif(2));

        System.out.println("voici liste club:");
        System.out.println(os.afficher());


    }
}
