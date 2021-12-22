package com.example.progmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public EditText Txusername, Txpassword;
    public Button btnLogin;
    public TextView textLogin, textSignup;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Txusername = findViewById(R.id.editTextUsername);
        Txpassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textLogin = findViewById(R.id.textLogin);
        textSignup = findViewById(R.id.textSignup);

        dbHelper = new DbHelper(this);
    }

    public void onLoginClick (View view){
        if(view.getId() == R.id.btnLogin) {
            String ambilUsername = Txusername.getText().toString().trim();
            String ambilPassword = Txpassword.getText().toString().trim();

            Boolean res = dbHelper.checkUser(ambilUsername, ambilPassword);
            if (res){
                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("UserNameLogin", ambilUsername);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, "Login failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onTextLogin (View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onRegisterClick (View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

}