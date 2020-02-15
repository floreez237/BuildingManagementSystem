/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import MenuUI.Tools;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;  
import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author flori
 */
public class Contract implements Serializable{

    private static long globalContractID = 1;

    public static long getGlobalContractID() {
        return globalContractID;
    }

    public static void setGlobalContractID(long aGlobalContractID) {
        globalContractID = aGlobalContractID;
    }
    private String contractId;
    private Person tenant;
    private String roomId;
    private Date dateOfPayment;
    private Date dateOfCreation;
    private int duration;

    public Contract() {
    }

    public Contract(String roomId, int duration,Date dateOfPayment,Person tenant) {
        this.contractId = "CONT" + (globalContractID);
        globalContractID++;
        this.tenant = tenant;
        this.roomId = roomId;
        this.dateOfPayment = dateOfPayment;
        this.duration = duration;
        this.dateOfCreation=new Date();

    }

    public String getContractId() {
        return contractId;
    }

    public Person getTenant() {
        return tenant;
    }

    public String getRoomId() {
        return roomId;
    }

    public Date getDateOfPayment() {
        return (Date) dateOfPayment.clone();
    }

    public int getDuration() {
        return duration;
    }

    public Date getDateOfCreation() {
        return (Date) dateOfCreation.clone();
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = (Date) dateOfCreation.clone();
    }
    

    public void setTenant(Person tenant) {
        this.tenant = tenant;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = (Date) dateOfPayment.clone();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }   
    
}
