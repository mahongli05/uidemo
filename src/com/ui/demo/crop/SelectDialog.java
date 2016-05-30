package com.ui.demo.crop;

/**
 * Created by MHL on 2016/5/30.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.com.ui.guide.R;


public class SelectDialog extends Dialog {

    private View mTakePhotoView;
    private View mChoosePhotoView;
    private View mCancelView;

    private PhotoAction mAction;

    public SelectDialog(Context context) {
        super(context, R.style.DialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initDialog();
    }

    public void setPhotoAction(PhotoAction action) {
        mAction = action;
    }

    private void initDialog() {

        mTakePhotoView = findViewById(R.id.take);
        mTakePhotoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAction != null) {
                    mAction.onTakePhoto();
                }
                SelectDialog.this.dismiss();
            }
        });

        mChoosePhotoView = findViewById(R.id.choose);
        mChoosePhotoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAction != null) {
                    mAction.onSelectPhoto();
                }
                SelectDialog.this.dismiss();
            }
        });

        mCancelView = findViewById(R.id.cancel);
        mCancelView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SelectDialog.this.dismiss();
            }
        });
    }

    public interface PhotoAction {
        void onTakePhoto();
        void onSelectPhoto();
    }
}

