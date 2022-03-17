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
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivityTeacher extends AppCompatActivity implements MultipleCDialogFragment.onMultiChoiceListener{


    TextView goToLogin, title, instruments;
    Button selectChoices, signUp;
    //ListView viewselectedinstr;
    ImageView profile;
    EditText name_register, email_register, password_register, confirmpassword_register, address_register, phone_register, birth_register, instruments_register, cv_register, bio_register;
    CheckBox isStudent, isTeacher;
    boolean valid = true;
    Uri imageUri;
    Uri fileUri;

    FirebaseAuth fAuth;
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        cv_register = findViewById(R.id.cv);
        bio_register = findViewById(R.id.bio);

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


        cv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);

            }
        });

       /* cv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);

            }
        });

*/

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
                String cv = cv_register.getText().toString().trim();
                String bio = bio_register.getText().toString().trim();

                if(imageUri == null){
                    Toast.makeText(RegisterActivityTeacher.this, "Please select Image", Toast.LENGTH_SHORT).show();
                    profile.requestFocus();
                    return;
                }

               if(TextUtils.isEmpty(fullname)){
                    name_register.setError("Full name is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Full Name is Required", Toast.LENGTH_SHORT).show();
                    name_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    email_register.setError("Email is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Email is Required", Toast.LENGTH_SHORT).show();
                    email_register.requestFocus();
                    return;
                }

                if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    email_register.setError("Invalid email address");
                    Toast.makeText(RegisterActivityTeacher.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    email_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    password_register.setError("Password is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Password is Required", Toast.LENGTH_SHORT).show();password_register.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    password_register.setError("Password must be >= 6 characters");
                    Toast.makeText(RegisterActivityTeacher.this, "Password must be >= 6 characters", Toast.LENGTH_SHORT).show();
                    password_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(confirmpass)){
                    confirmpassword_register.setError("Confirm Password is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Confirm Password is Required", Toast.LENGTH_SHORT).show();
                    confirmpassword_register.requestFocus();
                    return;
                }

                if(!password.equals(confirmpass)){
                    confirmpassword_register.setError("Passwords are not matching");
                    Toast.makeText(RegisterActivityTeacher.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                    confirmpassword_register.requestFocus();
                    return;

                }

                if(TextUtils.isEmpty(address)){
                    address_register.setError("Address is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Address is Required", Toast.LENGTH_SHORT).show();
                    address_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    phone_register.setError("Phone Number is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Phone Number is Required", Toast.LENGTH_SHORT).show();
                    phone_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(birth)){
                    birth_register.setError("Date of birth is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Date of birth is Required", Toast.LENGTH_SHORT).show();
                    birth_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(instruments)){
                    instruments_register.setError("Instruments is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Instruments is Required", Toast.LENGTH_SHORT).show();
                    instruments_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(cv.toString())){
                    cv_register.setError("CV is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "CV is Required", Toast.LENGTH_SHORT).show();
                    cv_register.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(bio.toString())){
                    bio_register.setError("Bio is Required");
                    Toast.makeText(RegisterActivityTeacher.this, "Bio is Required", Toast.LENGTH_SHORT).show();
                    bio_register.requestFocus();
                    return;
                }

                //register the user

                fAuth.fetchSignInMethodsForEmail(email_register.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean check = !task.getResult().getSignInMethods().isEmpty();
                                //nu exista cont cu acest mail, pot sa fac unul
                                if(!check){
                                    fAuth.createUserWithEmailAndPassword(email_register.getText().toString().trim(),password_register.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                User user = new User(name_register.getText().toString().trim(), email_register.getText().toString().trim(), password_register.getText().toString().trim(), address_register.getText().toString().trim(), phone_register.getText().toString().trim(), birth_register.getText().toString().trim(), instruments_register.getText().toString().trim());
                                                FirebaseDatabase.getInstance().getReference("Teachers")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            uploadToFirebaseProfileImage(imageUri);
                                                            uploadToFirebaseCV(fileUri);
                                                            Toast.makeText(RegisterActivityTeacher.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(RegisterActivityTeacher.this, "User data failed registered", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(RegisterActivityTeacher.this, "User failed registered", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                //exista cont cu acest mail
                                else
                                {
                                    Toast.makeText(RegisterActivityTeacher.this, "Email already present", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }








       private void uploadToFirebaseProfileImage(Uri uri){
           StorageReference Folder = FirebaseStorage.getInstance().getReference().child("ProfileImage");
           StorageReference fileRef = Folder.child(System.currentTimeMillis()+"."+getFilExtintion(imageUri));
           fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           FirebaseDatabase.getInstance().getReference("Teachers")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userProfileImage")
                                   .setValue(uri.toString());
                       }
                   });
               }
           });
       }


    private void uploadToFirebaseCV(Uri uri){
        StorageReference Folder = FirebaseStorage.getInstance().getReference().child("CV");
        StorageReference fileRef = Folder.child(System.currentTimeMillis()+"."+getFilExtintion(fileUri));
        fileRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("teacherCV",uri.toString().trim());
                        map.put("teacherBio",bio_register.getText().toString().trim());
                        FirebaseDatabase.getInstance().getReference("Teachers")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("CV-BIO").setValue(map);
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

        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            fileUri = data.getData();
            cv_register.setText(fileUri.toString().substring(fileUri.toString().lastIndexOf("/")+1));
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