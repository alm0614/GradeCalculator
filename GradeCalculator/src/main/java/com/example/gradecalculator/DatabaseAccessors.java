package com.example.gradecalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 5/23/13.
 */
public class DatabaseAccessors {
  private SQLiteDatabase db;
  private MySQLiteDBHelper dbHelper;
  private String[] semestersColumns = {DatabaseConstants.COLUMN_ID,
                                       DatabaseConstants.Semesters.Sequence,
                                       DatabaseConstants.Semesters.Name,
                                       DatabaseConstants.Semesters.GPA,
                                       DatabaseConstants.Semesters.Credits};

  private String[] coursesColumns = {DatabaseConstants.COLUMN_ID,
                                     DatabaseConstants.Courses.SemesterId,
                                     DatabaseConstants.Courses.Name,
                                     DatabaseConstants.Courses.Credits,
                                     DatabaseConstants.Courses.Grade};

  private String[] gradingScaleColumns = {DatabaseConstants.COLUMN_ID,
                                          DatabaseConstants.GradingScale.Name,
                                          DatabaseConstants.GradingScale.Value};

  public DatabaseAccessors(Context context) {
    dbHelper = new MySQLiteDBHelper(context);
  }

  public void open() throws SQLException {
    db = dbHelper.getWritableDatabase();
  }

  //inital get all requests
  public List<SemestersTableRecord> getAllSemesters() {
    List<SemestersTableRecord> semesters = new ArrayList<SemestersTableRecord>();

    Cursor cursor = db.query(DatabaseConstants.Semesters.TABLE_NAME, semestersColumns,null,null,null,null,null);
    cursor.moveToFirst();
    while(!cursor.isAfterLast())
    {
      SemestersTableRecord semester = cursorToSemester(cursor);
      semesters.add(semester);
      cursor.moveToNext();
    }
    cursor.close();
    return semesters;

  }

  public List<CoursesTableRecord> getAllCoursesForSemester(int semester_id){
    List<CoursesTableRecord> courses = new ArrayList<CoursesTableRecord>();
    Cursor cursor = db.query(DatabaseConstants.Courses.TABLE_NAME, coursesColumns, null,null,null,null,null);
    cursor.moveToFirst();
    while(!cursor.isAfterLast())
    {
      CoursesTableRecord course = cursorToCourse(cursor);
      courses.add(course);
      cursor.moveToNext();
    }
    cursor.close();
    return courses;
  }

  public List<GradingScaleTableRecord> getGradingScale(){
    List<GradingScaleTableRecord> gradingScales = new ArrayList<GradingScaleTableRecord>();
    Cursor cursor = db.query(DatabaseConstants.GradingScale.TABLE_NAME, gradingScaleColumns,null,null,null,null,null);
    cursor.moveToFirst();
    while(!cursor.isAfterLast()){
      GradingScaleTableRecord gradingScale = cursorToGradingScale(cursor);
      gradingScales.add(gradingScale);
      cursor.moveToNext();
    }
    cursor.close();
    return gradingScales;
  }

  //add requests
  public SemestersTableRecord addSemester(Integer seq, String name, Double gpa, Integer credits) {
    ContentValues values = new ContentValues();
    values.put(DatabaseConstants.Semesters.Sequence,seq );
    values.put(DatabaseConstants.Semesters.Name, name);
    if(gpa > 0)
    {
      values.put(DatabaseConstants.Semesters.GPA, gpa);
    }
    if(credits > 0)
    {
      values.put(DatabaseConstants.Semesters.Credits, credits);
    }
    long insertId = db.insert(DatabaseConstants.Semesters.TABLE_NAME,null,values);
    Cursor cursor = db.query(DatabaseConstants.Semesters.TABLE_NAME, semestersColumns, DatabaseConstants.COLUMN_ID + " = " + insertId,null,null,null,null);
    cursor.moveToFirst();
    SemestersTableRecord newSemester = cursorToSemester(cursor);
    cursor.close();
    return newSemester;
  }

  public void deleteAllSemesters()
  {
    db.delete(DatabaseConstants.Semesters.TABLE_NAME,null,null);
  }
  public CoursesTableRecord addCourse(Integer semester_id, String name, Double grade, Integer credits){
    ContentValues values = new ContentValues();
    values.put(DatabaseConstants.Courses.SemesterId, semester_id);
    values.put(DatabaseConstants.Courses.Name, name);
    values.put(DatabaseConstants.Courses.Grade, grade);
    values.put(DatabaseConstants.Courses.Credits, credits);
    long insertId = db.insert(DatabaseConstants.Courses.TABLE_NAME, null, values);
    Cursor cursor = db.query(DatabaseConstants.Courses.TABLE_NAME, coursesColumns, DatabaseConstants.COLUMN_ID + " = " + insertId,null,null,null,null);
    cursor.moveToFirst();
    CoursesTableRecord newCourse = cursorToCourse(cursor);
    cursor.close();
    return newCourse;
  }

  public GradingScaleTableRecord addGradingScale(String name, Double value){
    ContentValues values = new ContentValues();
    values.put(DatabaseConstants.GradingScale.Name, name);
    values.put(DatabaseConstants.GradingScale.Value, value);
    long insertId = db.insert(DatabaseConstants.GradingScale.TABLE_NAME, null, values);
    Cursor cursor = db.query(DatabaseConstants.GradingScale.TABLE_NAME, coursesColumns, DatabaseConstants.COLUMN_ID + " = " + insertId,null,null,null,null);
    cursor.moveToFirst();
    GradingScaleTableRecord newGradingScale = cursorToGradingScale(cursor);
    cursor.close();
    return newGradingScale;
  }

  //update requests
 /* public SemestersTableRecord updateSemester() {

  }

  public CoursesTableRecord updateCourse(){

  }

  public GradingScaleTableRecord updateGradingScale(){

  }*/
  private SemestersTableRecord cursorToSemester(Cursor cursor){
    SemestersTableRecord semester = new SemestersTableRecord();
    semester.setId(cursor.getInt(0));
    semester.setSequence(cursor.getInt(1));
    semester.setName(cursor.getString(2));
    semester.setGpa(cursor.getDouble(3));
    semester.setCredits(cursor.getInt(4));
    return semester;
  }

  private CoursesTableRecord cursorToCourse(Cursor cursor){
    CoursesTableRecord course = new CoursesTableRecord();
    course.setId(cursor.getInt(0));
    course.setSemester_id(cursor.getInt(1));
    course.setName(cursor.getString(2));
    course.setGrade(cursor.getDouble(3));
    course.setCredits(cursor.getInt(4));
    return course;
  }

  private GradingScaleTableRecord cursorToGradingScale(Cursor cursor){
    GradingScaleTableRecord gradingScale = new GradingScaleTableRecord();
    gradingScale.setId(cursor.getInt(0));
    gradingScale.setName(cursor.getString(1));
    gradingScale.setValue(cursor.getDouble(2));
    return gradingScale;
  }
}
