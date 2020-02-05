package com.graspery.www.wordcountpro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ImageEditorDialog extends Dialog {
    public Activity c;
    private Bitmap mBitmap;
    ImageView mImageView;
    Button scan;
    ImageView left;
    ImageView right;

    public ImageEditorDialog(Activity a, Bitmap bitmap) {
        super(a);
        this.c = a;
        this.mBitmap = bitmap;

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError e) {
            return source;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_editor_layout);

        mImageView = findViewById(R.id.image_editor_view);
        mImageView.setImageBitmap(mBitmap);

        left = findViewById(R.id.rotate_left);
        right = findViewById(R.id.rotate_right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBitmap = rotateImage(mBitmap, -90);
                mImageView.setImageBitmap(mBitmap);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBitmap = rotateImage(mBitmap, 90);
                mImageView.setImageBitmap(mBitmap);
            }
        });

        scan = findViewById(R.id.upload_and_scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "text_analyzer_image";//no .png or .jpg needed
                try {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    FileOutputStream fo = c.openFileOutput(fileName, Context.MODE_PRIVATE);
                    fo.write(bytes.toByteArray());
                    // remember close file output
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fileName = null;
                }
                dismiss();
            }
        });
    }
}