package com.example.expensemanager;



// Define a class to represent each result
public class ExpenseAnalyzer {
    private String cause;
    private double sum;

    public ExpenseAnalyzer(String cause, double sum) {
        this.cause = cause;
        this.sum = sum;
        }

    public String getCause() {
        return cause;
        }

    public double getSum() {
        return sum;
        }

    public void setCause(String cause) {
        this.cause = cause;
        }

    public void setSum(double sum) {
        this.sum = sum;
        }
}
