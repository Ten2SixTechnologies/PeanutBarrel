package com.peanutBarrel.actions;

import com.peanutBarrel.entities.Child;
import java.util.*;

public class Sorter
{

    public Sorter()
    {
    }

    public static List<Child> sortChildren(List<Child> children)
    {
        List<String> names = new ArrayList<String>();
        List<Child> sortedChildren = new ArrayList<Child>();
        Child child;
        for(Iterator<Child> iterator = children.iterator(); iterator.hasNext(); names.add(child.getFirstName()))
        {
            child = (Child)iterator.next();
        }

        Collections.sort(names);
        for(Iterator<String> iterator1 = names.iterator(); iterator1.hasNext();)
        {
            String name = (String)iterator1.next();
            for(Iterator<Child> iterator2 = children.iterator(); iterator2.hasNext();)
            {
                child = (Child)iterator2.next();
                String firstName = child.getFirstName();
                if(name.equals(firstName))
                {
                    sortedChildren.add(child);
                }
            }

        }

        return sortedChildren;
    }
}
