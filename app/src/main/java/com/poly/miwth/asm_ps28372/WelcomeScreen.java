package com.poly.miwth.asm_ps28372;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.poly.miwth.asm_ps28372.Onboarding.ViewPageAdapter;

public class WelcomeScreen extends AppCompatActivity {

    ViewPager mSlideView;
    LinearLayout mDots;
    Button btnSkip, btnNext, btnBack;
    TextView[] dots;
    ViewPageAdapter viewPageAdapter;

    FirebaseAuth mAuth;
    ViewPager.OnPageChangeListener viewLister = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            if (position > 0) {
                btnBack.setVisibility(View.VISIBLE);
            } else {
                btnBack.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_screen);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        mAuth = FirebaseAuth.getInstance();

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Intent intent;
                if (currentUser != null) {
                    intent = new Intent(WelcomeScreen.this, MainActivity.class);
                } else {
                    intent = new Intent(WelcomeScreen.this, LoginScreen.class);
                }
                setOnboardingStatus();
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) > 0) {
                    mSlideView.setCurrentItem(getItem(-1), true);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 3) {
                    mSlideView.setCurrentItem(getItem(1), true);
                } else {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    Intent intent;
                    if (currentUser != null) {
                        intent = new Intent(WelcomeScreen.this, MainActivity.class);
                    } else {
                        intent = new Intent(WelcomeScreen.this, LoginScreen.class);
                    }
                    setOnboardingStatus();
                    startActivity(intent);
                    finish();
                }
            }
        });


        mSlideView = (ViewPager) findViewById(R.id.slideViewPager);
        mDots = (LinearLayout) findViewById(R.id.indicator_layout);
        viewPageAdapter = new ViewPageAdapter(this);
        mSlideView.setAdapter(viewPageAdapter);

        setUpIndicator(0);
        mSlideView.addOnPageChangeListener(viewLister);


    }

    public void setUpIndicator(int position) {
        dots = new TextView[4];
        mDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(HtmlCompat.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.Wood, getApplicationContext().getTheme()));
            mDots.addView(dots[i]);

        }
        dots[position].setTextColor(getResources().getColor(R.color.camdat, getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        return mSlideView.getCurrentItem() + i;
    }

    private void setOnboardingStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isWelcomeCompleted", true);
        editor.apply();
    }
}