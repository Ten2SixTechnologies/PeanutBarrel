package com.peanutBarrel.controllers;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.data.ChildPicture;
import java.util.*;
import java.util.Map.Entry;

import javax.faces.model.SelectItem;
import org.primefaces.model.UploadedFile;

public class NewChild
{

    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private String medicalInfo;
    private String notes;
    private ChildPicture picture;
    private String family;

    public NewChild()
    {
    }

    public String next()
    {
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

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getMedicalInfo()
    {
        return medicalInfo;
    }

    public void setMedicalInfo(String medicalInfo)
    {
        this.medicalInfo = medicalInfo;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public UploadedFile getPicture()
    {
        return (UploadedFile)picture;
    }

    public void setPicture(UploadedFile picture)
    {
        this.picture = (ChildPicture)picture;
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
        Map<String, Integer> families = ActionsDelegate.getFamilies();
        Entry<String, Integer> family;
        
        Iterator<Entry<String, Integer>> iterator = families.entrySet().iterator();
        while(iterator.hasNext())
        {
            family = (Entry<String, Integer>)iterator.next();
            familyList.add(new SelectItem(family.getValue(), (String)family.getKey()));
        }

        return familyList;
    }

    public String toString()
    {
        return (new StringBuilder("NewUser [firstName=")).append(firstName).append(", middleName=").append(middleName).append(", lastName=").append(lastName).append("]").toString();
    }
}
