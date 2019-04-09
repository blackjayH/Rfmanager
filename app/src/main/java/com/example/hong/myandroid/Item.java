package com.example.hong.myandroid;

public class Item {
    private String userid;
    private String item;
    private String enrolldate;

    public Item(String userid, String item, String enrolldate) {
        this.userid = userid;
        this.item = item;
        this.enrolldate = enrolldate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getEnrolldate() {
        return enrolldate;
    }

    public void setEnrolldate(String enrolldate) {
        this.enrolldate = enrolldate;
    }

}
