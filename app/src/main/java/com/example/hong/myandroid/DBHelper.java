package com.example.hong.myandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "example.db";
    static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // SHOPPINGLIST : 장바구니 리스트 // TRASHLIST : 백업리스트 //  USERPRODUCT : 유저 냉장고 내 상품 리스트
    // nf // 1 : 밑줄, 0 : 밑줄 X
    // direction // 리스트 : 1, 쓰레기통 : 0
    // tf // 체크박스 변수 체크O :1 / 체크X : 0

    //String userid 유저아이디, String item 상품명, String enrolldate 등록일, int amount 수량, String position 보관방법, String category 카테고리, String expirydate 유통기한, String detail 메모
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SHOPPINGLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, item TEXT, enrolldate TEXT, nf INTEGER, direction INTEGER, tf INTEGER);");
        db.execSQL("CREATE TABLE RFITEMLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, item TEXT, enrolldate TEXT, amount INTEGER, position TEXT, category TEXT, expirydate TEXT, detail TEXT);");
        db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, pw TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 쇼핑리스트 아이템 추가
    public void insertuser(String userid, String pw) {
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, pw TEXT);");
        db.execSQL("INSERT INTO USER VALUES(null, '" + userid + "', '" + pw + "');");
        db.close();
    }

    public String getuser() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(1) + "\n";
        }
        return result;
    }

    // 쇼핑리스트 DB확인용
    public String getResult1() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SHOPPINGLIST", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(3) + "\n";
        }
        return result;
    }

    // 쇼핑리스트 아이템 로딩
    public ArrayList<BuyItem> getBuyItem() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BuyItem> bps = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM SHOPPINGLIST", null);
        while (cursor.moveToNext()) {
            bps.add(new BuyItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6)));
        }
        return bps;
    }

    // 쇼핑리스트 아이템 추가
    public void insert(String userid, String item, String enrolldate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO SHOPPINGLIST VALUES(null, '" + userid + "', '" + item + "', '" + enrolldate + "', " + 0 + ", " + 1 + ", 0);");
        db.close();
    }

    // 쇼핑리스트 아이템 업데이트
    public void insert(String userid, String item, String enrolldate, int nf, int direction, int tf) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO SHOPPINGLIST VALUES(null, '" + userid + "', '" + item + "', '" + enrolldate + "', " + 0 + ", " + 0 + ", 0);");
        db.close();
    }

    // 쇼핑리스트 아이템 제거
    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM SHOPPINGLIST WHERE item = '" + item + "';");
        db.close();
    }

    // 냉장고 아이템 추가
    // 유저 아이디 , 상품명, 등록일, , 수량, 저장장소(상온/냉장/냉동) , 카테고리, 유통기한, 메모
    //  db.execSQL("CREATE TABLE RFITEMLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, item TEXT, enrollDate TEXT, amount INTEGER, position TEXT, category TEXT, expirydate TEXT, detail TEXT);");
    public void insertrf(String userid, String item, String enrolldate, int amount, String position, String category, String expirydate, String detail) {
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("CREATE TABLE RFITEMLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, item TEXT, enrolldate TEXT, amount INTEGER, position TEXT, category TEXT, expirydate TEXT, detail TEXT);");
        db.execSQL("INSERT INTO RFITEMLIST VALUES(null, '" + userid + "', '" + item + "', '" + enrolldate + "', " + amount + ", '" + position + "', '" + category + "', '" + expirydate + "','" + detail + "');");
        db.close();
    }

    // 냉장고 DB확인용
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM RFITEMLIST", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(1)
                    + " "
                    + cursor.getString(2)
                    + " "
                    + cursor.getString(3)
                    + " "
                    + cursor.getInt(4)
                    + " "
                    + cursor.getString(5)
                    + " "
                    + cursor.getString(6)
                    + " "
                    + cursor.getString(7)
                    + " "
                    + cursor.getString(8)
                    + " \n"
            ;
        }
        return result;
    }


    /*


    public void update(String item, int amount) { // 디테일에서 수량입력
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE BUYLIST SET amount=" + amount + "  WHERE item='" + item + "';");
        db.close();
    }
    public void backUp(String user_id, String item, int amount, int live) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO BACKUPLIST VALUES(null, '" + user_id + "', '" + item + "', " + amount + ", " + live + ", 0);");
        db.close();
    }

     public void delete2(String title) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM USERPRODUCTLIST WHERE title= '" + title + "';");
        db.close();
    }

    public void returnbackup(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM BACKUPLIST WHERE item= '" + item + "';");
        db.close();
    }





     public ArrayList<BuyProduct> getBuyProduct() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BuyProduct> bps = new ArrayList<>();

        SQLiteDatabase db2 = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BUYLIST", null);

        while (cursor.moveToNext()) {
            bps.add(new BuyProduct(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
        }
        return bps;
    }


    public ArrayList<BuyProduct> getBackUpBuyProduct() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BuyProduct> bps = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM BACKUPLIST", null);
        while (cursor.moveToNext()) {
            bps.add(new BuyProduct(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
        }
        return bps;
    }

    public ArrayList<UserProduct> getUserProduct() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<UserProduct> ups = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM USERPRODUCTLIST", null);

        while (cursor.moveToNext()) {
            ups.add(new UserProduct(R.drawable.trash, cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getString(4)
                    , cursor.getInt(5)
                    , cursor.getString(6)
                    , cursor.getString(7)
                    , cursor.getString(8)
                    , cursor.getInt(9)
            ));
        }
        return ups;
    }
     */



}