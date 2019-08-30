package com.example.hong.myandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity implements ItemListFragment.Callbacks{
   // public static ListViewAdapter adapter;
    ///public static ArrayList<Integer> check = new ArrayList<>(); // 체크박스 상태로 1,0 만 삽입 1 : 체크X / 0 체크O
    //public static ArrayList<BuyProduct> buyproductList = new ArrayList<>(); // 장바구니 리스트
    //public static ArrayList<BuyProduct> backupList = new ArrayList<>(); // 백업리스트 : 장바구니에서 지웠던 품목들로 모아 놓은것
    //public static TextView all; // ALL ( / ) 텍스트뷰
    FragmentTabHost host;

    @Override
    public void onItemSelected(int id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        host.setup(this, getSupportFragmentManager(), R.id.content);

        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1"); // 구분자
        tabSpec1.setIndicator("1"); // 탭 이름
        host.addTab(tabSpec1, BuyItemListActivity.class, null);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2"); // 구분자
        tabSpec2.setIndicator("2"); // 탭 이름
        host.addTab(tabSpec2, RfitemListActivity.class, null);

        TabHost.TabSpec tabSpec3 = host.newTabSpec("tab3"); // 구분자
        tabSpec3.setIndicator("3"); // 탭 이름
        host.addTab(tabSpec3, AddItemActivity.class, null);

        host.setCurrentTab(0);


    }
}