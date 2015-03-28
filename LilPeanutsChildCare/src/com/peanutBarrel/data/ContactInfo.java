package com.peanutBarrel.data;


// Referenced classes of package com.peanutBarrel.data:
//            Address

public class ContactInfo
{

    private Long contactInfoId;
    private String primaryPhone;
    private String secondaryPhone;
    private Address address;
    private String eMail;

    public ContactInfo()
    {
    }

    public Long getContactInfoId()
    {
        return contactInfoId;
    }

    public String getPrimaryPhone()
    {
        return primaryPhone;
    }

    public String getSecondaryPhone()
    {
        return secondaryPhone;
    }

    public Address getAddress()
    {
        return address;
    }

    public String getEMail()
    {
        return eMail;
    }

    public void setContactInfoId(Long contactInfoId)
    {
        this.contactInfoId = contactInfoId;
    }

    public void setPrimaryPhone(String primaryPhone)
    {
        this.primaryPhone = primaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone)
    {
        this.secondaryPhone = secondaryPhone;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public void setEMail(String eMail)
    {
        this.eMail = eMail;
    }

    public ContactInfo(Address address)
    {
        this.address = address;
    }
}
