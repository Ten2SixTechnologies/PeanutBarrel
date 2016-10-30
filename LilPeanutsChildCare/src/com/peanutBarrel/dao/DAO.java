package com.peanutBarrel.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.peanutBarrel.data.ChildPicture;
import com.peanutBarrel.runtimeExceptions.DatabaseConnectivityException;
import com.peanutBarrel.services.DatabaseServices;

public abstract class DAO
{

    public DAO()
    {
    }

    public static ResultSet executeQuery(String query)
    {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try
        {
            con = DatabaseServices.getConnection();
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.executeQuery();
            con.commit();
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DAO.executeQuery", e);
        }
        return rs;
    }

    public static void executeUpdate(String query)
    {
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            con = DatabaseServices.getConnection();
            stmt = con.prepareStatement(query, 1);
            stmt.executeUpdate();
            con.commit();
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DAO.executeUpdate", e);
        }
    }

    public static Long executeInsert(String query)
    {
        resetAutoIncrement(getTableName(query));
        Connection con = null;
        ResultSet generatedKeys = null;
        PreparedStatement stmt = null;
        Long insertedEntityID = null;
        try
        {
            con = DatabaseServices.getConnection();
            stmt = con.prepareStatement(query, 1);
            stmt.executeUpdate();
            con.commit();
            generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next())
            {
                insertedEntityID = Long.valueOf(generatedKeys.getLong(1));
            }
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DAO.executeInsert", e);
        }
        return insertedEntityID;
    }

    public static Boolean executeInsert(ChildPicture picture, Long childInfoId)
    {
        resetAutoIncrement("ChildInfo");
        Boolean successfull = Boolean.valueOf(false);
        Connection con = DatabaseServices.getConnection();
        try
        {
            PreparedStatement pst = con.prepareStatement("INSERT INTO ChildInfo (Picture) VALUES(?) WHERE ChildInfo_ID = ?");
            File file = new File(picture.getFilePath());
            FileInputStream fis = new FileInputStream(file);
            pst.setBinaryStream(1, fis, fis.available());
            pst.setLong(2, childInfoId.longValue());
            con.commit();
            successfull = Boolean.valueOf(true);
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DAO.executeInsert(ChildPicture)", e);
        }
        return successfull;
    }

    private static void resetAutoIncrement(String tableName)
    {
        int nextValue = getNextValueForPrimaryKey(tableName);
        String sql = (new StringBuilder("ALTER TABLE ")).append(tableName).append(" AUTO_INCREMENT = ").append(nextValue).toString();
        Connection con = null;
        PreparedStatement stmt = null;
        try
        {
            con = DatabaseServices.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
            con.commit();
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DAO.resetAutoIncrement", e);
        }
    }

    private static int getNextValueForPrimaryKey(String tableName)
    {
        int nextValue = 0;
        String sql = (new StringBuilder("Select max(")).append(tableName).append("_id) as ").append(tableName).append("_id from ").append(tableName).toString();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            con = DatabaseServices.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            con.commit();
            if(rs.next())
            {
                nextValue = rs.getInt((new StringBuilder(String.valueOf(tableName))).append("_id").toString()) + 1;
            }
        }
        catch(Exception e)
        {
            throw new DatabaseConnectivityException("DAO.resetAutoIncrement", e);
        }
        return nextValue;
    }

    private static String getTableName(String query)
    {
        String insertStmt = query.toLowerCase();
        int intoIndex = insertStmt.indexOf("into") + 5;
        insertStmt = insertStmt.substring(intoIndex);
        int valuesIndex = insertStmt.indexOf(" ");
        String tableName = insertStmt.substring(0, valuesIndex);
        return tableName;
    }
}
