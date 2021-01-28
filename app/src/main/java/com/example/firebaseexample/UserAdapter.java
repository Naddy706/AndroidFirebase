package com.example.firebaseexample;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter  extends BaseAdapter {

    Context context;
    ArrayList<User> users;

    LayoutInflater layoutInflater;


    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
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
        myHolder.name.setText(users.get(position).getName());
        myHolder.email.setText(users.get(position).getEmail());
        myHolder.country.setText(users.get(position).getCountry());

        String i= users.get(position).getUri();

        Picasso.get().load(i).into(myHolder.image);

        return  convertView;



    }

    public class MyHolder {
        TextView name,email,country;
        ImageView image;

        public MyHolder(View view){
            name=view.findViewById(R.id.name);
            email =view.findViewById(R.id.email);
            country = view.findViewById(R.id.country);
            image = view.findViewById(R.id.imageView);

        }



    }


}






