package com.example.progmob2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FloatingActionButton btnNewList;
    String mainUser;
    RecyclerView recyclerView;
    ImageView emptyImage;
    TextView emptytext;
    private CustomAdapter customAdapter;
    private AlertDialog.Builder dialog;
    static DbHelper dbHelper;
    ArrayList<Drama> dramaArrayList;
    public Drama dramakosong = new Drama("a","a","a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        tabLayout = findViewById(R.id.tab_layout);
        btnNewList = findViewById(R.id.btnNew);
        emptyImage = findViewById(R.id.empty_image);
        emptytext = findViewById(R.id.empty_text);

        Bundle login = getIntent().getExtras();
        mainUser = login.getString("UserNameLogin");
        btnNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DbHelper(MainActivity.this);
        dramaArrayList = new ArrayList<Drama>();
        dialog = new AlertDialog.Builder(this);

        storeData();

        customAdapter = new CustomAdapter(MainActivity.this, dramaArrayList);
        customAdapter.setDialog(new CustomAdapter.Dialog() {
            @Override
            public void onClick(int position){
                final CharSequence[] dialogItem = {"edit", "view", "delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(MainActivity.this,
                                        EditActivity.class);
                                intent.putExtra("title", dramaArrayList.get(position).getDramaTitle());
                                startActivity(intent);
                                break;

                            case 1:
                                Intent intent1 = new Intent(MainActivity.this,
                                        showActivity.class);
                                intent1.putExtra("title", dramaArrayList.get(position).getDramaTitle());
                                startActivity(intent1);
                                break;

                            case 2:
                                dialog.setMessage("do you want to delete this data?")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dbHelper.deleteData(dramaArrayList.get(position).getDramaTitle());
                                                onStart();
                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });

                                AlertDialog alertDialog = dialog.create();
                                alertDialog.setTitle("Delete Data");
                                alertDialog.show();
                                onStart();
                        }
                    }
                });
                dialog.show();
            }
        });

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void storeData(){
        Cursor cursor = dbHelper.readData();
        String testerId, testerTitle, testerYear;
        if(cursor.getCount() == 0){
            emptyImage.setVisibility(View.VISIBLE);
            emptytext.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                testerId = String.valueOf(cursor.getString(0));
                testerTitle = String.valueOf(cursor.getString(1));
                testerYear = String.valueOf(cursor.getString(2));
                dramakosong = new Drama(testerId, testerTitle, testerYear);
                dramaArrayList.add(dramakosong);
            }
            emptyImage.setVisibility(View.GONE);
            emptytext.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.account) {
            Toast.makeText(MainActivity.this,"Account",Toast.LENGTH_SHORT);
            Intent intent = new Intent(MainActivity.this,AccountActivity.class);
            intent.putExtra("MainName", mainUser);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.logout) {
            Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_LONG);
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        dramaArrayList.clear();
        storeData();

        //kirim notif ke adapter
        customAdapter.notifyDataSetChanged();
    }
}