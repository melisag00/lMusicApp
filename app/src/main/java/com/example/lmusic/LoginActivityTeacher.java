package com.example.lmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityTeacher extends AppCompatActivity {

    EditText email_login, password_login;
    TextView goToSignup, title, forgotTextLink;
    Button login;
    ImageView logo;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        title = findViewById(R.id.titlelogin);
        login = findViewById(R.id.login_button);
        goToSignup = findViewById(R.id.goToSignup);
        forgotTextLink = findViewById(R.id.forgot_password);

        title.setTranslationX(800);
        title.setAlpha(0);
        title.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(200).start();

        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivityTeacher.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordReset = new AlertDialog.Builder(v.getContext());
                passwordReset.setTitle("Reset Passsword?");
                passwordReset.setMessage("Enter your email to received reset link.");
                passwordReset.setView(resetMail);
                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivityTeacher.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivityTeacher.this, "Error! Reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });
                passwordReset.create().show();
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
                            Toast.makeText(LoginActivityTeacher.this, "User logged", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{

                            Toast.makeText(LoginActivityTeacher.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });

    }


}