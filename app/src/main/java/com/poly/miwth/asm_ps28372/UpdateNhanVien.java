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

public class UpdateNhanVien extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextInputEditText edtMaNV;
    TextInputEditText edtTenNV;
    Spinner spnPhongBan;
    Button btn_Update;
    int position;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nhan_vien);
        toolbar = findViewById(R.id.tool_bar);
        toolbar = findViewById(R.id.tool_bar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_navigate_before_24);
        mAuth = FirebaseAuth.getInstance();
        edtMaNV = findViewById(R.id.edtMaNV);
        edtTenNV = findViewById(R.id.edtTenNV);
        spnPhongBan = findViewById(R.id.spnPhongBan);
        btn_Update = findViewById(R.id.btn_capNhatNV);

        String[] phongBanList = new String[]{
                "Nhân sự",
                "Hành chính",
                "Đào tạo"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phongBanList);
        spnPhongBan.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        NhanVienObject currentNV = (NhanVienObject) bundle.getSerializable("nhanvien");
        position = bundle.getInt("position");
        edtMaNV.setText(currentNV.getMaNV());
        edtTenNV.setText(currentNV.getHoTenNV());
        String phongBan = currentNV.getPhongBan();
        int selectionIndex = spinnerAdapter.getPosition(phongBan);
        spnPhongBan.setSelection(selectionIndex);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNv = edtMaNV.getText().toString().trim();
                String tenNv = edtTenNV.getText().toString().trim();
                String phongBan = spnPhongBan.getSelectedItem().toString().trim();
                NhanVienObject nhanVien = new NhanVienObject(maNv, tenNv, phongBan);
                Toast.makeText(UpdateNhanVien.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("nhanvien", nhanVien);
                bundle.putInt("position", position);
                result.putExtras(bundle);
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
            startActivity(new Intent(UpdateNhanVien.this, WelcomeScreen.class));
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
            startActivity(new Intent(UpdateNhanVien.this, LoginScreen.class));
            mAuth.signOut();
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}