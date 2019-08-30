package com.example.hong.myandroid;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
        loadshoppingbuttton = (Button) findViewById(R.id.loadshoppingbuttton);
        loadshoppingbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShoppingData("http://10.0.2.2/shoppinglistbackup.php");
            }
        });

        // 냉장고아이템 데이터 가져오기
        loadrfitemlistbuttton = (Button) findViewById(R.id.loadrfitemlistbuttton);
        loadrfitemlistbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRfItemData("http://10.0.2.2/rfitemlistbackup.php");

            }
        });

        // 쇼핑리스트 데이터 백업
        saveshoppingbuttton = (Button) findViewById(R.id.saveshoppingbuttton);
        saveshoppingbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveShoppingData();

            }
        });

        // 냉장고아이템 데이터 백업
        saverfitemlistbuttton = (Button) findViewById(R.id.saverfitemlistbuttton);
        saverfitemlistbuttton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveRfitemData();
            }
        });
    }

    protected void SaveShoppingData() {
        ArrayList<BuyItem> temp;
        temp = dbHelper.getBuyItem();
        String str;
        try {
            JSONArray jArray = new JSONArray();
            for (BuyItem by : temp) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("userid", by.getUserid());
                jsonObject1.put("item", by.getItem());
                jsonObject1.put("enrolldate", by.getEnrolldate());
                jsonObject1.put("nf", by.getNf());
                jsonObject1.put("direction", by.getDirection());
                jsonObject1.put("tf", by.getTf());
                jArray.put(jsonObject1);
            }
            str = jArray.toString();
            ContentValues values = new ContentValues();
            values.put("json", str);
            NetworkTask networkTask = new NetworkTask("http://10.0.2.2/saveshoppinglist.php", values);
            Toast.makeText(JsonActivity.this, str, Toast.LENGTH_SHORT).show();
            networkTask.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void SaveRfitemData() {
        ArrayList<RfItem> temp;
        temp = dbHelper.getRfItem();
        String str;
        try {
            JSONArray jArray = new JSONArray();
            for (RfItem by : temp) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("userid", by.getUserid());
                jsonObject1.put("item", by.getItem());
                jsonObject1.put("enrolldate", by.getEnrolldate());
                jsonObject1.put("amount", by.getAmount());
                jsonObject1.put("position", by.getPosition());
                jsonObject1.put("category", by.getCategory());
                jsonObject1.put("expirydate", by.getExpirydate());
                jsonObject1.put("detail", by.getDetail());
                jArray.put(jsonObject1);
            }
            str = jArray.toString();
            ContentValues values = new ContentValues();
            values.put("json", str);
            NetworkTask networkTask = new NetworkTask("http://10.0.2.2/saverfitemlist.php", values);
            Toast.makeText(JsonActivity.this, str, Toast.LENGTH_SHORT).show();
            networkTask.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void LoadShoppingData() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            items = jsonObj.getJSONArray("result");
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                String userid = c.getString("userid");
                String item = c.getString("item");
                String enrolldate = c.getString("enrolldate");
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
                String enrolldate = c.getString("enrolldate");
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

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(JsonActivity.this, s, Toast.LENGTH_SHORT).show();
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
