package com.graspery.www.wordcountpro;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class AboutUsDialog extends Dialog {
    public Activity c;
    private View v;
    private LinearLayout mLinearLayout;

    public AboutUsDialog(Activity a, View v) {
        super(a);
        this.c = a;
        this.v = v;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_us_layout);

        mLinearLayout = findViewById(R.id.about_us_linearlayout);
        mLinearLayout.addView(v);
    }
}
