package com.debajyotibasak.udacitypracticalquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFabNext;
    private EditText mEdtUserName, mEdtEmail, mEdtAboutMe;
    private View mViewName, mViewEmail, mViewAbout;

    private void initView() {
        setContentView(R.layout.activity_main);
        mFabNext = findViewById(R.id.fab_next);
        mEdtUserName = findViewById(R.id.edt_user_name);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtAboutMe = findViewById(R.id.edt_about);
        mViewName = findViewById(R.id.view_name);
        mViewEmail = findViewById(R.id.view_email);
        mViewAbout = findViewById(R.id.view_about);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        if (savedInstanceState != null) {
            mEdtUserName.setText(SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.NAME, null));
            mEdtEmail.setText(SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.EMAIL, null));
            mEdtAboutMe.setText(SharedPreferencesHelper.getSharedPreferenceString(this, AppConstants.ABOUT, null));
        }

        mEdtUserName.setOnFocusChangeListener((view, b) -> {
            if (b) {
                mViewName.setVisibility(View.VISIBLE);
                mViewEmail.setVisibility(View.INVISIBLE);
                mViewAbout.setVisibility(View.INVISIBLE);
            }
        });

        mEdtEmail.setOnFocusChangeListener((view, b) -> {
            if (b) {
                mViewName.setVisibility(View.INVISIBLE);
                mViewEmail.setVisibility(View.VISIBLE);
                mViewAbout.setVisibility(View.INVISIBLE);
            }
        });

        mEdtAboutMe.setOnFocusChangeListener((view, b) -> {
            if (b) {
                mViewName.setVisibility(View.INVISIBLE);
                mViewEmail.setVisibility(View.INVISIBLE);
                mViewAbout.setVisibility(View.VISIBLE);
            }
        });

        mFabNext.setOnClickListener(view -> {
                insertSharedPreferences();
                clearEditText();
                startActivityWithTransitions(view);
        });
    }

    private void startActivityWithTransitions(View view) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, AppConstants.TRANSITIONS);

        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(AppConstants.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(AppConstants.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(AppConstants.NAME, mEdtUserName.getText().toString().trim());
        outState.putString(AppConstants.EMAIL, mEdtEmail.getText().toString().trim());
        outState.putString(AppConstants.ABOUT, mEdtAboutMe.getText().toString().trim());
    }

    private void insertSharedPreferences() {
        SharedPreferencesHelper.setSharedPreferenceString(this, AppConstants.NAME, mEdtUserName.getText().toString().trim());
        SharedPreferencesHelper.setSharedPreferenceString(this, AppConstants.EMAIL, mEdtEmail.getText().toString().trim());
        SharedPreferencesHelper.setSharedPreferenceString(this, AppConstants.ABOUT, mEdtAboutMe.getText().toString().trim());
    }

    private void clearEditText() {
        mEdtUserName.setText(AppConstants.EMPTY);
        mEdtEmail.setText(AppConstants.EMPTY);
        mEdtAboutMe.setText(AppConstants.EMPTY);
    }
}
