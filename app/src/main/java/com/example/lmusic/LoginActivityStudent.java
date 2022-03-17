package com.example.lmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityStudent extends AppCompatActivity {
    EditText email_login, password_login;
    TextView goToSignup, title;
    Button login;
    ImageView logo;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        fAuth = FirebaseAuth.getInstance();

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        title = findViewById(R.id.titlelogin);
        login = findViewById(R.id.login_button);
        goToSignup = findViewById(R.id.goToSignup);

        title.setTranslationX(800);
        title.setAlpha(0);
        title.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(200).start();

        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivityStudent.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String password = password_login.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    email_login.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_login.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    password_login.setError("Password must be >= 6 characters");
                }

                //auth the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivityStudent.this, "User logged", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{

                            Toast.makeText(LoginActivityStudent.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });

    }
    }
