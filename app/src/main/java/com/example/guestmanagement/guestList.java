package com.example.guestmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.guest.Database.DBHelper;
import com.guest.Session.Session;

public class guestList extends AppCompatActivity {

    private TextView viewFirst, viewLast, viewContact, viewEmail, viewParticipants, viewConfirm, viewNote;
    private Button editButton, deleteButton;
    private Session session;
    DBHelper userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        session = new Session(this);

        userDb = new DBHelper(this);

        viewFirst = (TextView) findViewById(R.id.viewFirst);
        viewLast = (TextView) findViewById(R.id.viewLast);
        viewContact = (TextView) findViewById(R.id.viewContact);
        viewParticipants = (TextView) findViewById(R.id.viewParticipants);
        viewConfirm = (TextView) findViewById(R.id.viewConfirm);
        viewNote = (TextView) findViewById(R.id.viewNote);

        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        setUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });

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

    public void setUserData(){
        int id = Integer.parseInt(session.getSessionId());
        Cursor details = userDb.getUserData(id);
        vName.setText("Name: " + details.getString(details.getColumnIndex("fullname")));
        vBirthday.setText("D.O.B. : " +details.getString(details.getColumnIndex("birthday")));
        vContactNo.setText("Contact No: 0" + details.getString(details.getColumnIndex("contactno")));
        vEmail.setText("Email: " + details.getString(details.getColumnIndex("email")));
        vNic.setText("NIC: " + details.getString(details.getColumnIndex("nic")));
    }

    public void deleteAccount(){
        int id = Integer.parseInt(session.getSessionId());

        boolean status = guestDb.deleteAccount(id);
        if(status == true) {
            deleteAccountDialog successDialog = new deleteAccountDialog();
            successDialog.show(getSupportFragmentManager(),"success");
            session.logout();
        }
        else{
            Toast.makeText(viewGuest.this, "Guest can not be deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}
