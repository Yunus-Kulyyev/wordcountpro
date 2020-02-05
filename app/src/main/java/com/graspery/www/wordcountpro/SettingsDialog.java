package com.graspery.www.wordcountpro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SettingsDialog extends Dialog {
    public Activity c;
    private SharedPreferences mSharedPreferences;
    private Spinner mSpinner;
    private int minimumWordLength;
    private TextView progressTextView;
    private SeekBar mSeekBar;
    private Switch mSwitch;
    private Switch mSuperSwitch;
    private Switch mLightSwitch;
    private static final String[] languages = {"English", "German", "Spanish", "Polish",
            "Russian", "Italian", "Brazilian", "Turkish", "French" ,"Argentinian", "Slovakian", "Norwegian", "Japanese", "Ukranian"};

    public SettingsDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_layout);

        mSharedPreferences = c.getSharedPreferences("com.graspery.www.wordcountpro", MODE_PRIVATE);

        ImageView back = findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mSwitch = findViewById(R.id.drawer_switch);
        mSuperSwitch = findViewById(R.id.super_drawer_switch);
        mLightSwitch = findViewById(R.id.light_drawer_switch);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mSharedPreferences.edit().putString("theme", "dark").commit();
                    mSuperSwitch.setChecked(false);
                    mLightSwitch.setChecked(false);
                } else {
                    mSharedPreferences.edit().putString("theme", "light").commit();
                }
            }
        });

        mSuperSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mSharedPreferences.edit().putString("theme", "super_dark").commit();
                    mSwitch.setChecked(false);
                    mLightSwitch.setChecked(false);
                } else {
                    mSharedPreferences.edit().putString("theme", "light").commit();
                }
            }
        });

        mLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mSharedPreferences.edit().putString("theme", "light").commit();
                    mSwitch.setChecked(false);
                    mSuperSwitch.setChecked(false);
                } else {
                    mSharedPreferences.edit().putString("theme", "light").commit();
                }
            }
        });


        mSpinner = findViewById(R.id.language_spinner);
        List list = Arrays.asList(languages);

        LanguagesAdapter customAdapter = new LanguagesAdapter(c, R.layout.language_row, list);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_spinner_item,languages);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(customAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSharedPreferences.edit().putString("language", "en").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 1:
                        mSharedPreferences.edit().putString("language", "de").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 2:
                        mSharedPreferences.edit().putString("language", "es").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 3:
                        mSharedPreferences.edit().putString("language", "pl").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 4:
                        mSharedPreferences.edit().putString("language", "ru").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 5:
                        mSharedPreferences.edit().putString("language", "it").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 6:
                        mSharedPreferences.edit().putString("language", "pt-rBR").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 7:
                        mSharedPreferences.edit().putString("language", "tr").commit();
                        /*c.finish();
                        c.startActivity(c.getIntent());*/
                        break;
                    case 8:
                        mSharedPreferences.edit().putString("language", "fr").commit();
                        break;
                    case 9:
                        mSharedPreferences.edit().putString("language", "es-rAR").commit();
                        break;
                    case 10:
                        mSharedPreferences.edit().putString("language", "sk").commit();
                        break;
                    case 11:
                        mSharedPreferences.edit().putString("language", "no").commit();
                        break;
                    case 12:
                        mSharedPreferences.edit().putString("language", "ja").commit();
                        break;
                    case 13:
                        mSharedPreferences.edit().putString("language", "uk").commit();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Typeface typeface = Typeface.createFromAsset(c.getAssets(), "fonts/helvetica.otf");
        minimumWordLength = 1;
        progressTextView = findViewById(R.id.progress_word_length);
        progressTextView.setTypeface(typeface);
        progressTextView.setText(mSharedPreferences.getInt("minimum",1) + "");
        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setProgress(mSharedPreferences.getInt("minimum", 0));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minimumWordLength = progress + 1;
                progressTextView.setText((progress+1)+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSharedPreferences.edit().putInt("minimum", seekBar.getProgress()+1).commit();
            }
        });
    }
}