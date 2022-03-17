package com.example.lmusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class RegisterActivityStudent extends AppCompatActivity implements MultipleCDialogFragment.onMultiChoiceListener{

    TextView goToLogin, title, instruments;
    Button selectChoices, signUp;
    //ListView viewselectedinstr;
    ImageView profile;
    EditText name_register, email_register, password_register, confirmpassword_register, address_register, phone_register, birth_register, instruments_register;
    CheckBox isStudent, isTeacher;
    boolean valid = true;
    FirebaseAuth fAuth;
    Uri imageUri;


    StorageReference reference = FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        title = findViewById(R.id.titleSingup);
        goToLogin = findViewById(R.id.goToLogin);
        instruments = findViewById(R.id.register_instruments);
        selectChoices = findViewById(R.id.choice);

        //register variabile
        profile = findViewById(R.id.profile_image);
        name_register = findViewById(R.id.register_name);
        email_register = findViewById(R.id.register_email);
        password_register = findViewById(R.id.register_passsword);
        confirmpassword_register= findViewById(R.id.register_confirmpassword);
        address_register = findViewById(R.id.register_address);
        phone_register = findViewById(R.id.register_phonenumber);
        birth_register = findViewById(R.id.register_address);
        instruments_register = findViewById(R.id.register_instruments);

        signUp = findViewById(R.id.singUp);
        fAuth = FirebaseAuth.getInstance();

        //  if(fAuth.getCurrentUser() != null){
        //    startActivity(new Intent(getApplicationContext(),SelectRoleActivity.class));
        //      finish();
        //  }


        title.setTranslationX(800);
        title.setAlpha(0);
        title.animate().translationX(0).alpha(1).setDuration(2000).setStartDelay(200).start();

        selectChoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new MultipleCDialogFragment();
                dialogFragment.setCancelable(false);
                dialogFragment.show(getSupportFragmentManager(),"Dialog");
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivityTeacher.class));
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,2);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = name_register.getText().toString().trim();
                String email = email_register.getText().toString().trim();
                String password = password_register.getText().toString().trim();
                String confirmpass = confirmpassword_register.getText().toString().trim();
                String address = address_register.getText().toString().trim();
                String phone = phone_register.getText().toString().trim();
                String birth = birth_register.getText().toString().trim();
                String instruments = instruments_register.getText().toString().trim();


                if(TextUtils.isEmpty(fullname)){
                    name_register.setError("Full name is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Full Name is Required", Toast.LENGTH_SHORT).show();
                    name_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    email_register.setError("Email is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Email is Required", Toast.LENGTH_SHORT).show();
                    email_register.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_register.setError("Password is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Password is Required", Toast.LENGTH_SHORT).show();password_register.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    password_register.setError("Password must be >= 6 characters");
                    Toast.makeText(RegisterActivityStudent.this, "Password must be >= 6 characters", Toast.LENGTH_SHORT).show();
                    password_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(confirmpass)){
                    confirmpassword_register.setError("Confirm Password is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Confirm Password is Required", Toast.LENGTH_SHORT).show();
                    confirmpassword_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(address)){
                    address_register.setError("Address is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Address is Required", Toast.LENGTH_SHORT).show();
                    address_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    phone_register.setError("Phone Number is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Phone Number is Required", Toast.LENGTH_SHORT).show();
                    phone_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(birth)){
                    birth_register.setError("Date of birth is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Date of birth is Required", Toast.LENGTH_SHORT).show();
                    birth_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(instruments)){
                    instruments_register.setError("Instruments is Required");
                    Toast.makeText(RegisterActivityStudent.this, "Instruments is Required", Toast.LENGTH_SHORT).show();
                    instruments_register.requestFocus();
                    return;
                }

                //register the user

                fAuth.createUserWithEmailAndPassword(email_register.getText().toString().trim(),password_register.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if(imageUri != null)
                            {
                                uploadToFirebase(imageUri);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivityStudent.this, "Please select Image", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(RegisterActivityStudent.this, "User failed auth", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void uploadToFirebase(Uri uri){

        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+getFilExtintion(imageUri));
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        User user = new User(name_register.getText().toString().trim(), email_register.getText().toString().trim(), password_register.getText().toString().trim(), address_register.getText().toString().trim(), phone_register.getText().toString().trim(), birth_register.getText().toString().trim(), instruments_register.getText().toString().trim());
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivityStudent.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivityStudent.this, "User failed registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private String getFilExtintion(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri)) ;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            profile.setImageURI(imageUri);
        }
    }



    @Override
    public void positiveChecked(String[] list, ArrayList<String> selectedItelList) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String str: selectedItelList){
            stringBuilder.append(str+" ");
        }
        instruments.setText(stringBuilder);
    }

    @Override
    public void negativeChecked() {
        instruments.setText(" ");
    }
}