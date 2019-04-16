package com.example.hong.myandroid;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends Fragment3 {
    private String str = "https://www.beepscan.com/barcode/";
    private String htmlContentInStringFormat = "";
    Calendar cal = Calendar.getInstance();
    EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7;
    TextView sqlresult; //title1, title2, title3, title4, title5, title6, title7;
    Button barcode, stt, inputbutton1, inputbutton2, inputbutton3, inputbutton4, inputbutton5, inputbutton6, gotomain, gotosave, gotodel;
    DBHelper dbHelper;

    public AddItemActivity() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        str += intent.getStringExtra("result");
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_additem, container, false);
        dbHelper = new DBHelper(getContext());
      /*
        title1 = (TextView) view.findViewById(R.id.title1);
        title2 = (TextView) view.findViewById(R.id.title2);
        title3 = (TextView) view.findViewById(R.id.title3);
        title4 = (TextView) view.findViewById(R.id.title4);
        title5 = (TextView) view.findViewById(R.id.title5);
        title6 = (TextView) view.findViewById(R.id.title6);
        title7 = (TextView) view.findViewById(R.id.title7);
       */
        sqlresult = (TextView) view.findViewById(R.id.sqlresult);
        edit1 = (EditText) view.findViewById(R.id.edit1); // 상품명
        edit2 = (EditText) view.findViewById(R.id.edit2); // 수량
        edit3 = (EditText) view.findViewById(R.id.edit3); // 보관방법
        edit4 = (EditText) view.findViewById(R.id.edit4); // 유통기한
        edit5 = (EditText) view.findViewById(R.id.edit5); // 카테고리
        edit6 = (EditText) view.findViewById(R.id.edit6); // 등록일짜
        edit7 = (EditText) view.findViewById(R.id.edit7); // 메모(세부사항)

        //Intent intent = getIntent(); /** * 객체를 받아옵니다. */
        //edit1.setText(intent.getStringExtra("str"));
        barcode = (Button) view.findViewById(R.id.barcode); // 바코드 입력
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), BacodeActivity.class), 1002);
            }
        });

        stt = (Button) view.findViewById(R.id.stt); // stt 입력
        stt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VoiceActivity.class);
                startActivity(intent);
                /*
                NotificationManager notificationManager;
                PendingIntent intent;
                intent = PendingIntent.getActivity(getActivity(), 0,
                        new Intent(getActivity(), AddItemActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                        Notification.Builder builder = new Notification.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher_background) // 아이콘 설정하지 않으면 오류남
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentTitle("알림 제목") // 제목 설정
                        .setContentText("알림 내용") // 내용 설정
                        .setTicker("한줄 출력") // 상태바에 표시될 한줄 출력
                        .setAutoCancel(false)
                        .setContentIntent(intent);
                notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());


                 */
                Toast.makeText(getActivity(), "노티 ", Toast.LENGTH_SHORT).show();
            }


        });

        inputbutton1 = (Button) view.findViewById(R.id.inputbutton1); // 실온
        inputbutton1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                edit3.setText(inputbutton1.getText());
            }
        });

        inputbutton2 = (Button) view.findViewById(R.id.inputbutton2); // 냉장
        inputbutton2.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                edit3.setText(inputbutton2.getText());
            }
        });

        inputbutton3 = (Button) view.findViewById(R.id.inputbutton3); // 냉동
        inputbutton3.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                edit3.setText(inputbutton3.getText());
            }
        });

        inputbutton4 = (Button) view.findViewById(R.id.inputbutton4); // 유통기한 달력
        inputbutton4.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        String msg = String.format("%d/%d/%d", year, month + 1, date);
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        edit4.setText(msg);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
            }
        });

        inputbutton5 = (Button) view.findViewById(R.id.inputbutton5); // 카테고리
        inputbutton5.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                edit5.setText("default");
            }
        });

        inputbutton6 = (Button) view.findViewById(R.id.inputbutton6); // 등록일짜 달력
        long now = System.currentTimeMillis();
        Date inputdate = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d");
        String enrolldate = simpleDateFormat.format(inputdate);
        edit6.setText(enrolldate);

        gotomain = (Button) view.findViewById(R.id.gotomain); // 메인
        gotomain.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                //String str = "https://www.beepscan.com/barcode/8809022207013";

            }
        });

        gotosave = (Button) view.findViewById(R.id.gotosave); // 저장
        gotosave.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                saveInput();
                sqlresult.setText(dbHelper.getResult());
                Toast.makeText(getActivity(), dbHelper.getResult(), Toast.LENGTH_SHORT).show();
            }
        });

        gotodel = (Button) view.findViewById(R.id.gotodel); // 삭제
        gotodel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                resetInput();
            }
        });
        return view;
    }

    private void resetInput() {
        edit1.setText("");
        edit2.setText("");
        edit3.setText("");
        edit4.setText("");
        edit5.setText("");
        edit7.setText("");
    }

    private void saveInput() {
        //String userid 유저아이디, String item 상품명, String enrolldate 등록일, int amount 수량, String position 보관방법, String category 카테고리, String expirydate 유통기한, String detail 메모
        String userid = "me";
        String str1 = edit1.getText().toString(); // 상품명
        int str2 = Integer.parseInt(edit2.getText().toString()); // 수량
        String str3 = edit3.getText().toString(); // 보관방법
        String str4 = edit4.getText().toString(); // 유통기한
        String str5 = edit5.getText().toString(); // 카테고리
        String str6 = edit6.getText().toString(); // 등록일짜
        String str7 = edit7.getText().toString(); // 메모(세부사항)
        dbHelper.insertrf(userid, str1, str6, str2, str3, str5, str4, str7);
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(str).get();
                Elements element = doc.select("div.container");
                String title = element.select("p").text();

                for (Element e : element) {
                    htmlContentInStringFormat += title;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            edit1.setText(htmlContentInStringFormat);
        }
    }
}
