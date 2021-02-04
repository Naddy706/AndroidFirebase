package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.icu.lang.UScript;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class UserShow extends AppCompatActivity {


    ListView listView;
    ArrayList<User> users;
    public FirebaseDatabase database;
    public DatabaseReference reference;
    UserAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show);

        users = new ArrayList<User>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Users");
        getData();

         adapter = new UserAdapter(UserShow.this,users,database);

        listView = findViewById(R.id.list);
       listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = ((TextView) view.findViewById(R.id.id)).getText().toString();

                // Toast.makeText(UserShow.this, ""+selected, Toast.LENGTH_SHORT).show();
                AlertDialog builder = new AlertDialog.Builder(UserShow.this).create();
                    builder.setTitle(selected);
                    builder.setMessage("Match Ids");
                    builder.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }


        });



    }


    public  void getData(){



        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot ds:snapshot.getChildren()) {
                    User u = new User();
                    u.Id =ds.getKey();
                    u.Country = ds.getValue(User.class).getCountry();
                    u.Date = ds.getValue(User.class).getDate();
                    u.Email = ds.getValue(User.class).getEmail();
                    u.Gender =  ds.getValue(User.class).getGender();
                    u.Language = ds.getValue(User.class).getLanguage();
                    u.Name = ds.getValue(User.class).getName();
                    u.Password = ds.getValue(User.class).getPassword();
                    u.Time =ds.getValue(User.class).getTime();
                    u.Uri = ds.getValue(User.class).getUri();

                    users.add(u);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }





}