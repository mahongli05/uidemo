package com.ui.demo.infinitviewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MHL on 2016/5/25.
 */
public class UnlimitSlidePager extends FrameLayout {

    private ViewPager mViewPager;
    private ImageView mMirrorView;
    private Bitmap mMirrorBitmap;
    private UnlimitSlideAdapter mUnlimitAdapter = new UnlimitSlideAdapter();
    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners = new ArrayList<>();

    public UnlimitSlidePager(Context context) {
        this(context, null);
    }

    public UnlimitSlidePager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnlimitSlidePager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView();
    }

    private void setupView() {

        mMirrorView = new ImageView(getContext());
        mViewPager = new ViewPager(getContext());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i == mUnlimitAdapter.getCount() - 2 && v > 0.99f) {
                    mViewPager.setCurrentItem(0, false);
                } else if (i == mUnlimitAdapter.getCount() - 1) {
                    mViewPager.setCurrentItem(0, false);
                } else {
                    for (ViewPager.OnPageChangeListener listener : mOnPageChangeListeners) {
                        listener.onPageScrolled(i, v, i1);
                    }
                }
            }

            @Override
            public void onPageSelected(int i) {

                int index = i;
                if (i == mUnlimitAdapter.getCount() - 1) {
                    index = 0;
                }

                for (ViewPager.OnPageChangeListener listener : mOnPageChangeListeners) {
                    listener.onPageSelected(index);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                for (ViewPager.OnPageChangeListener listener : mOnPageChangeListeners) {
                    listener.onPageScrollStateChanged(i);
                }
            }
        });

        addView(mViewPager);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (item == 0) {
            mViewPager.setCurrentItem(mUnlimitAdapter.getCount() - 1, smoothScroll);
        } else {
            mViewPager.setCurrentItem(item, smoothScroll);
        }
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListeners.add(listener);
    }

    public void setPagerAdapter(PagerAdapter adapter) {
        mUnlimitAdapter.setSrcAdapter(adapter);
        mViewPager.setAdapter(mUnlimitAdapter);
    }

    private class UnlimitSlideAdapter extends PagerAdapter {

        private PagerAdapter mSrcAdapter;
        private View mFirstView;

        public void setSrcAdapter(PagerAdapter adapter) {
            if (mSrcAdapter != null) {
                mSrcAdapter.unregisterDataSetObserver(mDataSetObserver);
            }
            mSrcAdapter = adapter;
            mSrcAdapter.registerDataSetObserver(mDataSetObserver);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mSrcAdapter == null ? 0 : mSrcAdapter.getCount() + 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mSrcAdapter != null && position < mSrcAdapter.getCount()) {
                mSrcAdapter.destroyItem(container, position, object);
            } else {
                container.removeView(mMirrorView);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mSrcAdapter != null && position < mSrcAdapter.getCount()) {
                Object obj = mSrcAdapter.instantiateItem(container, position);
                if (position == 0 && obj instanceof View) {
                    mFirstView = (View) obj;
                }
                return obj;
            } else if (mFirstView != null) {
                boolean enable = mFirstView.isDrawingCacheEnabled();
                mFirstView.setDrawingCacheEnabled(true);
                Bitmap bitmap = mFirstView.getDrawingCache();
                if (bitmap != null) {
                    Bitmap oldBitmap = mMirrorBitmap;
                    mMirrorBitmap = Bitmap.createBitmap(bitmap);
                    mMirrorView.setImageBitmap(mMirrorBitmap);
                    if (oldBitmap != null) {
                        oldBitmap.recycle();
                    }
                    ViewParent parent = mMirrorView.getParent();
                    if (parent instanceof ViewGroup) {
                        ((ViewGroup) parent).removeView(mMirrorView);
                    }
                    container.addView(mMirrorView);
                    mFirstView.setDrawingCacheEnabled(enable);
                }
                return mMirrorView;
            }
            return null;
        }

        private DataSetObserver mDataSetObserver = new DataSetObserver() {

            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
                notifyDataSetChanged();
            }
        };
    }
}
