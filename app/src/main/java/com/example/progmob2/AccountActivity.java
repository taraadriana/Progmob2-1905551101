package com.example.progmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {

    public TextView accProfile, accName, accEmail, accUsername, accPassword,
            accGender, accHobby, accAge;
    DbHelper dbHelper;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        dbHelper = new DbHelper(this);

        ImageView back = findViewById(R.id.arrowbackAcc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accProfile = (TextView) findViewById(R.id.profile_name);
        accName = (TextView) findViewById(R.id.acc_name);
        accEmail = (TextView) findViewById(R.id.acc_email);
        accUsername = (TextView) findViewById(R.id.acc_username);
        accPassword = (TextView) findViewById(R.id.acc_password);
        accGender = (TextView) findViewById(R.id.acc_gender);
        accAge = (TextView) findViewById(R.id.acc_age);
        accHobby = (TextView) findViewById(R.id.acc_hobby);


        DbHelper dbHelper = new DbHelper(AccountActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_user WHERE username = '" +
                getIntent().getStringExtra("MainName") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            accProfile.setText(cursor.getString(3).toString());
            accName.setText(cursor.getString(1).toString());
            accEmail.setText(cursor.getString(2).toString());
            accUsername.setText(cursor.getString(3).toString());
            accPassword.setText(cursor.getString(4).toString());
            accGender.setText(cursor.getString(6).toString());
            accAge.setText(cursor.getString(5).toString());
            accHobby.setText(cursor.getString(7).toString());
        }

        Toast.makeText(this, "onCreate ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

}