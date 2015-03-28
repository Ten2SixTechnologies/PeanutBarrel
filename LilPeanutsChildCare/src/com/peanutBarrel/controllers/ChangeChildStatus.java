package com.peanutBarrel.controllers;

import javax.faces.context.FacesContext;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.entities.Child;

public class ChangeChildStatus
{

    String childStatus;
    Child child;
    Adult adult;

    public ChangeChildStatus()
    {
    }

    public void setChild(Child child)
    {
        this.child = child;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("child", child);
    }

    public Child getChild()
    {
        return child;
    }

    public void setAdult(Adult adult)
    {
        this.adult = adult;
    }

    public Adult getAdult()
    {
        return adult;
    }

    public String signIn()
    {
        ActionsDelegate.signChildIn(child, adult);
        return "backToRoster";
    }

    public String signOut()
    {
        ActionsDelegate.signChildOut(child, adult);
        return "backToRoster";
    }

    public String getChildStatus()
    {
        childStatus = ActionsDelegate.getChildStatus(child);
        return childStatus;
    }
}
