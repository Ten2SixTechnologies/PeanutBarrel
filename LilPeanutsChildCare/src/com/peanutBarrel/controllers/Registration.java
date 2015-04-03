package com.peanutBarrel.controllers;

import javax.faces.bean.ManagedBean;

import com.peanutBarrel.actions.ActionsDelegate;

@ManagedBean (name = "registration")
public class Registration
{

    private String newUserName;
    private String newPassword;
    private Long cridentialsId;

    public Registration()
    {
    }

    public void setCridentialsId(Long cridentialsId)
    {
        this.cridentialsId = cridentialsId;
    }

    public Long getCridentialsId()
    {
        return cridentialsId;
    }

    public void setNewUserName(String newUserName)
    {
        this.newUserName = newUserName;
    }

    public String getNewUserName()
    {
        return newUserName;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public String submit()
    {
        setCridentialsId(ActionsDelegate.createNewCridentials(newUserName, newPassword, 0));
        return "submit_Success";
    }
}
