package com.poly.miwth.asm_ps28372;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.poly.miwth.asm_ps28372.Adapter.NhanVienAdapter;
import com.poly.miwth.asm_ps28372.Object.NhanVienObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListNhanVien extends AppCompatActivity {
    ArrayList<NhanVienObject> list = new ArrayList<>();
    Toolbar toolbar;
    FirebaseAuth mAuth;

    TextView txt_thongBao;
    ListView lv_NV;
    NhanVienAdapter listAdapter;
    Button themNV;
    ActivityResultLauncher<Intent> getNewNV = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                NhanVienObject newNV = (NhanVienObject) result.getData().getSerializableExtra("newNV");
                list.add(newNV);
                writeListNV();
                setThongBaoStatus();
                listAdapter.notifyDataSetChanged();
            }
        }
    });
    ActivityResultLauncher<Intent> updateNV = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Bundle bundle = result.getData().getExtras();
                NhanVienObject updatedNV = (NhanVienObject) bundle.getSerializable("nhanvien");
                int position = bundle.getInt("position");
                list.set(position, updatedNV);
                writeListNV();
                setThongBaoStatus();
                listAdapter.notifyDataSetChanged();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nhan_vien);
        mAuth = FirebaseAuth.getInstance();
//        ánh xạ widget
        txt_thongBao = findViewById(R.id.txt_thongBao);
        lv_NV = findViewById(R.id.lv_NV);
        themNV = findViewById(R.id.btn_themNV);
//        toolbar
        toolbar = findViewById(R.id.tool_bar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_navigate_before_24);

//       đọc dữ liệu nhân viên
        getListNV();
        setListNV();

        themNV.setOnClickListener(v -> getNewNV.launch(new Intent(ListNhanVien.this, AddNhanVien.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nhanvien_menu, menu);
        // search bar search filter list
        MenuItem searchItem = menu.findItem(R.id.menu_search);
       SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm kiếm nhân viên");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        if (itemId == R.id.menu_welcome) {
            startActivity(new Intent(ListNhanVien.this, WelcomeScreen.class));
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
            startActivity(new Intent(ListNhanVien.this, LoginScreen.class));
            mAuth.signOut();
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void getListNV() {
        FileInputStream fileInputStream = null;
        File filesPath = getFilesDir();
        File file = new File(filesPath, "list_nv.dat");
        if (file.exists()) {
            try {
                fileInputStream = openFileInput("list_nv.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                list = (ArrayList<NhanVienObject>) objectInputStream.readObject();
                setThongBaoStatus();
                setListNV();
                objectInputStream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            txt_thongBao.setVisibility(View.VISIBLE);
            lv_NV.setVisibility(View.GONE);
        }
    }

    private void writeListNV() {
        File file = new File(getFilesDir(), "list_nv.dat");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(file.getName(), Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setThongBaoStatus() {
        if (list.isEmpty()) {
            txt_thongBao.setVisibility(View.VISIBLE);
            lv_NV.setVisibility(View.GONE);
        }
        txt_thongBao.setVisibility(View.GONE);
        lv_NV.setVisibility(View.VISIBLE);
    }

    public void setListNV() {
        listAdapter = new NhanVienAdapter(this, list);
        lv_NV.setAdapter(listAdapter);
    }

    public void xoaNV(int position) {
        list.remove(position);
        writeListNV();
        listAdapter.notifyDataSetChanged();
        setThongBaoStatus();
    }

    public void updateNhanVien(int postion) {
        Intent intent = new Intent(ListNhanVien.this, UpdateNhanVien.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("nhanvien", list.get(postion));
        bundle.putInt("position", postion);
        intent.putExtras(bundle);
        updateNV.launch(intent);
    }
}
