package com.peanutBarrel.controllers;

import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.data.ChildPicture;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.entities.Child;

@ManagedBean (name = "roster")
public class Roster
{

    String childIndex;
    List<Child> children;
    Child child;
    Adult adult;

    public Roster()
    {
    	setAdult((Adult) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInAdult"));
    	loadChildren();
    }

    public void loadChildren()
    {
        List<Child> children = ActionsDelegate.getChildren(adult);
        setChildren(children);
    }

    public StreamedContent getPicture()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if(context.getRenderResponse())
        {
            return new DefaultStreamedContent();
        }
        String childId = (String)context.getExternalContext().getRequestParameterMap().get("childId");
        ChildPicture image = null;
        
        Iterator<Child> iterator = children.iterator();
        while(iterator.hasNext())
        {
            Child child = (Child)iterator.next();
            if(child.getChildId().longValue() == Long.parseLong(childId))
            {
                image = child.getChildInfo().getPicture();
            }
        }

        return new DefaultStreamedContent(image.getInputStream());
    }

    public String clickListener()
    {
        String result = null;
        setChild((Child)children.get(Integer.parseInt(childIndex)));
        result = "clickListener_True";
        return result;
    }

    public void setChildIndex(String strChildIndex)
    {
        childIndex = strChildIndex;
    }

    public String getChildIndex()
    {
        return childIndex;
    }

    public Adult getAdult()
    {
        return adult;
    }

    public void setAdult(Adult adult)
    {
        this.adult = adult;
    }

    public List<Child> getChildren()
    {
        return children;
    }

    public void setChildren(List<Child> children)
    {
        this.children = children;
    }

    public Child getChild()
    {
        return child;
    }

    public void setChild(Child child)
    {
        this.child = child;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("child", child);
    }
}
