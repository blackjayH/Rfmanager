package com.example.hong.myandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonActivity extends AppCompatActivity {
    String myJSON;
    Button loadshoppingbuttton, loadrfitemlistbuttton, saveshoppingbuttton, saverfitemlistbuttton;
    JSONArray items = null;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getApplicationContext());
        setContentView(R.layout.activity_json);
        // 쇼핑리스트 데이터 가져오기
        loadshoppingbuttton = (Button) findViewById(R.id.loadshoppingbuttton) ;
        loadshoppingbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShoppingData("http://10.0.2.2/shoppinglistbackup.php");
            }
        }) ;
        // 냉장고아이템 데이터 가져오기
        loadrfitemlistbuttton = (Button) findViewById(R.id.loadrfitemlistbuttton) ;
        loadrfitemlistbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRfItemData("http://10.0.2.2/rfitemlistbackup.php");

            }
        }) ;

        saveshoppingbuttton = (Button) findViewById(R.id.saveshoppingbuttton) ;
        saveshoppingbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }) ;

        saverfitemlistbuttton = (Button) findViewById(R.id.saverfitemlistbuttton) ;
        saverfitemlistbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }) ;
    }

    protected void LoadShoppingData() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            items = jsonObj.getJSONArray("result");
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                String userid = c.getString("userid");
                String item = c.getString("item");
                String enrolldate= c.getString("enrolldate");
                int nf = c.getInt("nf");
                int direction = c.getInt("direction");
                int tf = c.getInt("tf");
                dbHelper.insert(userid, item, enrolldate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void LoadRfItemData() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            items = jsonObj.getJSONArray("result");
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                String userid = c.getString("userid");
                String item = c.getString("item");
                String enrolldate= c.getString("enrolldate");
                int amount = c.getInt("amount");
                String position = c.getString("position");
                String category = c.getString("category");
                String expirydate = c.getString("expirydate");
                String detail = c.getString("detail");
                dbHelper.insertrf(userid, item, enrolldate, amount, position, category, expirydate, detail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getShoppingData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                LoadShoppingData();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


    public void getRfItemData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                LoadRfItemData();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
