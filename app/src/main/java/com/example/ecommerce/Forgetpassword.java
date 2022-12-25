package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Forgetpassword extends AppCompatActivity {
private EditText emailedittext;
private Button resetpassbtn;
private ProgressBar progressBar;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://shopgate-9ec98-default-rtdb.firebaseio.com");
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        emailedittext=(EditText) findViewById(R.id.forgetpasss);
        resetpassbtn =(Button) findViewById(R.id.resetbtn);
        auth=FirebaseAuth.getInstance();
        final String username11=emailedittext.getText().toString();
        resetpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass();
             }
        });
        }
    public void resetPass(){
        String Mailtxt = emailedittext.getText().toString().trim();
        if(Mailtxt.isEmpty()){
            emailedittext.setError("Email is Required to reset your password");
            emailedittext.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Mailtxt).matches()){
            emailedittext.setError("Email isn't Valid");
            emailedittext.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(Mailtxt).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Forgetpassword.this , "Check your email to reset your password" ,Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(Forgetpassword.this , "Try again something went wrong" ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }






}