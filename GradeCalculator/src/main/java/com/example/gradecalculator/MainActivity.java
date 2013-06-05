package com.example.gradecalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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

public class MainActivity extends ListActivity {
  String [] testArray = {"TESTING1", "TESTING2", "TESTING3", "TESTING4", "TESTING5", "TESTING6", "TESTING7"};
  private ListView _semestersListView;
  private SimpleAdapter _simpleAdapter;
  private DatabaseAccessors _db;
  private String TAG = "GradeCalculator";
  private Integer CUR_SEQUENCE = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    _semestersListView = (ListView) findViewById(android.R.id.list);
    //this.deleteDatabase(DatabaseConstants.DATABASE_NAME);
    _db = new DatabaseAccessors(this);
    try
    {
      _db.open();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }


    _semestersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        HashMap<String, String> clickedSemester = (HashMap<String, String>) _semestersListView.getItemAtPosition(pos);
        String semesterId = clickedSemester.get("id");
        Log.i(TAG, "id " + semesterId);
      }
    });
    drawSemestersList();
  }

  public void drawSemestersList()
  {
    Log.i(TAG, "drawing list");

    _semestersListView.invalidateViews();
    List<SemestersTableRecord> semesters =  _db.getAllSemesters ();
    List<Map<String, String>> listData= new ArrayList<Map<String, String>>();
    for (SemestersTableRecord i : semesters)
    {
      String semester = "";
      semester += i.getId() + " " + i.getSequence() + " " + i.getName() + " " + i.getCredits() + " " + i.getGpa();
      Log.i(TAG, semester);
      Map<String, String> semesterItem = new HashMap<String, String>(3);
      semesterItem.put("name", i.getName());
      if(i.getCredits() > 0 && i.getGpa() > 0)
      {
        semesterItem.put("info", "Credits: " + i.getCredits() + "    GPA: " + i.getGpa());
      }
      else
      {
        semesterItem.put("info", "");
      }
      semesterItem.put("id", Integer.toString(i.getId()));
      CUR_SEQUENCE = i.getSequence();
      listData.add(semesterItem);
    }
    _simpleAdapter = new SimpleAdapter (this, listData,android.R.layout.simple_list_item_2,new String[] {"name", "info"}, new int[] {android.R.id.text1, android.R.id.text2});
    _semestersListView.setAdapter(_simpleAdapter);

  }
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId())
    {
      case R.id.action_settings:
        Log.i(TAG, "Menu clicked");
        break;
      case R.id.add_semester:
        Log.i(TAG, "Add clicked");
        addSemesterDialog();
        break;
      default:
        Log.i(TAG, "item ID" + item.getItemId());
    }
    return true;
  }

  public void addSemesterDialog()
  {
    /*final Dialog dialog = new Dialog(this);
    dialog.setContentView(R.layout.add_semester_dialog);
    dialog.setTitle("Add Semester");
    dialog.show();*/
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    builder.setTitle("Add Semester");
    builder.setView(inflater.inflate(R.layout.add_semester_dialog, null));
    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Dialog dialog = (Dialog) dialogInterface;
        EditText name = (EditText) dialog.findViewById(R.id.semesterNameEntry);
        EditText credits = (EditText) dialog.findViewById(R.id.semesterCreditsEntry);
        EditText gpa = (EditText) dialog.findViewById(R.id.semesterGPAEntry);
        Double newGpa = 0.0;
        Integer newCredits =0;
        if(name.getText().toString() == "")
        {
          Toast.makeText(getApplicationContext(),"Enter a name",Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, name.toString() + ": " + name.getText().toString());
        try{
           newGpa =  (gpa.getText().length() == 0) ? 0 : Double.parseDouble(gpa.getText().toString());
        }
        catch(Exception e)
        {
          Log.i(TAG, "Error parsing newGPA or newCredits");
          Toast.makeText(getApplicationContext(), "Invalid credits value", Toast.LENGTH_SHORT).show();
          credits.getText().clear();
          return;
        }
        try{
           newCredits = (credits.getText().length() == 0) ? 0 : Integer.parseInt(credits.getText().toString());
        }
        catch(Exception e)
        {
          Log.i(TAG, "Error parsing newCredits");
          Toast.makeText(getApplicationContext(), "Invalid gpa value", Toast.LENGTH_SHORT).show();
          gpa.getText().clear();
          return;
        }

        addSemesterToDb(name.getText().toString(), newGpa, newCredits);
        drawSemestersList();
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

  public void addSemesterToDb(String name, Double gpa, Integer credits )
  {
    try {
      //_db.deleteAllSemesters();
      // _db.addSemester("Testing", 3.0, 15);
      //_db.addSemester("Spring 2009", 3.5, 18);
      _db.addSemester(name, gpa, credits);
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
  }

}
