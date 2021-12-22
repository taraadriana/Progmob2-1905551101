package com.example.progmob2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity{

    public EditText name, email, username, password;
    public Button btnRegister;
    public RadioGroup gender;
    public RadioButton r1, r2;
    public SeekBar seekBar;
    public TextView textSeekbar;
    public CheckBox cb1, cb2, cb3;
    public TextView alertName, alertEmail, alertUsername, alertPassword,
            alertGender, alertHobby, alertAge;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);

        gender = (RadioGroup) findViewById(R.id.gender);
        r1 = (RadioButton) findViewById(R.id.male);
        r2 = (RadioButton) findViewById(R.id.female);

        cb1 = findViewById(R.id.olahraga);
        cb2 = findViewById(R.id.seni);
        cb3 = findViewById(R.id.lainnya);

        seekBar = findViewById(R.id.seekbar);
        textSeekbar = findViewById(R.id.textSeekbar);

        dbHelper = new DbHelper(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textSeekbar.setText(progress + "/100");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnRegister.setOnClickListener(v -> {

            String ambilName = name.getText().toString();
            String ambilEmail = email.getText().toString();
            String ambilUsername = username.getText().toString();
            String ambilPassword = password.getText().toString();

            int ambilAge = seekBar.getProgress();
            int ambilGender = gender.getCheckedRadioButtonId();

            View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);

            boolean check = validasiForm(ambilName, ambilEmail, ambilUsername, ambilPassword,
                    ambilGender);
            if (check == true) {
                alertName = (TextView) view.findViewById(R.id.ad_name);
                alertName.setText(ambilName);

                alertEmail = (TextView) view.findViewById(R.id.ad_email);
                alertEmail.setText(ambilEmail);

                alertUsername = (TextView) view.findViewById(R.id.ad_username);
                alertUsername.setText(ambilUsername);

                alertPassword = (TextView) view.findViewById(R.id.ad_password);
                alertPassword.setText(ambilPassword);

                alertGender = (TextView) view.findViewById(R.id.ad_gender);
                if (ambilGender == r1.getId()) {
                    alertGender.setText("male");
                } else if (ambilGender == r2.getId()) {
                    alertGender.setText("female");
                }

                String ambilGenderString = alertGender.getText().toString();
                alertHobby = (TextView) view.findViewById(R.id.ad_hobby);
                String a = "";
                if (cb1.isChecked()) {
                    a += "olahraga ,";
                }
                if (cb2.isChecked()) {
                    a += " seni ";
                }
                if (cb3.isChecked()) {
                    a += " lainnya";
                }
                alertHobby.setText(a);

                alertAge = (TextView) view.findViewById(R.id.ad_age);
                alertAge.setText(String.valueOf(ambilAge));

                AlertDialog.Builder dialog1 = new AlertDialog.Builder(RegisterActivity.this);
                dialog1.setPositiveButton("ok", (dialogInterface, which) -> {


                    ContentValues values = new ContentValues();

                    values.put(dbHelper.row_name, ambilName);
                    values.put(dbHelper.row_email, ambilEmail);
                    values.put(dbHelper.row_username, ambilUsername);
                    values.put(dbHelper.row_password, ambilPassword);
                    values.put(dbHelper.row_age, ambilAge);
                    values.put(dbHelper.row_gender, ambilGenderString);
                    values.put(dbHelper.row_hobby, alertHobby.getText().toString());
                    dbHelper.insertDataUser(values);

                    Intent intent = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    intent.putExtra("name",ambilName);
                    intent.putExtra("email",ambilEmail);
                    intent.putExtra("username",ambilUsername);
                    intent.putExtra("password",ambilPassword);
                    intent.putExtra("age",ambilAge);
                    intent.putExtra("gender",ambilGenderString);
                    intent.putExtra("hobby",alertHobby.getText().toString());
                    startActivity(intent);
                    //finish();
                });

                dialog1.setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.cancel());

                dialog1.setView(view);
                AlertDialog dialog2 = dialog1.create();
                dialog2.show();

            }else {
                Toast.makeText(getApplicationContext(),"Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validasiForm(String ambilName, String ambilEmail, String ambilUsername, String ambilPassword,
                                Integer ambilGender) {
        if (ambilName.length() == 0) {
            name.requestFocus();
            name.setError("Nama tidak boleh kosong!");
            return false;

        } else if (ambilEmail.length() == 0) {
            email.requestFocus();
            email.setError("Email tidak boleh kosong!");
            return false;
        } else if (!ambilEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            email.requestFocus();
            email.setError("Email tidak valid!");
            return false;

        } else if (ambilUsername.length() == 0) {
            username.requestFocus();
            username.setError("Username tidak boleh kosong!");
            return false;

        } else if (ambilPassword.length() <= 5){
            password.requestFocus();
            password.setError("Minimal 6 karakter!");
            return false;

        } else if(ambilGender == -1){
            Toast.makeText(getApplicationContext(),"Jenis kelamin harus diisi!", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked()){
            Toast.makeText(getApplicationContext(),"Hobby tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return false;

        }else{
            return true;
        }
    }

    public void onTextLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onRegisterClick(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
