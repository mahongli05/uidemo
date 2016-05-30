package com.ui.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.com.ui.guide.R;
import com.ui.demo.crop.PhotoCropActivity;
import com.ui.demo.guide.GuideActivity;
import com.ui.demo.infinitviewpager.SlidePagerViewActivity;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {

    	findViewById(R.id.guide).setOnClickListener(this);
        findViewById(R.id.unlimited_slide).setOnClickListener(this);
        findViewById(R.id.photo_select).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.guide:
                showGuideActivity();
                break;

            case R.id.unlimited_slide:
                showUnlimitedSlide();
                break;

            case R.id.photo_select:
                showPhotoSelect();
                break;
        }
    }

    private void showGuideActivity() {
        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
        startActivity(intent);
    }

    private void showUnlimitedSlide() {
        Intent intent = new Intent(MainActivity.this, SlidePagerViewActivity.class);
        startActivity(intent);
    }

    private void showPhotoSelect() {
        Intent intent = new Intent(MainActivity.this, PhotoCropActivity.class);
        startActivity(intent);
    }
}
