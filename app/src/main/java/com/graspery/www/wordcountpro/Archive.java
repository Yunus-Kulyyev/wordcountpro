package com.graspery.www.wordcountpro;

public class Archive {
    private String text;
    private String date;
    private int id;

    public Archive(String text, String date, int id) {
        this.text = text;
        this.date = date;
        this.id = id;
    }

    public Archive(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
