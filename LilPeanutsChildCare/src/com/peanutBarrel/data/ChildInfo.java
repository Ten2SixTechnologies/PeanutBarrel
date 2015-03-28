package com.peanutBarrel.data;

import java.util.Date;

// Referenced classes of package com.peanutBarrel.data:
//            ChildPicture

public class ChildInfo
{

    private Long childInfoId;
    private Date birthday;
    private Date enrollmentDate;
    private ChildPicture picture;
    private String medicalInfo;
    private String notes;

    public ChildInfo()
    {
    }

    public Long getChilInfoId()
    {
        return childInfoId;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public Date getEnrollmentDate()
    {
        return enrollmentDate;
    }

    public ChildPicture getPicture()
    {
        return picture;
    }

    public String getMedicalInfo()
    {
        return medicalInfo;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setChildInfoId(Long childInfoId)
    {
        this.childInfoId = childInfoId;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public void setEnrollmentDate(Date enrollmentDate)
    {
        this.enrollmentDate = enrollmentDate;
    }

    public void setPicture(ChildPicture picture)
    {
        this.picture = picture;
    }

    public void setMedicalInfo(String medicalInfo)
    {
        this.medicalInfo = medicalInfo;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }
}
