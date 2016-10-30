package com.peanutBarrel.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.dao.object.AdultDAO;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.entities.Child;

@SuppressWarnings("serial")
@ManagedBean (name = "ownerConsole")
@WebServlet("/loginServlet")
public class OwnerConsole extends HttpServlet
{
	List<String> adultNames = new ArrayList<String>();
	List<String> childNames = new ArrayList<String>();
	String selectedAdultName = "";
	
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
    
    public void setAdultNames(List<String> adultNames) {
    	this.adultNames = adultNames;
    }
    
    public void setChildNames(List<String> childNames) {
    	this.childNames = childNames;
    }
    
    public List<String> getAdultNames() {
    	List<Adult> adults = ActionsDelegate.getAllAdults();
    	    	
    	for(Adult adult : adults){
    		adultNames.add(adult.getFirstName() + " " + adult.getLastName());
    	}
    	
    	return adultNames;
    }
    
    public List<String> getChildNames() {
    	if(selectedAdultName.isEmpty() && adultNames.isEmpty() == false) {
    		selectedAdultName = adultNames.get(0);
    	}
    	
    	List<Child> children = ActionsDelegate.getChildren(AdultDAO.getAdultWithName(selectedAdultName));
    	for(Child child : children){
    		childNames.add(child.getFirstName() + " " + child.getLastName());
    	}
    	
    	return childNames;
    }  
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	selectedAdultName = request.getParameter("selectedAdult");
    }
    
    public void getChildrenNames() 
    {
    	
    }
}
