package com.example.progmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    Cursor cursor;
    EditText txTitle, txYear, txGenre, txEpisode, txActor, txSinopsis;
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txTitle = findViewById(R.id.updateTitle);
        txYear = findViewById(R.id.updateYear);
        txGenre = findViewById(R.id.updateGenre);
        txEpisode = findViewById(R.id.updateEpisode);
        txActor = findViewById(R.id.updateActor);
        txSinopsis = findViewById(R.id.updateSinopsis);


        DbHelper dbHelper = new DbHelper(EditActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_drama WHERE title = '" +
                getIntent().getStringExtra("title") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            txTitle.setText(cursor.getString(1).toString());
            txYear.setText(cursor.getString(2).toString());
            txGenre.setText(cursor.getString(3).toString());
            txEpisode.setText(cursor.getString(4).toString());
            txActor.setText(cursor.getString(5).toString());
            txSinopsis.setText(cursor.getString(6).toString());
            oldTitle = cursor.getString(1);
        }
    }

    public void btnEditData(View view){

        String title = txTitle.getText().toString().trim();
        String year = txYear.getText().toString().trim();
        String genre = txGenre.getText().toString().trim();
        String episode = txEpisode.getText().toString().trim();
        String actor = txActor.getText().toString().trim();
        String sinopsis = txSinopsis.getText().toString().trim();

        Boolean res = validasi(title, year,  genre, episode, actor, sinopsis);
        if(res){
            MainActivity.dbHelper.editData(
                    oldTitle, title, Integer.parseInt(txYear.getText().toString()), genre,
                    Integer.parseInt(txEpisode.getText().toString()),
                    actor,sinopsis
            );
            Toast.makeText(EditActivity.this, "Berhasil edit data", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(),"isi semua data",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void editQuit(View view){

        finish();
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
}