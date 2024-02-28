package com.esprit.activite.tests;


import com.esprit.activite.modeles.*;
import com.esprit.activite.services.CategorieService;
import com.esprit.activite.services.EquipementService;
import com.esprit.activite.services.MaintenanceService;
import com.esprit.activite.services.Rendez_vousService;
import com.esprit.activite.utils.DataSource;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class MainProg {
    public static void main(String[] args) {
        EquipementService eq = new EquipementService();
      // eq.ajouter(new Equipement( "test","bike", "descc",8 ,5,1,new Categorie_eq(2),new Maintenance_eq(2),"test"));
      //eq.modifier(new Equipement( "molka","bike", "descc",8 ,5,1,new Categorie_eq(2),new Maintenance_eq(2),"test"));
       //eq.supprimer(new Equipement( "test"));
       System.out.println(eq.afficher());
       eq.rechercheeq("ijk");

    Rendez_vousService rv = new Rendez_vousService();
    //rv.ajouter(new Rendez_vous(Timestamp.valueOf("2024-02-17 12:34:56"),new Equipement("molka"),5));
  //  rv.modifier(new Rendez_vous(Timestamp.valueOf("2022-04-16 10:34:56"),8,new Equipement("molka"),5));
  // rv.supprimer(new Rendez_vous( 8));
         //System.out.println(rv.afficher());
        CategorieService c=new CategorieService();
       // c.ajouter(new Categorie_eq("test"));
        //c.modifier(new Categorie_eq(1,"test2"));
        //c.supprimer((new Categorie_eq(1)));
       // System.out.println(c.afficher());
       MaintenanceService m=new MaintenanceService();
              // m.ajouter(new Maintenance_eq(Timestamp.valueOf("2024-02-17 12:34:56"), etat_m.maintenu));
              // m.modifier(new Maintenance_eq(1,Timestamp.valueOf("2024-02-18 12:34:56"), etat_m.maintenu));
                //m.supprimer(new Maintenance_eq(1));
        //System.out.println(m.afficher());
    }

}
