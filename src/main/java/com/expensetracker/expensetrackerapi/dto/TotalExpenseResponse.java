package com.expensetracker.expensetrackerapi.dto;

public class TotalExpenseResponse {

    private double total;

    public TotalExpenseResponse() {
    }

    public TotalExpenseResponse(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}