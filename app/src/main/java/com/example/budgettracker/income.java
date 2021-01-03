package com.example.budgettracker;

public class income {
    int idincome;
    String nameincome;

    String amountincome;

    public income() {
    }

    public income(int idincome, String nameincome, String amountincome) {
        this.idincome = idincome;
        this.nameincome = nameincome;
        this.amountincome = amountincome;
    }

    public int getIdincome() {
        return idincome;
    }

    public void setIdincome(int idincome) {
        this.idincome = idincome;
    }

    public String getNameincome() {
        return nameincome;
    }

    public void setNameincome(String nameincome) {
        this.nameincome = nameincome;
    }

    public String getAmountincome() {
        return amountincome;
    }

    public void setAmountincome(String amountincome) {
        this.amountincome = amountincome;
    }
}
