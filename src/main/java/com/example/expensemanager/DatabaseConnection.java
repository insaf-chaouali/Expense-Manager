package com.example.expensemanager;

import java.sql.Connection;
import java.sql.DriverManager;

import static java.lang.Class.forName;

public class DatabaseConnection {
    public Connection databaselink;

    public Connection getConnection(){
        String databaseName="db";
        String databaseUser ="root";
        String databasePassword="MySql13th13;";
        String url="jdbc:mysql://127.0.0.1:3306/expense";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink=DriverManager.getConnection(url,databaseUser,databasePassword);
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
       return databaselink;
    }
}
