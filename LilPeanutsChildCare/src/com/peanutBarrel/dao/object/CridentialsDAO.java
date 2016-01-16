package com.peanutBarrel.dao.object;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

import com.peanutBarrel.constants.UserType;
import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

public class CridentialsDAO extends DAO
{

    private static final char PASSWORD[] = "moveAlongCitizen,NothingToSeeHere".toCharArray();
    private static final byte SALT[] = {17, 34, 51, 68, 85, 102, 119, -120};

    public CridentialsDAO()
    {
    }

    public static Long persistCridentials(String userName, String password, int userType)
    {
        Long cridentialsId = null;
        try
        {
            cridentialsId = executeInsert((new StringBuilder("INSERT INTO CRIDENTIALS (USER_NAME, PWD, USER_TYPE_ID) VALUES ('"))
            		.append(userName).append("', '")
            		.append(encrypt(password)).append("', ")
            		.append(userType).append(")").toString());
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return cridentialsId;
    }

    public static Long loginValidation(String userName, String password)
    {
        long cridentialsId;
        ResultSet rs;
        cridentialsId = -1L;
        
        rs = executeQuery((new StringBuilder("SELECT * FROM CRIDENTIALS WHERE USER_NAME = '")).append(userName).append("'").toString());
        try
        {
            if(rs.next() && password.equals(decrypt(rs.getString("PWD"))))
            {
                cridentialsId = rs.getInt("Cridentials_ID");
            }
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return Long.valueOf(cridentialsId);
    }

    public static UserType getUserTypeFromCridentialsId(Long cridentialsId)
    {
        UserType userType;
        ResultSet rs;
        userType = UserType.INVALID;
        rs = executeQuery((new StringBuilder("SELECT * FROM CRIDENTIALS WHERE Cridentials_ID = ")).append(cridentialsId).toString());
        try
        {
            if(rs.next())
            {
                int userTypeKey = rs.getInt("User_Type_Id");
                userType = UserType.getUserType(userTypeKey);
            }
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return userType;
    }

    private static String encrypt(String property)
        throws GeneralSecurityException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        javax.crypto.SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(1, key, new PBEParameterSpec(SALT, 20));
        return (new Base64()).encodeToString(pbeCipher.doFinal(property.getBytes()));
    }

    private static String decrypt(String property)
        throws GeneralSecurityException, IOException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        javax.crypto.SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(2, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal((new Base64()).decode(property)));
    }

    public static long getLatestCridentialsId()
    {
        long latestCridentialsId;
        String sql;
        latestCridentialsId = 0L;
        sql = "SELECT max(Cridentials_Id) AS Cridentials_Id FROM CRIDENTIALS";
        ResultSet rs = null;
        try
        {
            rs = executeQuery(sql);
            if(rs.next())
            {
                latestCridentialsId = rs.getLong("Cridentials_Id");
            }
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return latestCridentialsId;
    }

    public static void changeCridentials(long adultId, int userType)
    {
        String sql = (new StringBuilder("UPDATE cridentials SET user_type_id  = "))
        		.append(userType).append(" where cridentials_id = (SELECT cridentials_id FROM adult WHERE adult_id = ")
        		.append(adultId).append(")").toString();
        try
        {
            executeUpdate(sql);
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
    }
}
