package com.example.hong.myandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity implements ItemListFragment.Callbacks{
    FragmentTabHost host;
    private boolean mTwoPane; //

    @Override
    public void onItemSelected(int id) {

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            ((ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.item_list)).setActivateOnItemClick(true);
        }

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