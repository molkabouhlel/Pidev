package com.esprit.equipement.tests;


import com.esprit.equipement.services.Categorie_eqService;
import com.esprit.equipement.services.EquipementService;
import com.esprit.equipement.services.MaintenanceService;
import com.esprit.equipement.services.Rendez_vousService;


public class MainProg {
    public static void main(String[] args) {
        EquipementService eq = new EquipementService();
      // eq.ajouter(new Equipement( "m","bike", "descc",8 ,5,1,new Categorie_eq(2),new Maintenance_eq(11),"test"));
     // eq.modifier(new Equipement( 4,"molka","bike", "descc",8,1,new Categorie_eq(11),new Maintenance_eq(11),"khghgjhg"));
      // eq.supprimer(new Equipement( 3));
       System.out.println(eq.afficher());

      // eq.rechercheeq("ijk");

    Rendez_vousService rv = new Rendez_vousService();
    //rv.ajouter(new Rendez_vous(Timestamp.valueOf("2024-02-17 12:34:56"),new Equipement("molka"),5));
  //  rv.modifier(new Rendez_vous(Timestamp.valueOf("2022-04-16 10:34:56"),8,new Equipement("molka"),5));
  // rv.supprimer(new Rendez_vous( 8));
         //System.out.println(rv.afficher());
        Categorie_eqService c=new Categorie_eqService();
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
