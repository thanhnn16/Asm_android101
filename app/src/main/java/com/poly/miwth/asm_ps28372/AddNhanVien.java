package com.poly.miwth.asm_ps28372;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.poly.miwth.asm_ps28372.Object.NhanVienObject;

public class AddNhanVien extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText edtMaNV;
    TextInputEditText edtTenNV;
    Spinner spn_PhongBan;
    Button btnThemNV;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        mAuth = FirebaseAuth.getInstance();
        edtMaNV = findViewById(R.id.edtMaNV);
        edtTenNV = findViewById(R.id.edtTenNV);
        spn_PhongBan = findViewById(R.id.spnPhongBan);
        btnThemNV = findViewById(R.id.btn_AddNV);
        toolbar = findViewById(R.id.tool_bar);
        toolbar = findViewById(R.id.tool_bar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_navigate_before_24);
        String[] phongBanList = new String[]{
                "Nhân sự",
                "Hành chính",
                "Đào tạo"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phongBanList);
        spn_PhongBan.setAdapter(spinnerAdapter);

        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNv = edtMaNV.getText().toString().trim();
                String tenNv = edtTenNV.getText().toString().trim();
                String phongBan = spn_PhongBan.getSelectedItem().toString().trim();
                NhanVienObject nhanVien = new NhanVienObject(maNv, tenNv, phongBan);
                Toast.makeText(AddNhanVien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("newNV", nhanVien);
                setResult(RESULT_OK, result);
                finish();
            }
        });
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
            startActivity(new Intent(AddNhanVien.this, WelcomeScreen.class));
            finish();
        }
        if (itemId == R.id.menu_about_me) {
            Toast.makeText(this, "Nông Nguyễn Thành\nPS28372", Toast.LENGTH_SHORT).show();
        }
        if (itemId == R.id.menu_logout) {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("remember");
            editor.apply();
            startActivity(new Intent(AddNhanVien.this, LoginScreen.class));
            mAuth.signOut();
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}