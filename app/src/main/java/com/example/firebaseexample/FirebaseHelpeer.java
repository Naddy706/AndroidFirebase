package com.example.firebaseexample;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelpeer {



    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    Context context;

    public FirebaseHelpeer(FirebaseDatabase database, DatabaseReference reference, FirebaseAuth mAuth, Context context) {
        this.database = database;
        this.reference = reference;
        this.mAuth = mAuth;
        this.context = context;
    }



    public FirebaseHelpeer(){

    }

    public void Register(String email , String password){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Register user sucessful", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(context, "Failed to Register User", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void Login(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(context,MainActivity.class);
                    context.startActivity(i);

                }
                else {
                    Toast.makeText(context, "email or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        });


    }






}
