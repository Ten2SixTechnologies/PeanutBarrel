package com.peanutBarrel.services;

import com.peanutBarrel.runtimeExceptions.DatabaseConnectivityException;
import java.sql.*;

public class DatabaseServices
{
    //Production connection
//	  static final String PASSWORD = "ja121905";
//    static final String URL = "jdbc:mysql://mysql-LilPeanutsChildCare.jelastic.servint.net:3306/peanutbarreldb";
//    static final String USERNAME = "code_connection";
    
    //Aaron's Local Connection
	static final String PASSWORD = "TenToSix17501026!!";
    static final String URL = "jdbc:mysql://localhost:3306/peanutbarrel";
    static final String USERNAME = "peanutbarrel";

    static Connection con = null;

    public DatabaseServices()
    {
    }

    public static Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            con.setAutoCommit(false);
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DatabaseServices.getConnection", e);
        }
        return con;
    }

    public static Connection getCurrentConnectionInstance()
    {
        return con;
    }

    public static void closeCurrentConnection()
    {
        try
        {
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
