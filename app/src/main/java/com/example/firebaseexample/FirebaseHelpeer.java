package com.example.firebaseexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseHelpeer {



    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Context context;
    ProgressDialog pd;

    public FirebaseHelpeer(FirebaseDatabase database, DatabaseReference reference, FirebaseAuth mAuth, Context context) {
        this.database = database;
        this.reference = reference;
        this.mAuth = mAuth;
        this.context = context;
    }



    public FirebaseHelpeer(){

    }

    public void Register(String email , String password){
        pd = new ProgressDialog(context);
        pd.setTitle("User Being Registered...");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(context, "Register user sucessful", Toast.LENGTH_SHORT).show();
                    storeUser(email,password);
                    sendEmail();
                    Intent i = new Intent(context,Login.class);
                    context.startActivity(i);
                    ((SignUp)context).finish();
                    pd.dismiss();

                }
                else{
                    Toast.makeText(context, "Failed to Register User", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

    }

  public  void storeUser(String email,String pass){

        register reg= new register();
        reg.email = email;
        reg.token = FirebaseInstanceId.getInstance().getToken();
        reference.child("User").push().setValue(reg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(context, "user registered sucessfully", Toast.LENGTH_SHORT).show();
                    Login(email,pass);
                }
                else {
                    Toast.makeText(context, "registeration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
  }

  public void sendEmail(){
        user =mAuth.getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "verification Email sent..", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Verification email failed", Toast.LENGTH_SHORT).show();
            }
        });
  }


    public void Login(String email, String password){
        pd = new ProgressDialog(context);
        pd.setTitle("Please Wait checking Credentials...");
        pd.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(context,MainActivity.class);
                    context.startActivity(i);
                    ((SignUp)context).finish();
                    pd.dismiss();
                }
                else {
                    Toast.makeText(context, "email or password incorrect", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });


    }






}
