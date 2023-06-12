package com.poly.miwth.asm_ps28372.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.poly.miwth.asm_ps28372.Object.NhanVienObject;
import com.poly.miwth.asm_ps28372.R;

import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<NhanVienObject> list;
    private List<NhanVienObject> filteredList;

    public NhanVienAdapter(Context context, List<NhanVienObject> list) {
        this.context = context;
        this.list = list;
        this.filteredList = new ArrayList<>(list); // Initialize filtered list with the original list
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.nhan_vien_item_list, parent, false);
        }

        TextView txtMaNV = convertView.findViewById(R.id.txt_maNV);
        TextView hoTenNV = convertView.findViewById(R.id.txt_tenNV);
        TextView phongBan = convertView.findViewById(R.id.txt_phongBanNV);

        NhanVienObject nv = filteredList.get(position);

        txtMaNV.setText(nv.getMaNV());
        hoTenNV.setText(nv.getHoTenNV());
        phongBan.setText(nv.getPhongBan());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<NhanVienObject> filtered = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filtered.addAll(list);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (NhanVienObject nv : list) {
                        if (nv.getHoTenNV().toLowerCase().contains(filterPattern)
                                || nv.getMaNV().toLowerCase().contains(filterPattern)
                                || nv.getPhongBan().toLowerCase().contains(filterPattern)) {
                            filtered.add(nv);
                        }
                    }
                }

                results.values = filtered;
                results.count = filtered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<NhanVienObject>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
