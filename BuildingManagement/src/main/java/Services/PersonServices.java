package Services;

import Model.Person;
import MenuUI.Tools;
import java.util.Date;
import java.util.List;


public class PersonServices {
    Person person;

    public PersonServices(Person person) {
        this.person = person;
    }
    
    
    
public static Person searchPersonByName(String name,List<Person> listOfPersons){
        for(Person person:listOfPersons){
            if(person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
    
     public static Person searchPersonByNationalIdNumber(String name,List<Person> listOfPersons){
        for(Person person:listOfPersons){
            if(person.getName().equals(name)) {
                return person;
            } 
        }
        return null;
    }
    
    public void updateName( String name){
        person.setName(name);
    }
    
    public void updateSex( char sex){
        person.setSex(sex);
    }
    
    public void updateDateOfBirth( Date dateOfBirth){
        person.setDateOfBirth(dateOfBirth);
    }
    
    public void updateNationalIdNumber( String nationalIdNumber){
        person.setNationalIdNumber(nationalIdNumber);
    }
    
    public void updatePhoneNumber( String phoneNumber){
        person.setPhoneNumber(phoneNumber);
    }
    
    public void updateMaritalStatus(String maritalStatus){
        person.setName(maritalStatus);
    }
   
    public void displayPerson(){
        System.out.println("PersonId: "+person.getPersonId());
        System.out.println("Name: "+person.getName());
        System.out.print("Date:");
        Tools.displayDate(person.getDateOfBirth());
        System.out.println("");
        System.out.println("Gender:"+person.getSex());
        
        if(person.getNationalIdNumber()!=null){
           System.out.println("National Id Number :"+person.getNationalIdNumber()); 
        }
        if(person.getPhoneNumber()!=null){
            System.out.println("PhoneNumber:"+person.getPhoneNumber());
        }
        if(person.getMaritalStatus()!=null){
            System.out.println("Marital Status:"+person.getMaritalStatus());
        }
         
    }

    public void updateName(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void displayPersonsInList(List<Person> listOfPersons){
         if(listOfPersons.isEmpty()){
             System.out.println("No person in list.");
         }
         
         System.out.println("ID\t\t\tName\t\t\tDate Of Birth\t\t\tGender");
         System.out.println("--\t\t\t----\t\t\t-------------\t\t\t------");
         listOfPersons.forEach((person) -> {
             System.out.printf("%-23s%-25s%-34s%c\n",person.getPersonId(),person.getName(),Tools.newDisplayDate(person.getDateOfBirth()),person.getSex());
         });
     }

     } 

