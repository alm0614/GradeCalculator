package com.example.gradecalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends ListActivity {
  private ListView _coursesListView;
  private SimpleAdapter _simpleAdapter;
  private DatabaseAccessors _db;
  private String _semesterId;
  private String TAG = "GradeCalculator";
  private TextView _currentGpaLabel;
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_courses);
    _coursesListView = (ListView) findViewById(android.R.id.list);
    _currentGpaLabel = (TextView) findViewById(R.id.currentGPA);
    //this.deleteDatabase(DatabaseConstants.DATABASE_NAME);
    Intent i = getIntent();
    _semesterId = i.getStringExtra("SEMESTERID");
    Log.i(TAG, "RECEIVED SEMESTER ID:" + _semesterId);
    _db = new DatabaseAccessors(this);
    try
    {
      _db.open();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }


    _coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        //HashMap<String, String> clickedSemester = (HashMap<String, String>) _coursesListView.getItemAtPosition(pos);
        //String semesterId = clickedSemester.get("id");
      }
    });

    _coursesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
        HashMap<String, String> clickedCourse = (HashMap<String, String>) _coursesListView.getItemAtPosition(pos);
        String courseId = clickedCourse.get("id");
        String courseName = clickedCourse.get("name");
        confirmDelete(courseId, courseName);
        return true;
      }
    });
    drawCoursesList();
    calculateSemesterGPA();
  }

  public void confirmDelete(final String id, final String name)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Are you sure you want to delete " + name)
            .setTitle("Delete Course")
            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
               _db.deleteCourse(Integer.parseInt(_semesterId), Integer.parseInt(id));
                drawCoursesList();
                Log.i(TAG, "will delete "+ name);
              }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
              }
            });

    builder.show();
  }
  public void drawCoursesList()
  {
    Log.i(TAG, "drawing list");

    _coursesListView.invalidateViews();
    List<CoursesTableRecord> courses =  _db.getAllCoursesForSemester(_semesterId);
    List<Map<String, String>> listData= new ArrayList<Map<String, String>>();
    for (CoursesTableRecord i : courses)
    {
      String course = "";
      course = i.getId() + " " + i.getName() + " " + i.getCredits() + " " + i.getGrade();
      Log.i(TAG, course);
      Map<String, String> courseItem = new HashMap<String, String>(3);
      courseItem.put("name", i.getName());
      if(i.getGrade() > 0)
      {
        courseItem.put("info", "Credits: " + i.getCredits() + "    Grade: " + i.getGrade());
      }
      else
      {
        courseItem.put("info","Credits: " + i.getCredits() );
      }
      courseItem.put("id", Integer.toString(i.getId()));
      listData.add(courseItem);
    }
    _simpleAdapter = new SimpleAdapter (this, listData,android.R.layout.simple_list_item_2,new String[] {"name", "info"}, new int[] {android.R.id.text1, android.R.id.text2});
    _coursesListView.setAdapter(_simpleAdapter);

  }
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.courses, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId())
    {
      case R.id.action_settings:
        Log.i(TAG, "Menu clicked");
        break;
      case R.id.add_course:
        Log.i(TAG, "Add clicked");
        addCourseDialog();
        break;
      default:
        Log.i(TAG, "item ID" + item.getItemId());
    }
    return true;
  }

  public void addCourseDialog()
  {
    /*final Dialog dialog = new Dialog(this);
    dialog.setContentView(R.layout.add_semester_dialog);
    dialog.setTitle("Add Semester");
    dialog.show();*/
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    builder.setTitle("Edit Course");
    builder.setView(inflater.inflate(R.layout.add_course_dialog, null));

    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Dialog dialog = (Dialog) dialogInterface;
        EditText name = (EditText) dialog.findViewById(R.id.courseNameEntry);
        EditText credits = (EditText) dialog.findViewById(R.id.courseCreditsEntry);
        EditText gpa = (EditText) dialog.findViewById(R.id.courseGPAEntry);
        Double newGpa;
        Integer newCredits;
        if(name.getText().toString() == "")
        {
          Toast.makeText(getApplicationContext(),"Enter a name", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, name.toString() + ": " + name.getText().toString());
        try{
           newGpa =  (gpa.getText().length() == 0) ? 0 : Double.parseDouble(gpa.getText().toString());
        }
        catch(Exception e)
        {
          Log.i(TAG, "Error parsing newGPA ");
          Toast.makeText(getApplicationContext(), "Invalid Grade Value", Toast.LENGTH_SHORT).show();
          credits.getText().clear();
          return;
        }
        try{
           newCredits = Integer.parseInt(credits.getText().toString());
        }
        catch(Exception e)
        {
          Log.i(TAG, "Error parsing newCredits");
          Toast.makeText(getApplicationContext(), "Invalid Credits Value", Toast.LENGTH_SHORT).show();
          gpa.getText().clear();
          return;


        }
        addCourseToDb(name.getText().toString(), newGpa, newCredits);
        drawCoursesList();
      }
    });

    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        //do nothing
      }
    });
    builder.show();

  }

  public void addCourseToDb(String name, Double gpa, Integer credits )
  {
    Log.i(TAG, "NAME: " + name + " " +  " GPA: " + gpa + " " + " CREDITS: " + credits);
    try {
      _db.addCourse(Integer.parseInt(_semesterId), name , gpa, credits);
    }
    catch (android.database.sqlite.SQLiteConstraintException e) {
      Log.e(TAG, "SQLiteConstraintException:" + e.getMessage());
    }
    catch (android.database.sqlite.SQLiteException e) {
      Log.e(TAG, "SQLiteException:" + e.getMessage());
    }
    catch (Exception e) {
      Log.e(TAG, "Exception:" + e.getMessage());
    }
    calculateSemesterGPA();
  }

  public void calculateSemesterGPA()
  {
    Double gpa = _db.calculateSemesterGPA(_semesterId);
    if(gpa.isNaN())
    {
      _currentGpaLabel.setText("0.0");
    }
    else
    {
      _currentGpaLabel.setText(gpa.toString());
    }

  }

}
