package com.poly.miwth.asm_ps28372;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    final int DELAY_TIME = 1000;
    TextView txtSignupNow, btnText;
    RelativeLayout btnLoginLayout;
    LottieAnimationView btnAnimation;
    EditText edtUsername, edtPassword;
    SharedPreferences sharedPreferences;
    CheckBox rmbMe;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        txtSignupNow = findViewById(R.id.txtSignupNow);
        btnAnimation = findViewById(R.id.btnAnimation);
        btnLoginLayout = findViewById(R.id.btnLogin);
        btnText = findViewById(R.id.btnText);
        rmbMe = findViewById(R.id.chkRemember);
        edtUsername = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPw);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        txtSignupNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, SignupScreen.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlayAnimation();
                String email = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                boolean rememberMeChecked = rmbMe.isChecked();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginScreen.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(LoginScreen.this::resetButton, DELAY_TIME);
                } else {
                    if (rememberMeChecked) {
                        editor.putBoolean("remember", true);
                        editor.apply();
                    } else {
                        editor.clear();
                        editor.apply();
                    }
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginScreen.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(LoginScreen.this::resetButton, DELAY_TIME);
                                startActivity(new Intent(LoginScreen.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginScreen.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(LoginScreen.this::resetButton, DELAY_TIME);
                            }
                        }
                    });
                }
            }
        });
    }

    private void resetButton() {
        btnAnimation.pauseAnimation();
        btnAnimation.setVisibility(View.GONE);
        btnText.setVisibility(View.VISIBLE);
    }

    private void btnPlayAnimation() {
        btnAnimation.setVisibility(View.VISIBLE);
        btnAnimation.playAnimation();
        btnText.setVisibility(View.GONE);
    }
}
