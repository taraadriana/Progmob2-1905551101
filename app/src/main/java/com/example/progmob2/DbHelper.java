package com.example.progmob2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;

    public static final String database_name = "db_drama";

    public static final String table_name = "tb_drama";
    public static final String row_id = "_id";
    public static final String row_title = "title";
    public static final String row_year = "year";
    public static final String row_genre = "genre";
    public static final String row_episode = "episode";
    public static final String row_actor = "actor";
    public static final String row_sinopsis = "sinopsis";

    public static final String table_name1 = "tb_user";
    public static final String id_user = "_id";
    public static final String row_name = "name";
    public static final String row_email = "email";
    public static final String row_username = "username";
    public static final String row_password = "password";
    public static final String row_age = "age";
    public static final String row_gender = "gender";
    public static final String row_hobby = "hobby";

    private SQLiteDatabase database;

    public DbHelper(Context context) {
        super(context, database_name, null, 2);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String queryDrama = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_title + " TEXT," + row_year + " INTEGER," + row_genre + " TEXT,"
                + row_episode + " INTEGER," + row_actor + " TEXT," + row_sinopsis + " TEXT)";

        String queryUser = "CREATE TABLE " + table_name1 + "(" + id_user + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_name + " TEXT," + row_email + " TEXT," + row_username + " TEXT,"
                + row_password + " TEXT," + row_age + " INTEGER," + row_gender + " TEXT,"
                + row_hobby + " TEXT)";

        database.execSQL(queryDrama);
        database.execSQL(queryUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + table_name);
        database.execSQL("DROP TABLE IF EXISTS " + table_name1);

        onCreate(database);
    }

    public void insertData(String title, int year, String genre, int episode,
                           String actor, String sinopsis) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(row_title, title);
        cv.put(row_year, year);
        cv.put(row_genre, genre);
        cv.put(row_episode, episode);
        cv.put(row_actor, actor);
        cv.put(row_sinopsis, sinopsis);

        db.insert(table_name, null, cv);
    }

    public void insertDataUser(ContentValues values) {

        database.insert(table_name1, null, values);
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {id_user};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(table_name1, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        if (count > 0)
            return true;
        else
            return false;
    }

    public Cursor readData() {
        String query = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void editData(String titleOld, String title, int year,
                            String genre, int episode, String actor, String sinopsis) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(row_title, title);
        values.put(row_year, year);
        values.put(row_genre, genre);
        values.put(row_episode, episode);
        values.put(row_actor, actor);
        values.put(row_sinopsis, sinopsis);
        db.update(table_name, values, "title=?", new String[]{titleOld});
        db.close();
    }

    public void deleteData(String titleOld) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table_name,"title=?", new String[]{titleOld});
        db.close();
    }
}

