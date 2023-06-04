package com.poly.miwth.asm_ps28372;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.poly.miwth.asm_ps28372.Adapter.PhongBanAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class PhongBan extends AppCompatActivity {
    ArrayList<String> list;
    ListView lvPhongBan;
    Toolbar toolbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_ban);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_navigate_before_24);
        lvPhongBan = findViewById(R.id.lvPhongBan);
        list = new ArrayList<>();
        String[] phongBan = {"Nhân sự", "Hành chính", "Đào tạo"};
        list.addAll(Arrays.asList(phongBan));
        PhongBanAdapter phongBanAdapter = new PhongBanAdapter(list, this);
        lvPhongBan.setAdapter(phongBanAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        if (itemId == R.id.menu_welcome) {
            startActivity(new Intent(PhongBan.this, WelcomeScreen.class));
            finish();
        }
        if (itemId == R.id.menu_about_me) {
            Toast.makeText(this, "Nông Nguyễn Thành\nPS28372", Toast.LENGTH_SHORT).show();
        }if (itemId == R.id.menu_logout) {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("remember");
            editor.apply();
            startActivity(new Intent(PhongBan.this, LoginScreen.class));
            mAuth.signOut();
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}