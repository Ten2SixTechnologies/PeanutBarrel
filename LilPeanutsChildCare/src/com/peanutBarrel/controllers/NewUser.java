package com.peanutBarrel.controllers;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.dao.object.StateDAO;
import java.util.*;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

@ManagedBean (name = "newUser")
public class NewUser
{

    private String firstName;
    private String middleName;
    private String lastName;
    private String primaryPhone;
    private String secondaryPhone;
    private String eMail;
    private String houseNumber;
    private String street;
    private String apt;
    private String city;
    private Integer zipCode;
    private String state;
    private String family;

    public NewUser()
    {
    }

    public String next()
    {
        ActionsDelegate.createNewUser(new String[] {
            firstName, middleName, lastName, primaryPhone, secondaryPhone, eMail, houseNumber, street, apt, city, 
            zipCode.toString(), state, family
        });
        return "next_Success";
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPrimaryPhone()
    {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhoneInput)
    {
        String areaCode = primaryPhoneInput.substring(1, 4);
        String prefix = primaryPhoneInput.substring(6, 9);
        String postfix = primaryPhoneInput.substring(10, 14);
        primaryPhone = (new StringBuilder(String.valueOf(areaCode))).append(prefix).append(postfix).toString();
    }

    public String getSecondaryPhone()
    {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhoneInput)
    {
        if(secondaryPhoneInput != null && secondaryPhoneInput.length() > 0)
        {
            String areaCode = secondaryPhoneInput.substring(1, 4);
            String prefix = secondaryPhoneInput.substring(6, 9);
            String postfix = secondaryPhoneInput.substring(10, 14);
            secondaryPhone = (new StringBuilder(String.valueOf(areaCode))).append(prefix).append(postfix).toString();
        } else
        {
            secondaryPhone = "";
        }
    }

    public String geteMail()
    {
        return eMail;
    }

    public void seteMail(String eMail)
    {
        this.eMail = eMail;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getApt()
    {
        return apt;
    }

    public void setApt(String apt)
    {
        this.apt = apt;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Integer getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(Integer zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public List<SelectItem> getStateList()
    {
        List<SelectItem> stateList = new ArrayList<SelectItem>();
        stateList.add(0, new SelectItem("MI", "Michigan"));
        Map<String, String> states = StateDAO.getAllStates();
        
        Iterator<Entry<String, String>> iterator = states.entrySet().iterator();
        while(iterator.hasNext())
        {
            Entry<String, String> state = (Entry<String, String>)iterator.next();
            if(!((String)state.getKey()).equals("Michigan"))
            {
                stateList.add(new SelectItem(state.getValue(), (String)state.getKey()));
            }
        }

        return stateList;
    }

    public String getFamily()
    {
        return family;
    }

    public void setFamily(String family)
    {
        this.family = family;
    }

    public List<SelectItem> getFamilyList()
    {
        List<SelectItem> familyList = new ArrayList<SelectItem>();
        familyList.add(0, new SelectItem(Integer.valueOf(0), "New Family"));
        Map<String, Integer> families = ActionsDelegate.getFamilies();
        Entry<String, Integer> family;
        
        Iterator<Entry<String, Integer>> iterator = families.entrySet().iterator();
        while(iterator.hasNext())
        {
            family = (java.util.Map.Entry<String, Integer>)iterator.next();
            familyList.add(new SelectItem(family.getValue(), (String)family.getKey()));
        }

        return familyList;
    }

    public String toString()
    {
        return (new StringBuilder("NewUser [firstName=")).append(firstName).append(", middleName=").append(middleName).append(", lastName=").append(lastName).append(", primaryPhone=").append(primaryPhone).append(", secondaryPhone=").append(secondaryPhone).append(", eMail=").append(eMail).append(", houseNumber=").append(houseNumber).append(", street=").append(street).append(", apt=").append(apt).append(", city=").append(city).append(", state=").append(state).append(", zipCode=").append(zipCode).append("]").toString();
    }
}
