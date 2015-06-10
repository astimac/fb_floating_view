package com.astimac.fb.popup.example.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.astimac.fb.popup.example.R;
import com.astimac.fb.popup.example.eventbus.ServiceRuntimeChangedEvent;
import com.astimac.fb.popup.example.mocks.MockModel;
import com.astimac.fb.popup.example.utils.CircleTransformation;
import com.astimac.fb.popup.example.utils.Prefs;
import com.astimac.fb.popup.example.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import de.greenrobot.event.EventBus;

/**
 * Created by alex on 6/10/15.
 */
public class PopUpService extends Service implements View.OnTouchListener, View.OnClickListener {

    /**
     * Drag and drop variables
     */
    private int mInitialX;
    private int mInitialY;
    private float mInitialTouchX;
    private float mInitialTouchY;

    /**
     * Flag which indicates whether the user can click on pop up
     */
    private boolean mIsClick = false;

    /**
     * Device Window Manager
     */
    private WindowManager mWindowManager;

    /**
     * Layout parameters for floating popup view
     */
    private WindowManager.LayoutParams mParams;

    /**
     * Inflated floating pop up view
     */
    private RelativeLayout mPopupView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mPopupView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_popup, null);

        final ImageView mImageView = (ImageView) mPopupView.findViewById(R.id.popup_image);

        final TextView mTextView = (TextView) mPopupView.findViewById(R.id.popup_text);

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.TOP | Gravity.RIGHT | Gravity.END;

        final int x = Prefs.getX(this);
        final int y = Prefs.getY(this);

        if(x == 0 && y == 0) {
            mParams.x = Utils.dpToPixels(20, getResources());

            mParams.y = Utils.dpToPixels(50, getResources());
        } else {
            mParams.x = x;

            mParams.y = y;
        }

        mWindowManager.addView(mPopupView, mParams);

        final MockModel mModel = new MockModel();
        mModel.setImageUrl("http://thecatapi.com/api/images/get?format=src&type=png&size=small");
        mModel.setNumberOfMessages(10);

        final BitmapPool mBitmapPool = Glide.get(this).getBitmapPool();
        Glide.with(this).load(R.mipmap.c09).bitmapTransform(new CircleTransformation(mBitmapPool)).into(mImageView);

        mTextView.setText(String.valueOf(mModel.getNumberOfMessages()));

        mPopupView.setOnTouchListener(this);

        mPopupView.setOnClickListener(this);

        Prefs.setServiceRunning(this, true);

        EventBus.getDefault().post(new ServiceRuntimeChangedEvent(true));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Prefs.setServiceRunning(this, false);
        if (mPopupView != null) {
            mWindowManager.removeView(mPopupView);
        }

        EventBus.getDefault().post(new ServiceRuntimeChangedEvent(false));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsClick = true;
                mInitialX = mParams.x;
                mInitialY = mParams.y;
                mInitialTouchX = event.getRawX();
                mInitialTouchY = event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                mIsClick = false;
                mParams.x = mInitialX - (int) (event.getRawX() - mInitialTouchX);
                mParams.y = mInitialY + (int) (event.getRawY() - mInitialTouchY);
                mWindowManager.updateViewLayout(mPopupView, mParams);
                return true;
            case MotionEvent.ACTION_UP:
                if(mIsClick) {
                    mIsClick = false;
                    mPopupView.performClick();
                } else {
                    Prefs.setX(this, mParams.x);
                    Prefs.setY(this, mParams.y);
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_view:
                stopSelf();
                break;
            default:
                break;
        }
    }
}
