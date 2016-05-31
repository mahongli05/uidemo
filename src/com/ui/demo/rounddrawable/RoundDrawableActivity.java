package com.ui.demo.rounddrawable;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.com.ui.guide.R;

/**
 * Created by MHL on 2016/5/31.
 */
public class RoundDrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_drawable);
        setupView();
    }

    private void setupView() {

        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_1);

        ((ImageView) findViewById(R.id.image_1)).setImageDrawable(new RoundBitmapDrawable(resources, bitmap));
        ((ImageView) findViewById(R.id.image_2)).setImageDrawable(new RoundBitmapDrawableWithStroke(resources, bitmap));
        ((ImageView) findViewById(R.id.image_3)).setImageDrawable(new BitmapDrawable(resources, bitmap));
    }
}
