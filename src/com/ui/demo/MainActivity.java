package com.ui.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.com.ui.guide.R;
import com.ui.demo.guide.GuideActivity;
import com.ui.demo.infinitviewpager.SlidePagerViewActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {

    	findViewById(R.id.guide).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GuideActivity.class);
				startActivity(intent);
			}
		});

        findViewById(R.id.infinit_slide).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SlidePagerViewActivity.class);
				startActivity(intent);
			}
		});
    }
}
