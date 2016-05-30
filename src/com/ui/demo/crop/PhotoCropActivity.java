package com.ui.demo.crop;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.com.ui.guide.R;
import com.ui.demo.util.FileUtil;
import com.ui.demo.util.UiUtil;

import java.io.File;

/**
 * Created by MHL on 2016/5/30.
 */
public class PhotoCropActivity extends Activity {

    private final static int SELECT_IMAGE = 100;
    private final static int CAMERA_CAPTURE = 200;
    private final static int CROP_IMAGE = 300;

    private View mButton;
    private ImageView mImageView;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        setupView();
    }

    private void setupView() {
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });

        mImageView = (ImageView) findViewById(R.id.preview);
    }

    private void showSelectDialog() {
        SelectDialog dialog = new SelectDialog(this);
        dialog.setPhotoAction(new SelectDialog.PhotoAction() {

            @Override
            public void onTakePhoto() {
                takePhoto();
            }

            @Override
            public void onSelectPhoto() {
                selectPicture();
            }
        });
        dialog.show();
    }

    private void takePhoto() {
        mImageUri = null;
        // use standard intent to capture an image
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // test intent for save
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            // create a file to save picture
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
            // we will handle the returned data in onActivityResult
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        }
    }

    private Uri generateFileUri() {
        // set package-specific directories to save picture
        File dirFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File tempFile = new File(dirFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
        FileUtil.ensureFile(tempFile);
        mImageUri = Uri.fromFile(tempFile);
        return mImageUri;
    }

    private void selectPicture() {
        try {
            mImageUri = null;
            // Pick Image From Gallery
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_IMAGE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE
                && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                performCrop(data.getData());
            }
        } else if (requestCode == CAMERA_CAPTURE
                && resultCode == Activity.RESULT_OK) {
            if (mImageUri != null) {
                performCrop(mImageUri);
            }
        } else if (requestCode == CROP_IMAGE
                && resultCode == Activity.RESULT_OK) {
            if (mImageUri != null) {
                mImageView.setImageURI(mImageUri);
            }
        }
    }

    private void performCrop(Uri contentUri) {
        try {
            // Start Crop Activity
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);

            // retrieve data on return
//            cropIntent.putExtra("return-data", true);
            // return to our file
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_IMAGE);
        } catch (ActivityNotFoundException e) {
            // respond to users whose devices do not support the crop action
            e.printStackTrace();
        }
    }
}
