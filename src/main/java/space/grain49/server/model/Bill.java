package space.grain49.server.model;

import java.sql.Timestamp;

public class Bill {
    private String UUID;
    private double money;
    private String type;
    private String accountOut;
    private String accountIn;
    private String note;
    private Timestamp timestamp;
    private String userID;


    public Bill(){
        
    }

    public Bill(String UUID, double money, String type, String accountOut, String accountIn, String note, Timestamp timestamp, String userID) {
        this.UUID = UUID;
        this.money = money;
        this.type = type;
        this.accountOut = accountOut;
        this.accountIn = accountIn;
        this.note = note;
        this.timestamp = timestamp;
        this.userID = userID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountOut() {
        return accountOut;
    }

    public void setAccountOut(String accountOut) {
        this.accountOut = accountOut;
    }

    public String getAccountIn() {
        return accountIn;
    }

    public void setAccountIn(String accountIn) {
        this.accountIn = accountIn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
