package com.peanutBarrel.data;


public class Address
{

    private Long addressId;
    private String houseNumber;
    private String street;
    private String apt;
    private String city;
    private String state;
    private int zipCode;

    public Address()
    {
    }

    public Long getAddressId()
    {
        return addressId;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public String getStreet()
    {
        return street;
    }

    public String getApt()
    {
        return apt;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setAddressId(Long addressId)
    {
        this.addressId = addressId;
    }

    public void setHouseNumber(String houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public void setApt(String apt)
    {
        this.apt = apt;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }
}
