package com.example.hong.myandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BuyItemListActivity extends Fragment {
    private ArrayList<BuyItem> buyitemList = new ArrayList<>();
    EditText inputbuyitem;
    Button enrollbuyitem, dropbuyitem;
    DBHelper dbHelper; // = new DBHelper(getActivity(), "11.db", null, 1);
    ListView listView;
    public static ListviewAdapter adapter;

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
        adapter = new ListviewAdapter();
        dbHelper = new DBHelper(getContext());
        buyitemList.clear();
        buyitemList = dbHelper.getBuyItem();
        if(buyitemList.size() != 0){
            for (BuyItem bp : buyitemList) {
                adapter.add(bp);
            }
        }
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        dropbuyitem = (Button) view.findViewById(R.id.dropbuyitem);
        dropbuyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.execSQL("CREATE TABLE SHOPPINGLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, item TEXT, enrolldate TEXT, nf INTEGER, direction INTEGER, tf INTEGER);");
                /*
                int nf; int direction; int tf;
                 */
                for (int i = buyitemList.size() - 1; i >= 0; i--){
                    if (buyitemList.get(i).isCtf()) {
                        dbHelper.delete(buyitemList.get(i).getItem());
                        adapter.remove(buyitemList.get(i));
                        buyitemList.remove(buyitemList.get(i));
                    }
                }





                adapter.notifyDataSetChanged();
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
                //dbHelper = new DBHelper(getContext());
                dbHelper.insert(userid, item, enrolldate);
                tmp = new BuyItem(userid, item, enrolldate);
                adapter.add(tmp);
                adapter.notifyDataSetChanged();
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
        return view;
    }


}