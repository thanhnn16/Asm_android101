package com.poly.miwth.asm_ps28372.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.poly.miwth.asm_ps28372.Object.NhanVienObject;

import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends BaseAdapter {
    List<NhanVienObject> list = new ArrayList<>();

    @Override
    public int getCount() {
        if (list.isEmpty()) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        return null;
    }
}
