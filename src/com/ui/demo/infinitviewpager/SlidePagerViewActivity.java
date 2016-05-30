package com.ui.demo.infinitviewpager;


import com.example.com.ui.guide.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SlidePagerViewActivity extends Activity {

	private UnlimitSlidePager mSlidePager;
	private PagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSlidePager = new UnlimitSlidePager(this);
		setContentView(mSlidePager);
		mAdapter = new PagerAdapter() {

			ImageView[] mImageViews = new ImageView[4];
			int[] mImageResourceId = {R.drawable.image_1,
					R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mImageViews.length;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView imageView = mImageViews[position];
				if (imageView == null) {
					imageView = new ImageView(SlidePagerViewActivity.this);
					imageView.setImageResource(mImageResourceId[position]);
					imageView.setScaleType(ScaleType.FIT_XY);
				}
				container.addView(imageView);
				return imageView;
			}

	        @Override
	        public void destroyItem(ViewGroup container, int position, Object object) {
				ImageView imageView = mImageViews[position];
				if (imageView != null) {
					container.removeView(imageView);
				}
	        }

		};
		mSlidePager.setPagerAdapter(mAdapter);
	}

}
