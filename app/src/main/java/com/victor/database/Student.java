package com.victor.database;

/**
 * Created by bchen on 9/27/14.
 */
public class Student {

    public enum AttendanceStatus {
        PRESENT, TARDY, ABSENT
    }

    private String fName;
    private String lName;
    private AttendanceStatus status;

    public Student(String f, String l) {
        fName = f;
        lName = l;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String s =  lName + ", " + fName + " ";
        switch(status) {
            case PRESENT:
                return s+"P";
            case TARDY:
                return s+"T";
            case ABSENT:
                return s+"A";
        }

        return ""; //never reached
    }
}
