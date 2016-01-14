package com.peanutBarrel.dao.object;

import java.sql.ResultSet;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.data.Address;
import com.peanutBarrel.services.DatabaseServices;

// Referenced classes of package com.peanutBarrel.dao.object:
//            StateDAO

public class AddressDAO extends DAO
{

    public AddressDAO()
    {
    }

    public static Long persistAddress(Address address)
    {
        Long addressId = null;
        try
        {
            addressId = executeInsert((new StringBuilder("INSERT INTO ADDRESS (HOUSE_NUM, STREET, APT, CITY, STATE_ID, ZIP) VALUES ('")).append(address.getHouseNumber()).append("',").append("'").append(address.getStreet()).append("',").append("'").append(address.getApt()).append("',").append("'").append(address.getCity()).append("',").append(getStateId(address.getState())).append(",").append(address.getZipCode()).append(")").toString());
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }

        return addressId;
    }

    public static Address getAddress(Long addressId)
    {
        String houseNumber;
        String street;
        String apt;
        String city;
        Long stateId;
        Integer zipCode;
        ResultSet rs;
        houseNumber = null;
        street = null;
        apt = null;
        city = null;
        stateId = null;
        zipCode = null;
        rs = executeQuery((new StringBuilder("SELECT * FROM ADDRESS WHERE Address_ID = ")).append(addressId).toString());
        try
        {
            if(rs.next())
            {
                houseNumber = rs.getString("House_Num");
                street = rs.getString("Street");
                apt = rs.getString("Apt");
                city = rs.getString("City");
                stateId = Long.valueOf(rs.getLong("State_ID"));
                zipCode = Integer.valueOf(rs.getInt("Zip"));
                if(apt == null)
                {
                    apt = "";
                }
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }

        Address address = new Address();
        address.setAddressId(addressId);
        address.setHouseNumber(houseNumber);
        address.setStreet(street);
        address.setApt(apt);
        address.setCity(city);
        address.setState(StateDAO.getStateAbrev(stateId));
        address.setZipCode(zipCode.intValue());
        return address;
    }

    private static Long getStateId(String state)
    {
        Long stateId;
        ResultSet rs;
        stateId = null;
        rs = null;
        if(state.length() == 2)
        {
            rs = executeQuery((new StringBuilder("SELECT State_ID FROM STATE WHERE State_Abrev = '")).append(state).append("'").toString());
        } else
        {
            rs = executeQuery((new StringBuilder("SELECT State_ID FROM STATE WHERE State_Name = '")).append(state).append("'").toString());
        }
        try
        {
            if(rs.next())
            {
                stateId = Long.valueOf(rs.getLong("State_ID"));
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }

        return stateId;
    }
}
