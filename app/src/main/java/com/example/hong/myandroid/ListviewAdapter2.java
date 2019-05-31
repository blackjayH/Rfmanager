package com.example.hong.myandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ListviewAdapter2 extends BaseAdapter {
    public ArrayList<BuyItem> trashbuyitemList = new ArrayList<>();
    ///boolean isChecked;

    @Override
    public int getCount() {
        return trashbuyitemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomViewHolder holder;
        final int index = position;
        final Context context = parent.getContext();

        if (convertView == null) { // Item Cell에 Layout을 적용시킬 Inflater 객체를 생성한다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_trashbuylist, parent, false);
            holder = new CustomViewHolder();
            holder.m_itemname = (TextView) convertView.findViewById(R.id.itemname2);
            holder.m_checkbox = (CheckBox) convertView.findViewById(R.id.checkbox2);
            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        holder.m_itemname.setText(trashbuyitemList.get(index).getItem());
        holder.m_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trashbuyitemList.get(index).getTf() == 0) {
                    trashbuyitemList.get(index).setTf(1);
                    trashbuyitemList.get(index).setCtf(true);
                } else {
                    trashbuyitemList.get(index).setTf(0);
                    trashbuyitemList.get(index).setCtf(false);
                }
                notifyDataSetChanged();
            }
        });
        holder.m_checkbox.setChecked(trashbuyitemList.get(position).isCtf());
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
        return trashbuyitemList.get(position);
    }

    public void add(BuyItem np) {
        trashbuyitemList.add(np);
    }

    public void remove(BuyItem np) {
        trashbuyitemList.remove(np);
    }

}
