package com.graspery.www.wordcountpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button registerButton;
    private NormalTextView termsAndConditions;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        initialize();
    }

    private void initialize() {
        nameEdit = findViewById(R.id.full_name);
        emailEdit = findViewById(R.id.email_input);
        passwordEdit = findViewById(R.id.password_input);

        NormalTextView back = findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

        termsAndConditions = findViewById(R.id.terms_conditions);
        termsAndConditions.setText(Html.fromHtml("By tapping \"Create New Account\" you<br>agree to the <font color=\"blue\">terms and conditions</font></br>"));
        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://docs.google.com/document/d/1RLM-C2C_PBvFFiAxjEbj3piTqJrK9hXGQl7Sxtp1OLE/edit?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        progressBar = findViewById(R.id.register_load);
        progressBar.setVisibility(View.INVISIBLE);

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEdit.getText().toString().trim();
                final String password = passwordEdit.getText().toString();
                final String name = nameEdit.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                if (inputValidation(email, password, name)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegisterActivity.this, "Authentication successfull.",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("users").child(user.getUid());
                                        myRef.child("name").setValue(name);
                                        myRef.child("email").setValue(email);
                                        myRef.child("date").setValue(currentDateTimeString);

                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        finish();

                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean inputValidation(String email, String password, String name) {
        boolean isValid = true;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern)) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }

        if (password.contains(" ") || password.length() < 4) {
            isValid = false;
            Toast.makeText(getApplicationContext(), "Invalid password address. Must be longer than 4 characters with no space", Toast.LENGTH_LONG).show();
        }

        if (name.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Include your name or alias", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }
}
