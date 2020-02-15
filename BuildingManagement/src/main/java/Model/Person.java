package Model;


import MenuUI.Tools;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Person implements Serializable{
    private static long globalPersonID = 1;

    public static long getGlobalPersonID() {
        return globalPersonID;
    }

    public static void setGlobalPersonID(long aGlobalPersonID) {
        globalPersonID = aGlobalPersonID;
    }
    
    private String personId;
    private String name;
    private Date dateOfBirth;
    private String nationalIdNumber;
    private char sex;
    private String maritalStatus;
    private String phoneNumber;

    public Person() {
    }

    public Person(String name, Date dateOfBirth, char sex) {
        this.personId = "PER"+globalPersonID;
        globalPersonID++;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }
    
    public Person(String name, Date dateOfBirth, String nationalIdNumber, char sex, String maritalStatus, String phoneNumber) {
        this.personId="PER"+globalPersonID;
        globalPersonID++;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationalIdNumber = nationalIdNumber;
        this.sex = sex;
        this.maritalStatus = maritalStatus;
        this.phoneNumber = phoneNumber;
    }

    public String getPersonId() {
        return personId;
    }
    
    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return (Date)dateOfBirth.clone();
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public char getSex() {
        return Character.toUpperCase(sex);
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }
 
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = (Date)dateOfBirth.clone();
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
   
  
   

}