package com.example.companyk;

public class Question {
    private String name, phone, ask, date;

    public Question(String name, String phone, String ask, String date) {
        this.name = name;
        this.phone = phone;
        this.ask = ask;
        this.date = date;
    }


    Question(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
