package com.test.templeteproject.base;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;


import com.test.templeteproject.R;


/**
 * Created by Vaqib on 6/19/2017.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Change
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    @Override
    public void startActivityForResult(@RequiresPermission Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.activity_anim_slide_in_right, R.anim.activity_anim_slide_out_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.activity_anim_slide_in_left, R.anim.activity_anim_slide_out_right);
    }


}
