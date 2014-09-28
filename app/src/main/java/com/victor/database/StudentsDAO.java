package com.victor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bchen on 9/27/14.
 */
public class StudentsDAO {
    private SQLiteDatabase database;
    private AttendanceSQLiteHelper dbHelper;
    private String[] allColumns = {"_id", "first_name", "last_name", "period_id", "status"};

    public StudentsDAO(Context context) {
        dbHelper = new AttendanceSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Student> getStudentsForPeriodId(long periodId) {
        List<Student> periods = new ArrayList<Student>();
        Cursor cursor = database.query("student", allColumns, "period_id = ?",
                new String[]{String.valueOf(periodId)}, null, null, "last_name");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            periods.add(cursorToStudent(cursor));
            cursor.moveToNext();
        }
        return periods;
    }

    public Student addStudentToPeriod(Student aStudent, long periodId) {
        ContentValues values = new ContentValues();
        values.put("first_name", aStudent.getfName());
        values.put("last_name", aStudent.getlName());
        if (aStudent.getStatus() == null) {
            values.put("status", 0);
        } else {
            values.put("status", aStudent.getStatus().ordinal());
        }
        values.put("period_id", periodId);
        long insertId = database.insert("student", null, values);
        return getStudentById(insertId);
    }

    public Student getStudentById(long id) {
        Cursor mCursor = database.query("student",
                allColumns,
                "_id = ?",
                new String[]{String.valueOf(id)},
                null, null, null);
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            return cursorToStudent(mCursor);
        } else {
            return null;
        }
    }

    public void updateStudent(Student aStudent) {
        ContentValues values = new ContentValues();
        if (aStudent.getStatus() == null) {
            values.put("status", 0);
        } else {
            values.put("status", aStudent.getStatus().ordinal());
        }
        database.update("student", values, "_id = ?", new String[]{String.valueOf(aStudent.getId())});
    }

    private Student cursorToStudent(Cursor aCursor) {
        Student aStudent = new Student();
        aStudent.setId(aCursor.getLong(0));
        aStudent.setfName(aCursor.getString(1));
        aStudent.setlName(aCursor.getString(2));
        aStudent.setPeriodId(aCursor.getLong(3));
        aStudent.setStatus(Student.AttendanceStatus.fromOrdinal(aCursor.getInt(4)));
        return aStudent;
    }
}
