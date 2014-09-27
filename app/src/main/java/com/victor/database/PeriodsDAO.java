package com.victor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bchen on 9/27/14.
 */
public class PeriodsDAO {
    private SQLiteDatabase database;
    private AttendanceSQLiteHelper dbHelper;
    private String[] allColumns = {"_id", "name"};

    public PeriodsDAO(Context context) {
        dbHelper = new AttendanceSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Period> getAllPeriods() {
        List<Period> periods = new ArrayList<Period>();
        return periods;
    }
}
