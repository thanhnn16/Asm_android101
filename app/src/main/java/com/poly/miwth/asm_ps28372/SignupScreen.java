package com.poly.miwth.asm_ps28372;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

public class SignupScreen extends AppCompatActivity {
    final int DELAY_TIME = 1000;

    TextView txtLoginNow, btnText;
    LottieAnimationView btnAnimation;
    EditText edtUser, edtPass, edtRePass;
    RelativeLayout btnSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        mAuth = FirebaseAuth.getInstance();
        btnAnimation = findViewById(R.id.btnAnimation);
        btnText = findViewById(R.id.btnText);
        txtLoginNow = findViewById(R.id.txtLoginNow);
        btnSignup = findViewById(R.id.btnSignup);
        edtUser = findViewById(R.id.edtSignupEmail);
        edtPass = findViewById(R.id.edtSignupPw);
        edtRePass = findViewById(R.id.edtSingupRePw);

        txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlayAnimation();
                String email = edtUser.getText().toString();
                String password = edtPass.getText().toString();
                String rePw = edtRePass.getText().toString();
                boolean matKhauManh = isStrongPassword(password);
                boolean kiemTraEmail = isValidEmail(email);
                if (email.isEmpty() || password.isEmpty() || rePw.isEmpty()) {
                    Toast.makeText(SignupScreen.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(SignupScreen.this::resetButton, DELAY_TIME);
                } else if (!kiemTraEmail) {
                    Toast.makeText(SignupScreen.this, "Vui lòng nhập đúng định email", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(SignupScreen.this::resetButton, DELAY_TIME);
                } else if (!matKhauManh) {
                    new Handler().postDelayed(SignupScreen.this::resetButton, DELAY_TIME);
                } else if (!password.equals(rePw)) {
                    Toast.makeText(SignupScreen.this, "Nhập lại mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(SignupScreen.this::resetButton, DELAY_TIME);
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            new Handler().postDelayed(SignupScreen.this::resetButton, DELAY_TIME);
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignupScreen.this, LoginScreen.class));
                                finish();
                                Toast.makeText(SignupScreen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupScreen.this, "Đăng ký không thành công.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean isStrongPassword(String password) {
        // Kiểm tra độ dài mật khẩu
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu quá ngắn. Yêu cầu ít nhất 6 ký tự.", Toast.LENGTH_SHORT).show();
            return false; // Mật khẩu quá ngắn
        }

        // Kiểm tra sự hiện diện của ít nhất một chữ cái viết hoa
        if (!password.matches(".*[A-Z].*")) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất một chữ cái viết hoa.", Toast.LENGTH_SHORT).show();
            return false; // Mật khẩu không có chữ cái viết hoa
        }

        // Kiểm tra sự hiện diện của ít nhất một chữ cái viết thường
        if (!password.matches(".*[a-z].*")) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất một chữ cái viết thường.", Toast.LENGTH_SHORT).show();
            return false; // Mật khẩu không có chữ cái viết thường
        }

        // Kiểm tra sự hiện diện của ít nhất một chữ số
        if (!password.matches(".*\\d.*")) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất một chữ số.", Toast.LENGTH_SHORT).show();
            return false; // Mật khẩu không có chữ số
        }

        return true; // Mật khẩu đủ mạnh
    }

    public boolean isValidEmail(String email) {
        // Mẫu regex cho chuỗi email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Kiểm tra sự khớp của chuỗi email với mẫu regex
        return email.matches(regex);
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