package com.esprit.activite.modeles;

public class typec {
     private int idtypec;
     private String typecours;
      public typec(){}
     public typec(int idtypec, String typecours) {
          this.idtypec = idtypec;
          this.typecours = typecours;
     }

     public typec(String typecours) {
          this.typecours = typecours;
     }

     public typec(int idtypec) {
          this.idtypec = idtypec;
     }

     public int getIdtypec() {
          return idtypec;
     }

     public void setIdtypec(int idtypec) {
          this.idtypec = idtypec;
     }

     public String getTypecours() {
          return typecours;
     }

     public void setTypecours(String typecours) {
          this.typecours = typecours;
     }

     @Override
     public String toString() {
          return "typec{" +
                  "idtypec=" + idtypec +
                  ", typecours='" + typecours + '\'' +
                  '}';
     }
}
