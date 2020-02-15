/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.*;
/**
 *
 * @author flori
 */
public class Bill implements Serializable{
    // this is just a test
    private static long globalId = 1;

    public static long getGlobalId() {
        return globalId;
    }

    public static void setGlobalId(long aGlobalId) {
        globalId = aGlobalId;
    }
    
    private String billId;
    private Date dateOfIssue;
    private Date dateOfDue;
    private Date dateOfPayment;
    private double amount;
    private boolean paid;

    
    

    public Bill(double amount) {
        this.amount = amount;
        billId = "BILL" + globalId;
        globalId++;
    }
    
    public String getBillId() {
        return billId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateOfIssue() {
        return (Date)dateOfIssue.clone();
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = (Date)dateOfIssue.clone();
    }

    public Date getDateOfDue() {
        return (Date)dateOfDue.clone();
    }

    public void setDateOfDue(Date dateOfDue) {
        this.dateOfDue = (Date)dateOfDue.clone();
    }

    public Date getDateOfPayment() {
        return (Date)dateOfPayment.clone();
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = (Date)dateOfPayment.clone();
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    
    
}
