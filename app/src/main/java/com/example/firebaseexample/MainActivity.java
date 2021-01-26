package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    EditText name, pass, email;
    Spinner spinner;
    Button btn,dt,tm,pic;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView imageView;
    CheckBox c1, c2, c3;
    ArrayList<String> LangList;
    String n,e,p,SDate,STime;
    public static final int PICK_IMAGE = 1;
    public Uri imageUri,downlaodUri;
    public int mYear, mMonth, mDay, mHour, mMinute;


    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        name = findViewById(R.id.editTextTextPersonName);
        pass = findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.editTextTextEmailAddress);
        radioGroup = findViewById(R.id.radioGroup);
        LangList = new ArrayList<String>();
        c1 = findViewById(R.id.checkBox);
        c2 = findViewById(R.id.checkBox2);
        c3 = findViewById(R.id.checkBox3);


        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageView= findViewById(R.id.ProfileImage);

        dt = findViewById(R.id.Date);
        tm = findViewById(R.id.Time);
        pic = findViewById(R.id.button7);
        btn = findViewById(R.id.button);



        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SDate = dayOfMonth + "-" + (month + 1) + "-" + year+"";

                        Toast.makeText(MainActivity.this, ""+dayOfMonth + "-" + (month + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });


        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar time = Calendar.getInstance();
                mHour = time.get(Calendar.HOUR_OF_DAY);
                mMinute = time.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        STime = hourOfDay + ":" + minute+"";
                        Toast.makeText(MainActivity.this, ""+hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();

            }
        });











        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 n = name.getText().toString().trim();
                 p = pass.getText().toString().trim();
                 e = email.getText().toString();
                String c = spinner.getSelectedItem().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                if (n.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the Name", Toast.LENGTH_SHORT).show();

                }
                else if (p.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the Password", Toast.LENGTH_SHORT).show();

                }
                else if (c.trim().equals("Select Country")) {
                    Toast.makeText(MainActivity.this, "Please select a Country", Toast.LENGTH_SHORT).show();

                }
                else if (e.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the email", Toast.LENGTH_SHORT).show();

                }
                else if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Please select ur gender ", Toast.LENGTH_SHORT).show();

                }
                else if(!c1.isChecked() && !c2.isChecked() && !c3.isChecked()){
                    Toast.makeText(MainActivity.this, "Please select a Language You Speak", Toast.LENGTH_SHORT).show();
                }
                else {


                    if(c1.isChecked()){
                        LangList.add(c1.getText().toString());
                    }
                    else {
                        c1.setChecked(false);
                        LangList.remove(c1.getText().toString());
                    }

                    if(c2.isChecked()){
                        LangList.add(c2.getText().toString());
                    }
                    else {

                        c2.setChecked(false);
                    }
                    if(c3.isChecked()){
                        LangList.add(c3.getText().toString());
                    }
                    else {
                        c3.setChecked(false);
                        LangList.remove(c3.getText().toString());

                    }

                    User users = new User();
                    users.Name = n;
                    users.Email = e;
                    users.Password = p;
                    users.Gender = radioButton.getText().toString();
                    users.Country = c;
                    users.Language = LangList.toString();
                    users.Date = SDate.toString();
                    users.Time = STime.toString();
                    users.Uri = downlaodUri.toString();

                    ref.push().setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
                                LangList.clear();
                                c1.setChecked(false);
                                c2.setChecked(false);
                                c3.setChecked(false);
                                radioButton.setChecked(false);
                            name.setText("");
                            email.setText("");
                            pass.setText("");
                            downlaodUri ="";
                            imageUri ="";
                                spinner.setSelection(0);
                            } else {
                                Toast.makeText(MainActivity.this, "fail to add data ", Toast.LENGTH_SHORT).show();
                                LangList.clear();
                                c1.setChecked(false);
                                c2.setChecked(false);
                                c3.setChecked(false);
                                radioButton.setChecked(false);
                                name.setText("");
                                email.setText("");
                                pass.setText("");
                                downlaodUri ="";
                                imageUri ="";
                                spinner.setSelection(0);
                            }
                        }
                    });
                }
            }
        });



        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent , PICK_IMAGE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            else {

                    imageUri = data.getData();
                    imageView.setImageURI(imageUri);
                    uploadPic();

            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }


    public void uploadPic() {

        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("File Uploading...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference reference = storageReference.child("Users/" + randomKey);


        UploadTask uploadTask = reference.putFile(imageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

               //  downlaodUri =taskSnapshot.getDownloadUrl();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downlaodUri = uri;
                        pd.dismiss();
                    }
                });
            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Sorry there is a problem uploading image change image", Toast.LENGTH_SHORT).show();
            }
        });

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPer= (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress :"+(int) progressPer + "%");
            }
        });


    }
}