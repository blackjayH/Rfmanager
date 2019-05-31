package com.example.hong.myandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.hong.myandroid.BuyItemListActivity.adapter;
import static com.example.hong.myandroid.BuyItemListActivity.buyitemList;

public class TrashItemListActivity extends AppCompatActivity {
    Button allcheckbackupitem, backupitem, back;
    DBHelper dbHelper2; // = new DBHelper(getActivity(), "11.db", null, 1);
    ListView listView2;
    public static ListviewAdapter2 adapter2;
    private ArrayList<BuyItem> trashbuyitemList = new ArrayList<>();

    public TrashItemListActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        dbHelper2 = new DBHelper(getApplicationContext());
        adapter2 = new ListviewAdapter2();
        trashbuyitemList = dbHelper2.getBuyItem();

        for (int i = trashbuyitemList.size() - 1; i >= 0; i--) {
            if (trashbuyitemList.get(i).getDirection() != 0) {
                trashbuyitemList.remove(trashbuyitemList.get(i));
            }
        }
        for (BuyItem bp : trashbuyitemList)
            adapter2.add(bp);
        listView2 = (ListView) findViewById(R.id.listView2);
        listView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        // 체크박스 전체 선택
        Button allcheckbackupitem = (Button) findViewById(R.id.allcheckbackupitem);
        allcheckbackupitem.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (BuyItem bp : trashbuyitemList) {
                            if (bp.getTf() == 0) {
                                bp.setTf(1);
                                bp.setCtf(true);
                            } else {
                                bp.setTf(0);
                                bp.setCtf(false);
                            }
                        }
                        adapter2.notifyDataSetChanged();
                    }
                }
        );

        // 휴지통에서 복원
        Button backupitem = (Button) findViewById(R.id.backupitem);
        backupitem.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = trashbuyitemList.size() - 1; i >= 0; i--) {
                            if (trashbuyitemList.get(i).isCtf()) {
                                dbHelper2.delete(trashbuyitemList.get(i).getItem());
                                dbHelper2.insert(trashbuyitemList.get(i).getUserid(), trashbuyitemList.get(i).getItem(), trashbuyitemList.get(i).getEnrolldate());
                                adapter2.remove(trashbuyitemList.get(i));
                                trashbuyitemList.remove(trashbuyitemList.get(i));
                            }
                        }
                        adapter2.notifyDataSetChanged();
                    }
                }
        );

        // 되돌아가기 버튼
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}