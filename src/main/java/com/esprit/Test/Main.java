package com.esprit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esprit.Models.*;
import com.esprit.Services.*;
import com.esprit.Utils.*;

public class Main {

public static void main(String args[]){
    UserServices us = new UserServices();
    //us.add(new Admin("tttt","ttt","8988988898","00000046",5,"Admin"));
    //us.add(new Membre("fff","fff","ff","54544fff6",888,"Membre","aboone"));
    //us.add(new Coach("dorra.benarbi@gmail.com","cdddd","benarbi","dorra",5,"Coach","Ariana"));

    //us.update (new User(9,"ramiiiii","cdddd","555","545dfs446",5,"Admin"));
    //us.update(new Membre( 11,"mailouk","cdss","555","545446",5,"Membre","aboone"));
   // us.update(new Coach(13,"dorrabenarbi@esprit.tn","cdaaa","555","545446",5,"Coach","adresse"));

    //us.supp(new User(2));

    System.out.println(us.show());



}

}
