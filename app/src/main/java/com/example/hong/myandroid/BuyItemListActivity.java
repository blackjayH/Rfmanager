package com.example.hong.myandroid;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class BuyItemListActivity extends Fragment {
    private BuyItemNotification buyItemNotification;
    public static ArrayList<BuyItem> buyitemList = new ArrayList<>();
    public static ArrayList<String> buyitemnameList = new ArrayList<>();
    public static ArrayList<RfItem> rfitemList = new ArrayList<>();
    public static ArrayList<String> rfitemnameList = new ArrayList<>();
    public static ArrayList<String> buyitemenrolldateList = new ArrayList<>();
    EditText inputbuyitem;
    Button enrollbuyitem, dropbuyitem, restorebuyitem;
    DBHelper dbHelper; // = new DBHelper(getActivity(), "11.db", null, 1);
    ListView listView;
    public static ListviewAdapter adapter;
    String title = "ddd";
    String content = "ddd";

    public BuyItemListActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_buy, container, false);
        // 장바구니리스트에 등록
        inputbuyitem = (EditText) view.findViewById(R.id.inputbuyitem);

        dbHelper = new DBHelper(getContext());
        listView = (ListView) view.findViewById(R.id.listView);
        updateadapter();
        /*
         buyitemList.clear();
        buyitemList = dbHelper.getBuyItem();

        for (int i = buyitemList.size() - 1; i >= 0; i--) {
            if (buyitemList.get(i).getDirection() != 1) {
                buyitemList.remove(buyitemList.get(i));
            }
        }
        for (BuyItem bp : buyitemList) {
                adapter.add(bp);
                listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        }
         */


        //Notification
        buyItemNotification = new BuyItemNotification(getActivity());
        // Notification 추가메뉴
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            Notification.Builder nb = buyItemNotification.
                    getAndroidChannelNotification(title, "By " + content);

            buyItemNotification.getManager().notify(3, nb.build());
        }

        enrollbuyitem = (Button) view.findViewById(R.id.enrollbuyitem);
        enrollbuyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.execSQL("CREATE TABLE SHOPPINGLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, item TEXT, enrolldate TEXT, nf INTEGER, direction INTEGER, tf INTEGER);");
                /*
                int nf; int direction; int tf;
                 */
                String userid = "me";
                String item = inputbuyitem.getText().toString();
                Toast.makeText(getActivity().getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                long now = System.currentTimeMillis();
                Date inputdate = new Date(now);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String enrolldate = simpleDateFormat.format(inputdate);

                BuyItem tmp = null;
                dbHelper.insert(userid, item, enrolldate);
                tmp = new BuyItem(userid, item, enrolldate);
                buyitemList.add(tmp);
                adapter.add(tmp);
                adapter.notifyDataSetChanged();
                inputbuyitem.setText(null);
                //Toast.makeText(getActivity().getApplicationContext(),  dbHelper.getResult1(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity().getApplicationContext(),  "입력완료", Toast.LENGTH_SHORT).show();

                /*
                buyproductList.add(tmp);
                check.add(1);
                adapter.add(tmp);
                // adapter.notifyDataSetChanged();
                int tmp2 = 0;
                for (BuyProduct bp : buyproductList) {
                    if (bp.getLive() == 1)
                        tmp2++;
                }
                String str = buyproductList.size() + "";
                String temp = "ALL ( " + tmp2 + " / " + str + " )";
                all.setText(temp);
                //Toast.makeText(getApplicationContext(), item + "이 추가되었습니다", Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter);
                inputproduct.setText("");
                 */

            }
        });

        dropbuyitem = (Button) view.findViewById(R.id.dropbuyitem);
        dropbuyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.execSQL("CREATE TABLE SHOPPINGLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, item TEXT, enrolldate TEXT, nf INTEGER, direction INTEGER, tf INTEGER);");
                /*
                int nf; int direction; int tf;
                 */
                for (int i = buyitemList.size() - 1; i >= 0; i--) {
                    if (buyitemList.get(i).isCtf()) {
                        dbHelper.delete(buyitemList.get(i).getItem());
                        dbHelper.insert(buyitemList.get(i).getUserid(), buyitemList.get(i).getItem(), buyitemList.get(i).getEnrolldate(), 0, 0, 0);
                        adapter.remove(buyitemList.get(i));
                        buyitemList.remove(buyitemList.get(i));
                    }
                }
                updateadapter();
                /*
                buyproductList.add(tmp);
                check.add(1);
                adapter.add(tmp);
                // adapter.notifyDataSetChanged();
                int tmp2 = 0;
                for (BuyProduct bp : buyproductList) {
                    if (bp.getLive() == 1)
                        tmp2++;
                }
                String str = buyproductList.size() + "";
                String temp = "ALL ( " + tmp2 + " / " + str + " )";
                all.setText(temp);
                //Toast.makeText(getApplicationContext(), item + "이 추가되었습니다", Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter);
                inputproduct.setText("");
                 */

            }
        });
        // 휴지통 이동
        restorebuyitem = (Button) view.findViewById(R.id.restorebuyitem);
        restorebuyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrashItemListActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateadapter();
    }

    private void updateadapter() {
        adapter = new ListviewAdapter();
        buyitemList.clear();
        buyitemnameList.clear();
        buyitemenrolldateList.clear();
        buyitemList = dbHelper.getBuyItem();
        rfitemList = dbHelper.getRfItem();
        rfitemnameList.clear();
        sort();

        for (int i = buyitemList.size() - 1; i >= 0; i--) {
            if (buyitemList.get(i).getDirection() != 1) {
                buyitemList.remove(buyitemList.get(i));
                buyitemnameList.remove(buyitemList.get(i).getItem());
                buyitemenrolldateList.remove(buyitemList.get(i).getItem());
            }
        }

        for (BuyItem bp : buyitemList) {
            buyitemnameList.add(bp.getItem());
            buyitemenrolldateList.add(bp.getEnrolldate());
            adapter.add(bp);
        }
        for (RfItem rf : rfitemList) {
            rfitemnameList.add(rf.getItem());
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void sort() {
        //Comparator<BuyItem> comparator = new Comparator<BuyItem>()
        Comparator<RfItem> comparator = new Comparator<RfItem>()
        {
            Collator collator = Collator.getInstance();
            @Override
            //public int compare(BuyItem object1, BuyItem object2)
            public int compare(RfItem object1, RfItem object2)

            {
                return collator.compare(object1.getItem(), object2.getItem());
            }
        };
        //Collections.sort(buyitemList, comparator);
        Collections.sort(rfitemList, comparator);

        /*
        Comparator<BuyItem> comparator = new Comparator<BuyItem>()
        {
            Collator collator = Collator.getInstance();
            @Override
            public int compare(BuyItem object1, BuyItem object2)
            {
                return collator.compare(object1.getItem(), object2.getItem());
            }
        };
        Collections.sort(buyitemList, comparator);
         */


    }



}