package space.grain49.server.model;

public class PaymentAccount {
    private String uuid;
    private String name;
    private String type;
    private double money;
    private String note;
    private String userID;

    public PaymentAccount(String uuid, String name, String type, double money, String note, String userID) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.money = money;
        this.note = note;
        this.userID = userID;
    }

    

    @Override
    public String toString() {
        return this.name + " " + this.note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
