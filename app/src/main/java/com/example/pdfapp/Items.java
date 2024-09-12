package com.example.pdfapp;

import java.io.Serializable;

public class Items implements Serializable {
    int id;
    String itemName;
    int quant;
    double rate;
    int perc;
    double taxableValue;
    double cgst;
    double sgst;
    double total;

    public Items() {
    }

    public Items(int id, String itemName, int quant, double rate, int perc, double taxableValue, double cgst, double sgst, double total) {
        this.id = id;
        this.itemName = itemName;
        this.quant = quant;
        this.rate = rate;
        this.perc = perc;
        this.taxableValue = taxableValue;
        this.cgst = cgst;
        this.sgst = sgst;
        this.total = total;
    }

    public Items(String itemName, int quant, double rate, int perc, double taxableValue, double cgst, double sgst, double total) {
        this.itemName = itemName;
        this.quant = quant;
        this.rate = rate;
        this.perc = perc;
        this.taxableValue = taxableValue;
        this.cgst = cgst;
        this.sgst = sgst;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quant=" + quant +
                ", rate=" + rate +
                ", perc=" + perc +
                ", taxableValue=" + taxableValue +
                ", cgst=" + cgst +
                ", sgst=" + sgst +
                ", total=" + total +
                '}';
    }

    public double getTaxableValue() {
        return taxableValue;
    }

    public void setTaxableValue(double taxableValue) {
        this.taxableValue = taxableValue;
    }

    public double getCgst() {
        return cgst;
    }

    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    public double getSgst() {
        return sgst;
    }

    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    public int getPerc() {
        return perc;
    }

    public void setPerc(int perc) {
        this.perc = perc;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
