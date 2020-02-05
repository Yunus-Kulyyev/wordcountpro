package com.graspery.www.wordcountpro.GoogleApi;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graspery.www.wordcountpro.GoogleApi.Models.TokenInfo;
import com.graspery.www.wordcountpro.NormalTextView;
import com.graspery.www.wordcountpro.R;

public class SyntaxDialog extends Dialog {
    public Activity c;
    private TokenInfo token;
    private NormalTextView word;
    private NormalTextView part;
    private NormalTextView label;
    private NormalTextView aspect;
    private NormalTextView caseT;
    private NormalTextView form;
    private NormalTextView gender;
    private NormalTextView mood;
    private NormalTextView number;
    private NormalTextView person;
    private NormalTextView proper;
    private NormalTextView recip;
    private NormalTextView tense;
    private NormalTextView voice;

    public SyntaxDialog(Activity a, TokenInfo token) {
        super(a);
        this.c = a;
        this.token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.syntax_layout);

        word = findViewById(R.id.syn_word);
        if(!token.text.contains("UNKNOWN")) {
            word.setText("Word: " + token.text);
        } else {
            word.setVisibility(View.GONE);
        }

        part = findViewById(R.id.syn_part_speech);
        if(!token.partOfSpeech.contains("UNKNOWN")) {
            part.setText("Part of Speech: " + token.partOfSpeech);
        } else {
            part.setVisibility(View.GONE);
        }

        label = findViewById(R.id.syn_label);
        if(!token.label.contains("UNKNOWN")) {
            label.setText("Label: " + token.label);
        } else {
            label.setVisibility(View.GONE);
        }

        aspect = findViewById(R.id.syn_aspect);
        if(!token.aspect.contains("UNKNOWN")) {
            aspect.setText("Aspect: " + token.aspect);
        } else {
            aspect.setVisibility(View.GONE);
        }


        caseT = findViewById(R.id.syn_case);
        if(!token.textCase.contains("UNKNOWN")) {
            caseT.setText("Case: " + token.textCase);
        } else {
            caseT.setVisibility(View.GONE);
        }

        form = findViewById(R.id.syn_form);
        if(!token.form.contains("UNKNOWN")) {
            form.setText("Form: " + token.form);
        } else {
            form.setVisibility(View.GONE);
        }

        gender = findViewById(R.id.syn_gender);
        if(!token.gender.contains("UNKNOWN")) {
            gender.setText("Gender: " + token.gender);
        } else {
            gender.setVisibility(View.GONE);
        }

        mood = findViewById(R.id.syn_mood);
        if(!token.mood.contains("UNKNOWN")) {
            mood.setText("Mood: " + token.mood);
        } else {
            mood.setVisibility(View.GONE);
        }

        number = findViewById(R.id.syn_number);
        if(!token.number.contains("UNKNOWN")) {
            number.setText("Number: " + token.number);
        } else {
            number.setVisibility(View.GONE);
        }

        person = findViewById(R.id.syn_person);
        if(!token.person.contains("UNKNOWN")) {
            person.setText("Person: " + token.person);
        } else {
            person.setVisibility(View.GONE);
        }

        proper = findViewById(R.id.syn_proper);
        if(!token.proper.contains("UNKNOWN")) {
            proper.setText("Proper: " + token.proper);
        } else {
            proper.setVisibility(View.GONE);
        }

        tense = findViewById(R.id.syn_tense);
        if(!token.tense.contains("UNKNOWN")) {
            tense.setText("Tense: " + token.tense);
        } else {
            tense.setVisibility(View.GONE);
        }

        voice = findViewById(R.id.syn_voice);
        if(!token.voice.contains("UNKNOWN")) {
            voice.setText("Voice: " + token.voice);
        } else {
            voice.setVisibility(View.GONE);
        }

        recip = findViewById(R.id.syn_reciprocity);
        if(!token.reciprocity.contains("UNKNOWN")) {
            recip.setText("reciprocity: " + token.reciprocity);
        } else {
            recip.setVisibility(View.GONE);
        }
    }
}