package com.example.guestmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guest.Database.DBHelper;
import com.guest.Session.Session;

import java.util.Calendar;

public class editGuest extends AppCompatActivity {

    private EditText txth1, txth2, txth3, txth4, txt3, txth5;
    private Button button;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DBHelper guestDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guest);

        session = new Session(this);

        guestDb = new DBHelper(this);

        button = findViewById(R.id.button);

        txth1 = (EditText) findViewById(R.id.txth1);
        txth2 = (EditText) findViewById(R.id.txth2);
        txth3 = (EditText) findViewById(R.id.txth3);
        txth4 = (EditText) findViewById(R.id.txth4);

        //Select birthday when clicked
        editBday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditProfile.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //Set edittext to selected birthday
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int newMonth = month + 1;
                String date = year + "/" + newMonth + "/" + dayOfMonth;
                editBday.setText(date);
            }
        };

        setUserData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.menuLogout){
            session.logout();
            finish();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

        else if(id==R.id.menueditProfile){
            Intent intent = new Intent(this, EditProfile.class);
            startActivity(intent);
        }

        else if(id==R.id.menuProfile){
            Intent intent = new Intent(this, viewProfile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String first = txth1.getText().toString();
                String last = txth2.getText().toString();
                String email =txth4.getText().toString();
                String contactNo = txth3.getText().toString();

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
                    toastText.setText(".!");
                    toast.show();
                }

                else if(email.trim().length() == 0){
                    toastText.setText("Enter Email.");
                    toast.show();
                }
                else if(!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
                    toastText.setText("Invalid Email.!");
                    toast.show();
                }
                else if(contact.trim().length() == 0){
                    toastText.setText("Enter contact.!");
                    toast.show();
                }
                else if(contact.trim().length() != 10 ) {
                    toastText.setText("Invalid contact number.!");
                    toast.show();
                }

                //Register New Guest
                else{
                    int id = Integer.parseInt(session.getSessionId());

                    boolean status = guestDb.updateInfo(id, fullName, birthday, nic, email, contactNo, password);

                    if (status == true) {
                        updateSuccessfulDialog successDialog = new updateSuccessfulDialog();
                        successDialog.show(getSupportFragmentManager(),"success");
                        session.logout();

                    } else {
                        Toast.makeText(EditProfile.this, "Guest can not be update", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void setGuestData(){
        int id = Integer.parseInt(session.getSessionId());
        Cursor details = guestDb.getUserData(id);
        txth1.setText(details.getString(details.getColumnIndex("first")));
        txth2.setText(details.getString(details.getColumnIndex("last")));
        txth3.setText("0" + details.getString(details.getColumnIndex("contact")));
        editParticipant.setText(details.getString(details.getColumnIndex("participant")));
        editConfirm.setText(details.getString(details.getColumnIndex("confirm")));
        editNote.setText(details.getString(details.getColumnIndex("note")));
    }
}
