package com.example.hong.myandroid;

public class BuyItem extends Item { // 장바구니 품목
    private int nf; // 1 : 밑줄, 0 : 밑줄 X
    private int direction; // 리스트 : 1, 쓰레기통 : 0
    private int tf; // 체크박스 변수 체크O :1 / 체크X : 0
    private boolean ctf;

    public BuyItem(String userid, String item, String enrolldate) {
        super(userid, item, enrolldate);
        this.nf = 0;
        this.direction = 1;
        this.tf = 0;
    }

    public BuyItem(String userid, String item, String enrolldate, int nf, int direction, int tf) {
        super(userid, item, enrolldate);
        this.nf = nf;
        this.direction = direction;
        this.tf = tf;
    }

    public int getNf() {
        return nf;
    }

    public void setNf(int nf) {
        this.nf = nf;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public boolean isCtf() {
        return ctf;
    }

    public void setCtf(boolean ctf) {
        this.ctf = ctf;
    }
}
