package com.ui.demo.util;

import android.os.Handler;
import android.os.Looper;

public class UiUtil {

    private static volatile Handler sHandler;

    private static void ensureHandler() {
        if (sHandler == null) {
            synchronized (UiUtil.class) {
                if (sHandler == null) {
                    sHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
    }

    public boolean isOnUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThreadDelay(runnable, 0);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        try {
            ensureHandler();
            sHandler.postDelayed(new NoExceptionRunnable(runnable), delayMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runOnUiThreadAtTime(Runnable runnable, long uptimeMillis) {
        try {
            ensureHandler();
            sHandler.postAtTime(new NoExceptionRunnable(runnable), uptimeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Handler getUIThreadHandler() {
        ensureHandler();
        return sHandler;
    }

    /*
     * catch all exception in NoExceptionRunnable run
     * */
    private static class NoExceptionRunnable implements Runnable {

        private Runnable mRunnable;

        public NoExceptionRunnable(Runnable runnable) {
            mRunnable = runnable;
        }

        @Override
        public void run() {
            try {
                mRunnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

