package com.example.introslider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.introslider.Model.MyPageAdapter;

public class WelcomeActivity extends AppCompatActivity {

private ViewPager viewPager;
private LinearLayout dotLayout;
private TextView[] dotsTv;
private int[]  layouts = new int[]{R.layout.slider_1, R.layout.slider_2, R.layout.slider_3, R.layout.slider_4, R.layout.slider_5};

private Button btnNext, btnSkip;

private MyPageAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isAppStartFirstTime()){
            startMainActivity();
            finish();
        }
        setStatusBarTransparent();

        setContentView(R.layout.activity_welcome);

        initView();

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem()+1;
                if(currentPage<layouts.length){
                    //////////Move to next Page//////////
                    viewPager.setCurrentItem(currentPage);
                }
                else{
                    startMainActivity();
                }
            }
        });

        pageAdapter = new MyPageAdapter(layouts,getApplicationContext());
        viewPager.setAdapter(pageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == layouts.length-1){
                    btnNext.setText("START");
                    btnSkip.setVisibility(View.GONE);
                }
                else{
                    btnNext.setText("NEXT");
                    btnSkip.setVisibility(View.VISIBLE);
                }
                setDotsStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setDotsStatus(0);
    }

    private boolean isAppStartFirstTime(){
        SharedPreferences spr = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        return spr.getBoolean("FirstTimeStartFlag", true);
    }

    private void setFirstTimeStartStatus(boolean fss){
        SharedPreferences spr = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spr.edit();
        editor.putBoolean("FirstTimeStartFlag",fss);
        editor.commit();
    }

    private void initView() {

        viewPager = findViewById(R.id.view_pager);
        dotLayout = findViewById(R.id.dotLayout);
        btnSkip = findViewById(R.id.skipBT);
        btnNext = findViewById(R.id.nextBT);
    }

    private void setStatusBarTransparent() {

        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void startMainActivity(){
        setFirstTimeStartStatus(false);
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setDotsStatus(int page){

        dotLayout.removeAllViews();
        dotsTv = new TextView[layouts.length];
        for(int i = 0; i<dotsTv.length; i++){
            dotsTv[i] = new TextView(this);
            dotsTv[i].setText(Html.fromHtml("&#8226;"));
            dotsTv[i].setTextSize(30);
            dotLayout.addView(dotsTv[i]);
        }

        if(dotsTv.length>0){
            dotsTv[page].setTextColor(Color.parseColor("#000000"));
        }
    }
}
