package com.example.gradecalculator;

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
  private String[] semestersColumns = {DatabaseConstants.Semesters.ID,
                                       DatabaseConstants.Semesters.Sequence,
                                       DatabaseConstants.Semesters.Name,
                                       DatabaseConstants.Semesters.GPA,
                                       DatabaseConstants.Semesters.Credits};

  private String[] coursesColumns = {DatabaseConstants.Courses.ID,
                                     DatabaseConstants.Courses.SemesterId,
                                     DatabaseConstants.Courses.Name,
                                     DatabaseConstants.Courses.Credits,
                                     DatabaseConstants.Courses.Grade};

  private String[] gradingScaleColumns = {DatabaseConstants.GradingScale.Name,
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
  public SemestersTableRecord addSemester(SemestersTableRecord) {

  }

  public CoursesTableRecord addCourse(CoursesTableRecord){

  }

  public GradingScaleTableRecord addGradingScale(GradingScaleTableRecord){

  }

  //update requests
  public SemestersTableRecord updateSemester(SemestersTableRecord) {

  }

  public CoursesTableRecord updateCourse(CoursesTableRecord){

  }

  public GradingScaleTableRecord updateGradingScale(GradingScaleTableRecord){

  }

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
    gradingScale.setName(cursor.getString(0));
    gradingScale.setValue(cursor.getDouble(1));
    return gradingScale;
  }
}
