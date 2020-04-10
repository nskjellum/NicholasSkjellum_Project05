package com.example.nicholasskjellum_project05;

public class TransactionHistory {

    public double amount;
    public String reason;
    public String date;

    public String toSQL() {

        StringBuilder sb = new StringBuilder("(");
        sb.append("'").append(date).append("'").append(",");
        sb.append(amount).append(",");
        sb.append("'").append(reason).append("'").append(")");

        return sb.toString();
    }

}
