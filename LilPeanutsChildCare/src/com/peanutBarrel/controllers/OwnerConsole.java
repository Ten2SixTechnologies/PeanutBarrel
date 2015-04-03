package com.peanutBarrel.controllers;

import javax.faces.bean.ManagedBean;

import com.peanutBarrel.actions.ActionsDelegate;

@ManagedBean (name = "ownerConsole")
public class OwnerConsole
{

    public OwnerConsole()
    {
    }

    public String register()
    {
        return "newAdult_navigateRegister";
    }

    public String newChild()
    {
        return "newChild_navigateNewChild";
    }

    public String changeCridentials()
    {
        ActionsDelegate.changeCridentials(4L, 2);
        return null;
    }
}
