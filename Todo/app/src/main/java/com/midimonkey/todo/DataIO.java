package com.midimonkey.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataIO extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "todoDB.db";
    public static final String TASKS_TABLE_NAME = "taskTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "description";

    public DataIO(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TASKS_TABLE_NAME
                        + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_TASK + " TEXT"
                        + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        onCreate(db);
    }

    public void addTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK, task.getDescription());
        db.insert(TASKS_TABLE_NAME, null, contentValues);
    }

    public void editTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_TASK, task.getDescription());
        db.update(TASKS_TABLE_NAME,
                newValues,
                COLUMN_ID + " = ?",
                new String[]{Integer.toString(task.getId())});
    }

    public void deleteTask (Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS_TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(task.getId())});
    }

    public Task getTask(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + Integer.toString(id), null);
        cursor.moveToFirst();
        Task task = new Task(cursor.getInt(0), cursor.getString(1));
        return task;
    }

    public Cursor getCursor()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);
        return cursor;
    }

    public ArrayList<Task> getAllTasks()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);
        ArrayList<Task> favors = new ArrayList<>();
        Task favor = null;
        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                favor = new Task(cursor.getInt(0), cursor.getString(1));
                favors.add(favor);
            } while (cursor.moveToNext());
        }
        db.close();
        return favors;
    }

    public void close()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }
}