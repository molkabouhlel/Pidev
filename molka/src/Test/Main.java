package Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Models.Coach;
import Models.Membre;
import Models.User;
import Services.UserServices;
import Utils.Connexion;
public class Main {

public static void main(String args[]){
    UserServices us = new UserServices();
    //us.add(new User("mail","cd","555","545446",5,"Admin"));
    //us.add(new Membre("fff","fff","ff","54544fff6",888,"Membre","aboone"));
    //us.add(new Coach("mail","cd","555","545446",5,"Coach","adresse"));

    //us.update (new User(9,"maikqsldl","cd","555","545446",5,"Admin"));
    //us.update(new Membre(11,"mailouk","cdss","555","545446",5,"Membre","aboone"));
    //us.update(new Coach(13,"MAIL","cd","555","545446",5,"Coach","adresse"));

    //us.supp(new User(12));

    System.out.println(us.show());



}

}
