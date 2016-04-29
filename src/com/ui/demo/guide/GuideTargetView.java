package com.ui.demo.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.ui.guide.R;
import com.ui.demo.util.ViewUtil;

/**
 * Created by MHL on 2016/4/26.
 */
public class GuideTargetView extends View {

    private RectF mTargetRectInThis = new RectF();
    private Rect mTargetRectInParent;
    private int mBackgroundColor;
    private Paint mBackgroundBitmapPaint;
    private Paint mBackgroundPaint;
    private int mPaddingHorizontal;
    private int mPaddingVertical;

    public GuideTargetView(Context context) {
        this(context, null);
    }

    public GuideTargetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideTargetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mBackgroundBitmapPaint = new Paint();
        mBackgroundBitmapPaint.setAntiAlias(true);
        mBackgroundBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mBackgroundBitmapPaint.setColor(getResources().getColor(R.color.black));
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundColor = getResources().getColor(R.color.guide_bg_color);
        mPaddingHorizontal = getResources().getDimensionPixelOffset(R.dimen.guide_target_padding_horizontal);
        mPaddingVertical = getResources().getDimensionPixelOffset(R.dimen.guide_target_padding_vertical);
    }

    public void updateTargetViewRelativeRect(View rootParent, Rect targetRectInRoot) {

        ViewGroup parent = (ViewGroup)getParent();
        if (parent == rootParent) {
            mTargetRectInParent = targetRectInRoot;
        } else {
            Rect parentRectInRoot = ViewUtil.getRelativeRect(rootParent, parent);
            mTargetRectInParent = new Rect(targetRectInRoot.left - parentRectInRoot.left,
                    targetRectInRoot.top - parentRectInRoot.top,
                    targetRectInRoot.right - parentRectInRoot.left,
                    targetRectInRoot.bottom - parentRectInRoot.top);
        }

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)getLayoutParams();
        params.topMargin = mTargetRectInParent.top + mTargetRectInParent.height() / 2 - params.height / 2;
        parent.updateViewLayout(this, params);

        mBackgroundBitmapPaint.setStrokeWidth(params.height / 2);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initTargetRectInThis();
    }

    private void initTargetRectInThis() {
        if (mTargetRectInParent != null) {
            mTargetRectInThis.set(mTargetRectInParent.left - getLeft() - mPaddingHorizontal,
                    getHeight() / 2 - mTargetRectInParent.height() / 2 - mPaddingVertical,
                    mTargetRectInParent.right - getLeft() + mPaddingHorizontal,
                    getHeight() / 2 + mTargetRectInParent.height() / 2 + mPaddingVertical);
            drawBackgroundBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	if (mBackgroundBitmap != null) {
    		canvas.drawBitmap(mBackgroundBitmap, 0, 0, mBackgroundPaint);
    	}
    }

    private Bitmap mBackgroundBitmap;

    private void drawBackgroundBitmap() {
        if (mBackgroundBitmap != null) {
            mBackgroundBitmap.recycle();
        }
        mBackgroundBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBackgroundBitmap);
        canvas.drawColor(mBackgroundColor);
        canvas.drawRoundRect(mTargetRectInThis, getHeight() / 2, getHeight() / 2, mBackgroundBitmapPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (mTargetRectInThis.contains(x, y)) {
            if (mTargetClickListener != null) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mTargetClickListener.onTargetClick();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private OnTargetClickListener mTargetClickListener;

    public void setOnTargetClickListener(OnTargetClickListener listener) {
        mTargetClickListener = listener;
    }

    public interface OnTargetClickListener {
        void onTargetClick();
    }
}

