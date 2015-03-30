package com.peanutBarrel.services;

import com.peanutBarrel.runtimeExceptions.DatabaseConnectivityException;
import java.sql.*;

public class DatabaseServices
{

    static final String PASSWORD = "ja121905";
    static Connection con = null;
    static final String URL = "jdbc:mysql://mysql-LilPeanutsChildCare.jelastic.servint.net:3306/peanutbarreldb";
    static final String USERNAME = "code_connection";

    public DatabaseServices()
    {
    }

    public static Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://mysql-LilPeanutsChildCare.jelastic.servint.net:3306/peanutbarreldb", "code_connection", "ja121905");
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
