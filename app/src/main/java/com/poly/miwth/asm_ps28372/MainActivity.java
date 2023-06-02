package com.poly.miwth.asm_ps28372;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnThoat, btnDangXuat, btnPhongBan, btnNhanVien;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        btnThoat = findViewById(R.id.btnThoat);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnPhongBan = findViewById(R.id.btnPhongBan);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        mAuth = FirebaseAuth.getInstance();

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn thoát khỏi ứng dụng?").setCancelable(false).setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        btnPhongBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhongBan.class));
            }
        });

        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Nhân viên", Toast.LENGTH_SHORT).show();
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận");

                builder.setMessage("Bạn có muốn đăng xuất hay không?").setCancelable(false).setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String email = currentUser.getEmail();
                        Toast.makeText(MainActivity.this, "Đăng xuất thành công\nHẹn gặp lại " + email, Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("remember");
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LoginScreen.class));
                        finish();
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_welcome) {
            startActivity(new Intent(MainActivity.this, WelcomeScreen.class));
            finish();
        }
        if (itemId == R.id.menu_about_me) {
            Toast.makeText(this, "Nông Nguyễn Thành\nPS28372", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.menu_logout) {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("remember");
            editor.apply();
            startActivity(new Intent(MainActivity.this, LoginScreen.class));
            FirebaseAuth.getInstance().signOut();
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}