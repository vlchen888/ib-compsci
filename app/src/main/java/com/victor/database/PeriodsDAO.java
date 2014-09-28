package com.victor.database;

import android.content.Context;
import android.database.Cursor;
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
        Cursor cursor = database.query("period", allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            periods.add(cursorToPeriod(cursor));
            cursor.moveToNext();
        }
        return periods;
    }

    public Period getPeriodForId(long id) {
        Cursor mCursor = database.query("period",
                allColumns,
                "_id = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            return cursorToPeriod(mCursor);
        } else {
            return null;
        }
    }

    private Period cursorToPeriod(Cursor cursor) {
        Period period = new Period();
        period.setId(cursor.getLong(0));
        period.setName(cursor.getString(1));
        return period;
    }
}
