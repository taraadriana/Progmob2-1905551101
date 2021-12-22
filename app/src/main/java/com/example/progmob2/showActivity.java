package com.example.progmob2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class showActivity extends AppCompatActivity {
    protected Cursor cursor;
    TextView txTitle, txYear, txGenre, txEpisode, txActor, txSinopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        txTitle = findViewById(R.id.shTitle);
        txYear = findViewById(R.id.shYear);
        txGenre = findViewById(R.id.shgenre);
        txEpisode = findViewById(R.id.shEpisode);
        txActor = findViewById(R.id.shActor);
        txSinopsis = findViewById(R.id.shSinopsis);

        DbHelper dbHelper = new DbHelper(showActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_drama WHERE title = '" +
                getIntent().getStringExtra("title") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            txTitle.setText(cursor.getString(1).toString());
            txYear.setText(cursor.getString(2).toString());
            txGenre.setText(cursor.getString(3).toString());
            txEpisode.setText(cursor.getString(4).toString());
            txActor.setText(cursor.getString(5).toString());
            txSinopsis.setText(cursor.getString(6).toString());
        }
    }

    public void showQuit(View view){

        finish();
    }
}