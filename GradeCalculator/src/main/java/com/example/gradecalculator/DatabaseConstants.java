package com.example.gradecalculator;


import android.provider.BaseColumns;

/**
 * Created by Andrew on 5/20/13.
 */
public class DatabaseConstants {

  private DatabaseConstants() {} //prevent copying
  public static final String DATABASE_NAME = "gradecalc.db";
  public static final Integer DATABASE_VERSION = 1;
  public static final String COLUMN_ID = "_id";
  /** table name and column definitions  **/
  public static abstract class Semesters implements BaseColumns{
    public static final String TABLE_NAME = "semesters";
    public static final String Sequence = "sequence";
    public static final String Name = "name";
    public static final String GPA = "gpa";
    public static final String Credits = "credits";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
        "(" +
        COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        Sequence   + " INTEGER NOT NULL, " +
        Name       + " TEXT NOT NULL, " +
        GPA        + " REAL, " +
        Credits    + " INTEGER " +
        ")";
  }

  public static abstract class Courses implements BaseColumns{
    public static final String TABLE_NAME = "courses";
    public static final String SemesterId = "semesterid";
    public static final String Name = "name";
    public static final String Credits = "credits";
    public static final String Grade = "grade";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
        "(" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "FOREIGN KEY("+SemesterId+")" + " REFERENCES semesters(semesterid), " +
        Name     + " TEXT NOT NULL, " +
        Grade    + " REAL NOT NULL, " +
        Credits  + " INTEGER NOT NULL" +
        ")";
  }

  public static abstract class GradingScale implements BaseColumns{
    public static final String TABLE_NAME = "gradingScale";
    public static final String Name = "name";
    public static final String Value = "value";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
        "(" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
        Name      + " TEXT PRIMARY KEY, " +
        Value     + " REAL NOT NULL" +
        ")";

  }
  //TODO create table for storing different categories for graded assignments
}

