package com.ui.demo.util;

import android.graphics.Rect;
import android.view.View;

public class ViewUtil {

    public static Rect getRelativeRect(View srcView, View dstView) {
        Rect srcRect = new Rect();
        Rect dstRect = new Rect();
        if (srcView != null && dstView != null) {
            srcView.getGlobalVisibleRect(srcRect);
            dstView.getGlobalVisibleRect(dstRect);
            return new Rect(dstRect.left - srcRect.left,
                    dstRect.top - srcRect.top,
                    dstRect.right - srcRect.left,
                    dstRect.bottom - srcRect.top);
        }
        return null;
    }
}
