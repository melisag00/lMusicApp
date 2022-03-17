package com.example.lmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SelectRoleActivity extends AppCompatActivity {

    ImageView img;
    TextView text;
    private Button goToLoginStudent;
    private Button goToLoginTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectrole);


        img = findViewById(R.id.logo);
        text = findViewById(R.id.name);
        goToLoginStudent = findViewById(R.id.goToLoginStudentPage);
        goToLoginTeacher = findViewById(R.id.goToLoginTeacherPage);

   /*     img .setTranslationX(800);
        img .animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(200).start();

        text.setTranslationX(800);
        text.setAlpha(0);
        text.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(200).start();

       goToLoginStudent.setTranslationX(800);
       goToLoginStudent.setAlpha(0);
       goToLoginStudent.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(500).start();

        goToLoginTeacher.setTranslationX(800);
        goToLoginTeacher.setAlpha(0);
        goToLoginTeacher.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(500).start();
    */


         goToLoginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoleActivity.this, LoginActivityStudent.class);
                startActivity(intent);
            }
        });


        goToLoginTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoleActivity.this, LoginActivityTeacher.class);
                startActivity(intent);
            }
        });
    }
}