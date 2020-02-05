package com.graspery.www.wordcountpro;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class FileUploadDialog extends Dialog {
    public Activity c;

    public FileUploadDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.file_upload_dialog_layout);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }
}