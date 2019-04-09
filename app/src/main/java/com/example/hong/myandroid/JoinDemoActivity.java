package com.example.hong.myandroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class JoinDemoActivity  extends AppCompatActivity {
    EditText emailInput2, passwordInput2, passwordInputcheck2;
    String id, pw, pw2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        emailInput2 = (EditText) findViewById(R.id.emailInput2);
        passwordInput2 = (EditText) findViewById(R.id.passwordInput2);
        passwordInputcheck2 = (EditText) findViewById(R.id.passwordInputcheck2);


        // DB ID 중복 체크 / 비밀번호 체크

        Button joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = emailInput2.getText().toString();
                pw = passwordInput2.getText().toString();
                pw2 = passwordInputcheck2.getText().toString();
                if (pw.equals(pw2))
                    insertoToDatabase(id, pw);
                else
                    Toast.makeText(getApplicationContext(), "비밀번호를 체크해주세요.", Toast.LENGTH_LONG).show();
                // Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                //startActivity(intent);
            }
        });


    }


    private void insertoToDatabase(String id, String pw) {
        class InsertData extends AsyncTask<String, Void, String> {
            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(JoinDemoActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();


            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String id = (String) params[0];
                    String pw = (String) params[1];

                    String link = "http://10.0.2.2/login.php";

                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("pw", "UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(id, pw);

    }

}