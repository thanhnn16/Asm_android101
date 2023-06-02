package com.poly.miwth.asm_ps28372.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.poly.miwth.asm_ps28372.R;

import java.util.ArrayList;

public class PhongBanAdapter extends BaseAdapter {

    ArrayList<String> list;
    Context context;

    public PhongBanAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.phong_ban_item_list, parent, false);

        TextView tenPhongBan = convertView.findViewById(R.id.txt_TenPhongBan);
        tenPhongBan.setText(list.get(position).toString());

        return convertView;
    }
}
