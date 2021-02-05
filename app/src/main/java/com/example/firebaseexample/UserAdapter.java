package com.example.firebaseexample;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter  extends BaseAdapter {

    Context context;
    ArrayList<User> users;



    FirebaseDatabase database;

    LayoutInflater layoutInflater;


    public UserAdapter(Context context, ArrayList<User> users,FirebaseDatabase database) {
        this.context = context;
        this.users = users;
        this.database = database;
    }




    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(layoutInflater == null ){
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.mylist,parent,false);
        }

        MyHolder myHolder = new MyHolder(convertView);
        myHolder.id.setText(users.get(position).getId());
        myHolder.name.setText(users.get(position).getName());
        myHolder.email.setText(users.get(position).getEmail());
        myHolder.country.setText(users.get(position).getCountry());

        String i= users.get(position).getUri();

        Picasso.get().load(i).into(myHolder.image);

        myHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image clicked", Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog builder = new AlertDialog.Builder(context).create();
                builder.setTitle("Delete");
                builder.setMessage("Are You Sure U want delete Data");
              //  builder.setCancelable(false);
                builder.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Del(users.get(position).getId(),position);

                        dialog.dismiss();
                    }
                });
                builder.show();


            }
        });


        myHolder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,MainActivity.class);

                i.putExtra("id",users.get(position).getId());
                i.putExtra("name",users.get(position).getName());
                i.putExtra("email",users.get(position).getEmail());
                i.putExtra("password",users.get(position).getPassword());
                i.putExtra("country",users.get(position).getCountry());
                i.putExtra("gender",users.get(position).getGender());
                i.putExtra("language",users.get(position).getLanguage());
                i.putExtra("date",users.get(position).getDate());
                i.putExtra("time",users.get(position).getTime());
                i.putExtra("uri",users.get(position).getUri());

                context.startActivity(i);


            }
        });







        return  convertView;



    }




public void Del(String id,int i){
        database.getReference().child("Users").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if(task.isSuccessful()){
                Toast.makeText(context, "data delete", Toast.LENGTH_SHORT).show();

              UserAdapter.super.notifyDataSetChanged();



            }else{
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
        });
}



    public class MyHolder {
        TextView id,name,email,country;
        ImageView image;
        Button up,dt;

        public MyHolder(View view){
            id = view.findViewById(R.id.id);
            name=view.findViewById(R.id.name);
            email =view.findViewById(R.id.email);
            country = view.findViewById(R.id.country);
            image = view.findViewById(R.id.imageView);
            up=view.findViewById(R.id.update);
            dt = view.findViewById(R.id.delete);
        }




    }


}






