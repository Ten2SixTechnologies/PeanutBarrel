package com.peanutBarrel.data;

import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.entities.Child;
import java.util.Date;

public class TimeLog
{

    private Long timeLogId;
    private Child child;
    private Adult adultIn;
    private Date timeIn;
    private Adult adultOut;
    private Date timeOut;

    public TimeLog()
    {
    }

    public Long getTimeLogId()
    {
        return timeLogId;
    }

    public Child getChild()
    {
        return child;
    }

    public Adult getAdultIn()
    {
        return adultIn;
    }

    public Date getTimeIn()
    {
        return timeIn;
    }

    public Adult getAdultOut()
    {
        return adultOut;
    }

    public Date getTimeOut()
    {
        return timeOut;
    }

    public void setTimeLogId(Long timeLogId)
    {
        this.timeLogId = timeLogId;
    }

    public void setChild(Child child)
    {
        this.child = child;
    }

    public void setAdultIn(Adult adultIn)
    {
        this.adultIn = adultIn;
    }

    public void setTimeIn(Date timeIn)
    {
        this.timeIn = timeIn;
    }

    public void setAdultOut(Adult adultOut)
    {
        this.adultOut = adultOut;
    }

    public void setTimeOut(Date timeOut)
    {
        this.timeOut = timeOut;
    }
}
