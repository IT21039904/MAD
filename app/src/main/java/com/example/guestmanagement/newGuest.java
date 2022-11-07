package com.example.guestmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guest.Database.DBHelper;

import java.util.Calendar;

public class newGuest extends AppCompatActivity {

    private editFirst, editLast, editContact, editEmail, editParticipant, editConfirm, editNote;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DBHelper guestDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        guestDb = new DBHelper(this);
        SQLiteDatabase db = guestDb.getReadableDatabase();

        editFirst = (EditText) findViewById(R.id.editFirst);
        editLast = (EditText) findViewById(R.id.editLast);
        editContact = (EditText) findViewById(R.id.editContact);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editParticipant = (EditText) findViewById(R.id.editParticipant);
        editConfirm = (EditText) findViewById(R.id.editConfirm);
        editNote = (EditText) findViewById(R.id.editNote);



        @Override
        protected void onResume() {
            super.onResume();

            registerButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    String first = editFirst.getText().toString();
                    String last = editLast.getText().toString();
                    String email = editEmail.getText().toString();
                    String contact = editContact.getText().toString();
                    String participant = editParticipant.getText().toString();
                    String confirm = editConfirm.getText().toString();
                    String note = editNote.getText().toString();

                    //Custom Error Toast Settings
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.id_error_toast));

                    TextView toastText = layout.findViewById(R.id.error_text);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);

                    //Validations
                    if(first.trim().length() == 0){
                        toastText.setText("Enter first name.!");
                        toast.show();
                    }

                    else if(last.trim().length() == 0){
                        toastText.setText("Enter last name.!");
                        toast.show();
                    }

                    else if(email.trim().length() == 0){
                        toastText.setText("Enter valid email.");
                        toast.show();
                    }
                    else if(!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
                        toastText.setText("Enter a valid mail.!");
                        toast.show();
                    }
                    else if(contactNo.trim().length() == 0){
                        toastText.setText("Enter a valid contact number.!");
                        toast.show();
                    }


                    //Register New Guest
                    else{
                        long status = guestDb.addUser(first, last, contact, email, participant, confirm, note);

                        if (status > 0) {
                            regSuccessfulDialog successDialog = new regSuccessfulDialog();
                            successDialog.show(getSupportFragmentManager(),"success");

                        } else {
                            Toast.makeText(Register.this, "Guest can not be updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }

        //Finish activity when click login button
        public void openLoginActivity(){
            finish();
        }