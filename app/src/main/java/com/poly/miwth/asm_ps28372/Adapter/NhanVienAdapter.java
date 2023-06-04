package com.poly.miwth.asm_ps28372.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.poly.miwth.asm_ps28372.ListNhanVien;
import com.poly.miwth.asm_ps28372.Object.NhanVienObject;
import com.poly.miwth.asm_ps28372.R;

import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends BaseAdapter {
    Context context;
    List<NhanVienObject> list = new ArrayList<>();

    public NhanVienAdapter(Context context, List<NhanVienObject> list) {
        this.context = context;
        this.list = list;
    }

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
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.nhan_vien_item_list, parent, false);
        TextView txtMaNV = convertView.findViewById(R.id.txt_maNV);
        TextView hoTenNV = convertView.findViewById(R.id.txt_tenNV);
        TextView phongBan = convertView.findViewById(R.id.txt_phongBanNV);
        txtMaNV.setText(list.get(position).getMaNV());
        hoTenNV.setText(list.get(position).getHoTenNV());
        phongBan.setText(list.get(position).getPhongBan());

        Button btnUpdate = convertView.findViewById(R.id.btn_capNhat);
        Button btnDelete = convertView.findViewById(R.id.btn_xoa);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListNhanVien)context).updateNhanVien(position);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListNhanVien) context).xoaNV(position);
            }
        });

        return convertView;
    }
}
