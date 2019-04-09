package com.example.hong.myandroid;

public class RfItem extends Item {
    private int icon; // 이미지
    private int amount; // 수량
    private String position; // 저장공간(냉동실 / 냉장실 / 상온)
    private String category; // 카테고리
    private String expirydate; // 유통기한
    private String detail; // 상세정보
    private boolean ox; // 체크박스 변수

    public RfItem(String userid, String item, String enrolldate, int amount, String position, String category, String expirydate, String detail) {
        super(userid, item, enrolldate);
        this.amount = amount;
        this.position = position;
        this.category = category;
        this.expirydate = expirydate;
        this.detail = detail;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isOx() {
        return ox;
    }

    public void setOx(boolean ox) {
        this.ox = ox;
    }
}
