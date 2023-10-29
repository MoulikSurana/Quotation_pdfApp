package com.example.pdfapp;

import java.io.Serializable;

public class Items implements Serializable {
    int id;
    String itemName;
    int quant,rate,total;

    public Items() {
    }

    public Items(int id, String itemName, int quant, int rate, int total) {
        this.id = id;
        this.itemName = itemName;
        this.quant = quant;
        this.rate = rate;
        this.total = total;
    }

    public Items(String itemName, int quant, int rate, int total) {
        this.itemName = itemName;
        this.quant = quant;
        this.rate = rate;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quant=" + quant +
                ", rate=" + rate +
                ", total=" + total +
                '}';
    }
}
