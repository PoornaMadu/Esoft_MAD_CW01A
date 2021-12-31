package com.example.crudexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "todo";//db name
    private static final String TABLE_NAME = "todo";//db table name

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " +
                "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + STARTED + " TEXT,"
                + FINISHED + " TEXT" +
                " );";

//      todo(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,description TEXT,started TEXT,finished TEXT);
        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        //Drop older table if existed
        db.execSQL(DROP_TABLE_QUERY);
        //create table again
        onCreate(db);

    }

    public void addTodo(Todo todo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, todo.getTitle());
        contentValues.put(DESCRIPTION, todo.getDescription());
        contentValues.put(STARTED, todo.getStarted());
        contentValues.put(FINISHED, todo.getFinished());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();


    }
    public int countTodo() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public List<Todo> getAllTodo() {
        List<Todo> toDos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Todo kiyanne tdo kyna java class eka
                Todo toDo = new Todo();

                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDescription(cursor.getString(2));
                toDo.setStarted(cursor.getLong(3));
                toDo.setFinished(cursor.getLong(4));

                toDos.add(toDo);

            } while (cursor.moveToNext());
        }
        return toDos;
    }


    public void deleteToDo(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //todo
    public Todo getSingleTodo(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, TITLE, DESCRIPTION, STARTED, FINISHED}, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Todo toDo;
        if (cursor != null) {
            cursor.moveToFirst();
            toDo = new Todo(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)


            );
            return toDo;
        }
        return null;
    }

    // todo
    public int updateSingleTodo(Todo toDo){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, toDo.getTitle());
        contentValues.put(DESCRIPTION, toDo.getDescription());
        contentValues.put(STARTED, toDo.getStarted());
        contentValues.put(FINISHED, toDo.getFinished());

        int status=db.update(TABLE_NAME,contentValues,ID+" =?",new String[]{String.valueOf(toDo.getId())});

        db.close();
        return status;

    }
}






















































































