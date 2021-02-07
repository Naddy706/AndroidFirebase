package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

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
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser us=mAuth.getCurrentUser();
        if(us != null){
            Intent i = new Intent(SignUp.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    text = findViewById(R.id.textView);
    email = findViewById(R.id.SignEmail);
    pass =findViewById(R.id.SignPassword);
    btn = findViewById(R.id.SignUp);

    database = FirebaseDatabase.getInstance();
    reference = database.getReference();
    mAuth = FirebaseAuth.getInstance();
    helper = new FirebaseHelpeer(database,reference,mAuth,SignUp.this);



    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String e = email.getText().toString().trim();
            String p = pass.getText().toString().trim();
            if(e.isEmpty() ){
                Toast.makeText(SignUp.this, "Email cannot be null", Toast.LENGTH_SHORT).show();
            }
            else if(p.isEmpty()){
                Toast.makeText(SignUp.this, "Password Can not be null", Toast.LENGTH_SHORT).show();
            }
            else if(p.length()<6){
                Toast.makeText(SignUp.this, "Password must be greater then 5 charater", Toast.LENGTH_SHORT).show();
            }
            else {
                helper.Register(e, p);
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
            Intent i = new Intent(SignUp.this,Login.class);
            startActivity(i);
        }
    });

    }
}