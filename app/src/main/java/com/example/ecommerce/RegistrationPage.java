package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RegistrationPage extends AppCompatActivity {
EditText etDate;
    private FirebaseAuth auth ;
DatePickerDialog.OnDateSetListener setListener;
DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://shopgate-9ec98-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        auth = FirebaseAuth.getInstance();
        etDate=findViewById(R.id.datereg);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        final EditText usernamereg=findViewById(R.id.username);
        final EditText firstname=findViewById(R.id.firstname);
        final EditText secondname=findViewById(R.id.secondname);
        final EditText emailreg=findViewById(R.id.emailreg);
        final EditText passwordreg=findViewById(R.id.passwordreg);
        final EditText datereg=findViewById(R.id.datereg);
        final Button regbtn=findViewById(R.id.regbtn);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernametxt=usernamereg.getText().toString();
                final String fullnametxt=firstname.getText().toString()+" "+secondname.getText().toString();
                final String emailregtxt=emailreg.getText().toString();
                final String passwordregtxt=passwordreg.getText().toString();
                final String dateregtxt=datereg.getText().toString();
                if(fullnametxt.isEmpty() ||emailregtxt.isEmpty()||passwordregtxt.isEmpty()||dateregtxt.isEmpty()){
                    Toast.makeText(RegistrationPage.this,"Please FILL all fields",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernametxt))
                            {
                                Toast.makeText(RegistrationPage.this,"Username should be unique",Toast.LENGTH_SHORT).show();
                            }else if (passwordregtxt.length()<8){
                                Toast.makeText(RegistrationPage.this,"Password should be at least 8 numbers",Toast.LENGTH_SHORT).show();
                            }else if(!Patterns.EMAIL_ADDRESS.matcher(emailregtxt).matches()){
                                emailreg.setError("please provide valid email");
                                emailreg.requestFocus();
                                return;
                            }
                            else{
                                auth.createUserWithEmailAndPassword(emailregtxt,passwordregtxt)
                                        .addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegistrationPage.this , "Added" ,Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(RegistrationPage.this,LoginPage.class));
                                                }
                                                else{
                                                    Toast.makeText(RegistrationPage.this , "Failed" ,Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                databaseReference.child("users").child(usernametxt).child("addToCart").child("ID:").setValue(fullnametxt);
                                databaseReference.child("users").child(usernametxt).child("FullName").setValue(fullnametxt);
                                databaseReference.child("users").child(usernametxt).child("Password").setValue(passwordregtxt);
                                databaseReference.child("users").child(usernametxt).child("Date").setValue(dateregtxt);
                                databaseReference.child("users").child(usernametxt).child("Email address").setValue(emailregtxt);


                                Toast.makeText(RegistrationPage.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            auth.createUserWithEmailAndPassword(emailregtxt,passwordregtxt)
                                    .addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegistrationPage.this , "Added" ,Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegistrationPage.this,LoginPage.class));
                                            }
                                            else{
                                                Toast.makeText(RegistrationPage.this , "Failed" ,Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }
                    });

                }
            }
        });
        etDate.setShowSoftInputOnFocus(false);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(RegistrationPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        }
        );
    }
}