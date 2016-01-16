package com.peanutBarrel.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.entities.Adult;

@ManagedBean (name = "logInValidation")
public class LogInValidation
{
    private String userName;
    private String password;
    private Adult adult;

    public LogInValidation()
    {
    }

    public void LogInValidatioin()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void setAdult(Adult adult)
    {
        this.adult = adult;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInAdult", adult);
    }

    public Adult getAdult()
    {
        return adult;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public int validate()
    {
        setAdult(ActionsDelegate.signIn(userName, password, adult));
        userName = "";
        return adult.getUserType().getKey();
    }
}
