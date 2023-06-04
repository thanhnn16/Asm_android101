package com.poly.miwth.asm_ps28372;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean isRemember = sharedPreferences.getBoolean("remember", false);
        boolean isWelcomeCompleted = sharedPreferences.getBoolean("isWelcomeCompleted", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isWelcomeCompleted) {
                    startActivity(new Intent(SplashScreenActivity.this, WelcomeScreen.class));
                    finish();
                    return;
                }

                if (isRemember && currentUser != null) {
                    String email = currentUser.getEmail();
                    Toast.makeText(SplashScreenActivity.this, "Chào mừng " + email + " quay trở lại", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                } else {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(SplashScreenActivity.this, LoginScreen.class));
                }

                finish();
            }
        }, 1200);
    }
}
