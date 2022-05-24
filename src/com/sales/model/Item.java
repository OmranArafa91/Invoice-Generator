
package com.sales.model;

public class Item {
    private String item;
    private double price;
    private int amount;
    private Inv inv;

    public Item() {
    }

    public Item(String item, double price, int amount, Inv inv) {
        this.item = item;
        this.price = price;
        this.amount = amount;
        this.inv = inv;
    }

    public double getItemTotal() {
        return price * amount;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + inv.getNum() + ", item=" + item + ", price=" + price + ", count=" + amount + '}';
    }

    public Inv getInv() {
        return inv;
    }
    
    public String getCSV() {
        return inv.getNum() + "," + item + "," + price + "," + amount;
    }
    
}
