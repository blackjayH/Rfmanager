package com.example.hong.myandroid;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    EditText emailInput2, passwordInput2, passwordInputcheck2;
    CheckBox idcheckBox;
    String id, pw, pw2;
    String url = "http://10.0.2.2/check.php";
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        // 위젯에 대한 참조.
        emailInput2 = (EditText) findViewById(R.id.emailInput2);
        passwordInput2 = (EditText) findViewById(R.id.passwordInput2);
        passwordInputcheck2 = (EditText) findViewById(R.id.passwordInputcheck2);
        Button joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = emailInput2.getText().toString();
                pw = passwordInput2.getText().toString();
                pw2 = passwordInputcheck2.getText().toString();
                if (pw.equals(pw2))
                {
                    ContentValues values = new ContentValues();
                    values.put("id", id);
                    values.put("pw", pw);
                    NetworkTask networkTask = new NetworkTask(url, values);
                    networkTask.execute();
                }
                else
                    Toast.makeText(getApplicationContext(), "비밀번호를 체크해주세요.", Toast.LENGTH_LONG).show();
            }
        });

        idcheckBox = (CheckBox) findViewById(R.id.idcheckBox) ;
        idcheckBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idcheckBox.isChecked()) {
                    Toast.makeText(JoinActivity.this, "체크", Toast.LENGTH_SHORT).show();
                    if (emailInput2.getText().toString().equals(""))
                    {
                        Toast.makeText(JoinActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        idcheckBox.toggle();
                    }
                    else
                    {
                        //dialog = ProgressDialog.show(JoinTestActivity.this, "", "아이디를 중복 여부를 체크하고있습니다.", true);
                        check(emailInput2.getText().toString());
                    }
                } else {
                    Toast.makeText(JoinActivity.this, "체크해제되었습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        }) ;
    }

    private void check(String str) {
        ContentValues values = new ContentValues();
        values.put("username", str);
        NetworkTask networkTask = new NetworkTask(url, values);
        networkTask.execute();
    }

    private void alarmcheck(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        if(str.equals("사용불가능한 아이디입니다."))
        {
            builder.setTitle("iD 사용여부 확인");
            builder.setMessage("사용할 수 없는 아이디입니다. 다시 입력해주세요");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    idcheckBox.toggle();
                }
            });
        }
        else if(str.equals("사용가능한 아이디입니다."))
        {
            builder.setTitle("iD 사용여부 확인");
            builder.setMessage("이 아이디를 사용하시겠습니까?");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    idcheckBox.setEnabled(false);
                    emailInput2.setEnabled(false);
                }
            });
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    idcheckBox.toggle();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showfail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("iD 사용여부 확인");
        builder.setMessage("사용할 수 없는 아이디입니다. 다시 입력해주세요");
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idcheckBox.toggle();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showtrue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("iD 사용여부 확인");
        builder.setMessage("이 아이디를 사용하시겠습니까?");
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idcheckBox.setEnabled(false);
                emailInput2.setEnabled(false);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idcheckBox.toggle();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
            alarmcheck(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //passwordInputcheck2.setText(s);
        }
    }

}
