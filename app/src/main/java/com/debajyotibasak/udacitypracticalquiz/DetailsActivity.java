package com.debajyotibasak.udacitypracticalquiz;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private LinearLayout mLayRoot;
    private TextView mTxvUserName, mTxvEmail, mTxvAbout;
    private FloatingActionButton mFabBack;
    private int revealX, revealY;

    private void initViews() {
        setContentView(R.layout.activity_details);
        mLayRoot = findViewById(R.id.lay_root);
        mTxvUserName = findViewById(R.id.txv_name);
        mTxvEmail = findViewById(R.id.txv_email);
        mTxvAbout = findViewById(R.id.txv_about);
        mFabBack = findViewById(R.id.fab_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && getIntent().hasExtra(AppConstants.EXTRA_CIRCULAR_REVEAL_X)
                && getIntent().hasExtra(AppConstants.EXTRA_CIRCULAR_REVEAL_Y)) {

            mLayRoot.setVisibility(View.INVISIBLE);
            revealX = getIntent().getIntExtra(AppConstants.EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = getIntent().getIntExtra(AppConstants.EXTRA_CIRCULAR_REVEAL_Y, 0);

            ViewTreeObserver viewTreeObserver = mLayRoot.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        mLayRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            mLayRoot.setVisibility(View.VISIBLE);
        }

        if (!SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.NAME, null).trim().isEmpty()) {
            mTxvUserName.setText(SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.NAME, null));
        } else {
            mTxvUserName.setText(R.string.txt_demo_user);
        }

        if (!SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.EMAIL, null).trim().isEmpty()) {
            mTxvEmail.setText(SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.EMAIL, null));
        } else {
            mTxvEmail.setText(R.string.txt_demo_email);
        }

        if (!SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.ABOUT, null).trim().isEmpty()) {
            mTxvAbout.setText(SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.ABOUT, null));
        } else {
            mTxvAbout.setText(R.string.txt_demo_description);
        }


        mFabBack.setOnClickListener(view -> onBackPressed());
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(mLayRoot.getWidth(), mLayRoot.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(mLayRoot, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            mLayRoot.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

}
