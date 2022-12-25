package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    Button b1;
    private static final String FILE_EMAIL="remember";
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://shopgate-9ec98-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        auth = FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        b1 = findViewById(R.id.regbtn);
        final EditText emailid=findViewById(R.id.email);
        final EditText logpass=findViewById(R.id.logpass);
        final Button logbtn=findViewById(R.id.loginbtn);
        final TextView forgetpassword=findViewById(R.id.forgetpass);
        CheckBox remember=(CheckBox)findViewById(R.id.remember);
        SharedPreferences sharedPreferences=getSharedPreferences(FILE_EMAIL,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String email=sharedPreferences.getString("svEmail","");
        String password=sharedPreferences.getString("svPassword","");
        if(sharedPreferences.contains("checked")&&sharedPreferences.getBoolean("checked",false)==true){
            remember.setChecked(true);
        }else{
            remember.setChecked(false);
        }
        emailid.setText(email);
        logpass.setText(password);



        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,Forgetpassword.class));
            }
        });


        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Emailtxt=emailid.getText().toString();
                final String logpasstxt=logpass.getText().toString();

                if(remember.isChecked()) {
                    editor.putBoolean("checked", true);
                    editor.apply();
                    StoreDataUsingSharedPref(Emailtxt, logpasstxt);


                    if (Emailtxt.isEmpty() || logpasstxt.isEmpty()) {
                        Toast.makeText(LoginPage.this, "Please Enter Your Email or Password", Toast.LENGTH_SHORT).show();

                    }
                    auth.signInWithEmailAndPassword(Emailtxt,logpasstxt)
                            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginPage.this , "Login Success" ,Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LoginPage.this,HomePage.class));
                                    }
                                    else {
                                        Toast.makeText(LoginPage.this , " Error occurred password may be wrong ",Toast.LENGTH_LONG).show();
                                        Toast.makeText(LoginPage.this , "Error"+task.getException() ,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    getSharedPreferences(FILE_EMAIL,MODE_PRIVATE).edit().clear().commit();
                    auth.signInWithEmailAndPassword(Emailtxt,logpasstxt)
                            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginPage.this , "Login Success" ,Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LoginPage.this,HomePage.class));
                                    }
                                    else {
                                        Toast.makeText(LoginPage.this , " Error occurred password may be wrong ",Toast.LENGTH_LONG).show();
                                        Toast.makeText(LoginPage.this , "Error"+task.getException() ,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginPage.this,RegistrationPage.class);
                startActivity(i);


            }
        });
    }
    private void StoreDataUsingSharedPref(String email, String password) {
        SharedPreferences.Editor editor=getSharedPreferences(FILE_EMAIL,MODE_PRIVATE).edit();

        editor.putString("svEmail",email);
        editor.putString("svPassword",password);
        editor.apply();
    }
}