
package com.sales.model;

import java.util.ArrayList;

public class Inv {
    private int num;
    private String date;
    private String cus;
    private ArrayList<Item> items;
    
    public Inv() {
    }

    public Inv(int num, String date, String cus) {
        this.num = num;
        this.date = date;
        this.cus = cus;
    }

    public double getInvTotal() {
        double total = 0.0;
        for (Item line : getItems()) {
            total += line.getItemTotal();
        }
        return total;
    }
    
    public ArrayList<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int id) {
        this.num = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + date + ", customer=" + cus + '}';
    }
    
    public String getCSV() {
        return num + "," + date + "," + cus;
    }
    
}
