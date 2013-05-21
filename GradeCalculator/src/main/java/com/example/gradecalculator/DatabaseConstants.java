package com.example.gradecalculator;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Andrew on 5/20/13.
 */
public class DatabaseConstants {

  private DatabaseConstants() {} //prevent copying
  public static final String DATABASE_NAME = "gradecalc.db";
  public static final Integer DATABASE_VERSION = 1;
  /** table name and column definitions  **/
  public static abstract class Semesters implements BaseColumns{
    public static final String TABLE_NAME = "semesters";
    public static final String ID = "semesterid";
    public static final String Sequence = "sequence";
    public static final String Name = "name";
    public static final String GPA = "gpa";
    public static final String Credits = "credits";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
        "(" + ID + " integer primary key autoincrement, " +
        Sequence + " integer not null, " +
        Name     + " text not null, " +
        GPA      + " real, " +
        Credits  + " real)";
  }

  public static abstract class Courses implements BaseColumns{
    public static final String TABLE_NAME = "courses";
    public static final String ID = "courseId";
    public static final String SemesterId = "semesterid";
    public static final String Name = "name";
    public static final String Credits = "credits";
    public static final String Grade = "grade";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME +
        "(" + ID + " integer primary key autoincrement, " +
        "FOREIGN KEY("+SemesterId+")" + " REFERENCES semesters(semesterid), " +
        Name     + " TEXT NOT NULL, " +
        Grade    + " REAL NOT NULL, " +
        Credits  + " REAL NOT NULL)";
  }
}

