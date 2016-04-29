package com.ui.demo.guide;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.com.ui.guide.R;
import com.ui.demo.guide.GuideTargetView.OnTargetClickListener;
import com.ui.demo.util.UiUtil;
import com.ui.demo.util.ViewUtil;

public class GuideActivity extends Activity implements OnClickListener {

	private Button mTargetView;
	private GuideTargetView mGuideTargetView;
	private View mGuideContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		setupView();
	}

	private void setupView() {
		mTargetView = (Button)findViewById(R.id.button);
		mTargetView.setOnClickListener(this);

		mGuideContainer = findViewById(R.id.guide_contaier);
		mGuideContainer.setVisibility(View.INVISIBLE);

		mGuideTargetView = (GuideTargetView)findViewById(R.id.guide_target);
		mGuideTargetView.setOnTargetClickListener(new OnTargetClickListener() {

			@Override
			public void onTargetClick() {
				mGuideContainer.setVisibility(View.GONE);
				mTargetView.setText(getString(R.string.show_guide));
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == mTargetView) {
			mTargetView.setText(getString(R.string.hide_guide));
			UiUtil.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					View view = findViewById(R.id.content_root);
					Rect rect = ViewUtil.getRelativeRect(view, mTargetView);
					mGuideTargetView.updateTargetViewRelativeRect(view, rect);
					mGuideContainer.setVisibility(View.VISIBLE);
				}
			});
		}

	}

}
