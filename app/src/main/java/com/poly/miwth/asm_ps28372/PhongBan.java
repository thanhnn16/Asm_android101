package com.poly.miwth.asm_ps28372;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.poly.miwth.asm_ps28372.Adapter.PhongBanAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class PhongBan extends AppCompatActivity {
    ArrayList<String> list;
    Context context = this;
    ListView lvPhongBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_ban);
        lvPhongBan = findViewById(R.id.lvPhongBan);
        list = new ArrayList<>();
        String[] phongBan = {"Nhân sự", "Hành chính", "Đào tạo"};
        list.addAll(Arrays.asList(phongBan));
        PhongBanAdapter phongBanAdapter = new PhongBanAdapter(list, this);
        lvPhongBan.setAdapter(phongBanAdapter);

    }
}