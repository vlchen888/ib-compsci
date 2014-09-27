package com.victor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bchen on 9/27/14.
 */
public class AttendanceSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "attendance.db";
    public static final int DATABASE_VERSION = 1;

    public AttendanceSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_CREATE_PERIOD = "create table period (_id integer " +
            "primary key autoincrement, name text not null);";
    private static final String TABLE_CREATE_STUDENT = "create table student (_id integer " +
            "primary key autoincrement, first_name text, last_name text, period_id integer, status integer);";
    private static final String SQL_INIT_PERIOD = "insert into period (name) values ('Period %d');\n";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE_PERIOD);
        database.execSQL(TABLE_CREATE_STUDENT);
        for (int i = 0; i < 7; i++ ) {
            database.execSQL(String.format(SQL_INIT_PERIOD, i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists period;\ndrop table if exists student;");
    }
}