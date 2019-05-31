package com.example.hong.myandroid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter {
    private ArrayList<BuyItem> buyitemList = new ArrayList<>();
    ///boolean isChecked;

    @Override
    public int getCount() {
        return buyitemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomViewHolder holder;
        final int index = position;
        final Context context = parent.getContext();

        if (convertView == null) { // Item Cell에 Layout을 적용시킬 Inflater 객체를 생성한다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_buylist, parent, false);
            holder = new CustomViewHolder();
            holder.m_itemname = (TextView) convertView.findViewById(R.id.itemname);
            holder.m_checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }
        holder.m_itemname.setText(buyitemList.get(index).getItem());
/*
        if (buyitemList.get(index).getNf() == 1) {
            holder.m_itemname.setTextColor(Color.parseColor("#FF1111")); // 빨간색으로 설정
            holder.m_itemname.setPaintFlags(holder.m_itemname.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // 줄긋기
        }

        holder.m_itemname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyitemList.get(index).getNf() == 0) {
                    //Toast.makeText(context, index + "번째 선택 ", Toast.LENGTH_SHORT).show();
                    buyitemList.get(index).setNf(1);
                }
                else{
                    //Toast.makeText(context, index + "번째 선택 ", Toast.LENGTH_SHORT).show();
                    buyitemList.get(index).setNf(0);
                }
                notifyDataSetChanged();
            }
        });


 */
        holder.m_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyitemList.get(index).getTf() == 0) {
                    buyitemList.get(index).setTf(1);
                    buyitemList.get(index).setCtf(true);
                } else {
                    buyitemList.get(index).setTf(0);
                    buyitemList.get(index).setCtf(false);
                }
                notifyDataSetChanged();
            }
        });
        holder.m_checkbox.setChecked(buyitemList.get(position).isCtf());
        return convertView;
    }


    public class CustomViewHolder {
        public TextView m_itemname;
        public CheckBox m_checkbox;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return buyitemList.get(position);
    }

    public void add(BuyItem np) {
        buyitemList.add(np);
    }

    public void remove(BuyItem np) {
        buyitemList.remove(np);
    }

}

