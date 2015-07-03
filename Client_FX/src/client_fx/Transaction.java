/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_fx;

import java.sql.Time;

/**
 *
 * @author Roy
 */
public class Transaction {

    private static Transaction instance;

    private String accountID;
    private long ammount;
    private Time completionDate;
    private boolean pending;
    private long ID;

    public static Transaction init() {
        instance = new Transaction();
        return instance;
    }

    public static Transaction getCurrentTransaction() throws Exception {
        if (instance == null) {
            throw new Exception("Exception is not initialized");
        }
        return instance;
    }

    public static void clearTransaction() {
        instance = null;
    }

    public static boolean transactionPending() {
        return (instance != null && instance.pending);
    }

    public Transaction() {
        this.accountID = null;
        this.ammount = 0;
        this.completionDate = null;
        this.pending = true;
        this.ID = 0;
    }

    public static Transaction getInstance() {
        return instance;
    }

    public static void setInstance(Transaction instance) {
        Transaction.instance = instance;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public long getAmmount() {
        return ammount;
    }

    public void setAmmount(long ammount) {
        this.ammount = ammount;
    }

    public Time getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Time completionDate) {
        this.completionDate = completionDate;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

}
