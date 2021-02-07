package com.example.firebaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextView text;
    EditText email,pass;
    Button btn;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseHelpeer helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text = findViewById(R.id.textView);
        email = findViewById(R.id.LoginEmail);
        pass =findViewById(R.id.LoginPassword);
        btn = findViewById(R.id.Login);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        helper = new FirebaseHelpeer(database,reference,mAuth,Login .this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString().trim();
                String p = pass.getText().toString().trim();
                if(e.isEmpty() ){
                    Toast.makeText(Login.this, "Email cannot be null", Toast.LENGTH_SHORT).show();
                }
                else if(p.isEmpty()){
                    Toast.makeText(Login.this, "Password Can not be null", Toast.LENGTH_SHORT).show();
                }
                else {
                    helper.Login(e, p);
                    e = "";
                    p= "";
                    email.setText("");
                    pass.setText("");
                }
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });
    }
}