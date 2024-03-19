package edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model;

import java.io.Serializable;

public class Expense implements Serializable {

    private String expenseId;
    private String note;
    private String category;

    private long amount;

    private String type;

    private long time;

    private String uId;

    public Expense() {
    }


    public Expense(String expenseId, String note, String category, long amount, String type, long time) {
        this.expenseId = expenseId;
        this.note = note;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.time = time;
    }

    public Expense(String expenseId, String note, String category, long amount, String type, long time, String uId) {
        this.expenseId = expenseId;
        this.note = note;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.time = time;
        this.uId = uId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId='" + expenseId + '\'' +
                ", note='" + note + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", uId='" + uId + '\'' +
                '}';
    }
}
