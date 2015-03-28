package com.peanutBarrel.data;

import com.peanutBarrel.entities.Child;
import java.util.Date;

public class BillingInfo
{

    Long billingInfoId;
    Date asOfDate;
    Double price;
    Child child;

    public BillingInfo()
    {
    }

    public Long getBillingInfoId()
    {
        return billingInfoId;
    }

    public Date getAsOfDate()
    {
        return asOfDate;
    }

    public Double getPrice()
    {
        return price;
    }

    public Child getChild()
    {
        return child;
    }

    public void setBillingInfoId(Long billingInfoId)
    {
        this.billingInfoId = billingInfoId;
    }

    public void setAsOfDate(Date asOfDate)
    {
        this.asOfDate = asOfDate;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public void setChild(Child child)
    {
        this.child = child;
    }
}
