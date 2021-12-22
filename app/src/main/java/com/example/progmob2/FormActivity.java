package com.example.progmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {
    EditText txTitle, txYear, txGenre, txEpisode, txActor, txSinopsis;
    Button btnSubmit;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txTitle = (EditText) findViewById(R.id.editTextTitle);
        txYear = (EditText) findViewById(R.id.editTextYear);
        txGenre = (EditText) findViewById(R.id.editTextGenre);
        txEpisode = (EditText) findViewById(R.id.editTextEpisode);
        txActor = (EditText) findViewById(R.id.editTextActor);
        txSinopsis = (EditText) findViewById(R.id.editTextSinopsis);
        btnSubmit = (Button) findViewById(R.id.btnSubmitForm);

        dbHelper = new DbHelper(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txTitle.getText().toString().trim();
                String year = txYear.getText().toString().trim();
                String genre = txGenre.getText().toString().trim();
                String episode = txEpisode.getText().toString().trim();
                String actor = txActor.getText().toString().trim();
                String sinopsis = txSinopsis.getText().toString().trim();


                Boolean res = validasi(title, year,  genre, episode, actor, sinopsis);
                if (res){
                    dbHelper.insertData(title, Integer.valueOf(txYear.getText().toString().trim()),  genre,
                            Integer.valueOf(txEpisode.getText().toString().trim()), actor, sinopsis);
                    finish();

                }else{
                    Toast.makeText(FormActivity.this, "Data tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validasi(String title, String year,  String genre, String episode,
                            String actor, String sinopsis){
        if (title.length() == 0) {
            txTitle.requestFocus();
            txTitle.setError("Title tidak boleh kosong!");
            return false;

        } else if (year.length() == 0) {
            txYear.requestFocus();
            txYear.setError("Year tidak boleh kosong!");
            return false;

        } else if (genre.length() == 0) {
            txGenre.requestFocus();
            txGenre.setError("Genre tidak boleh kosong!");
            return false;

        } else if (episode.length() == 0){
            txEpisode.requestFocus();
            txEpisode.setError("Episode tidak boleh kosong!");
            return false;

        } else if (actor.length() == 0) {
            txActor.requestFocus();
            txActor.setError("Actor tidak boleh kosong!");
            return false;

        } else if (sinopsis.length() == 0){
            txSinopsis.requestFocus();
            txSinopsis.setError("Sinopsis tidak boleh kosong!");
            return false;

        }else{
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}