package com.example.gradecalculator;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends ListActivity {
  String [] testArray = {"TESTING1", "TESTING2", "TESTING3", "TESTING4", "TESTING5", "TESTING6", "TESTING7"};
  private ListView _semestersListView;
  private SimpleAdapter _simpleAdapter;
  private DatabaseAccessors _db;
  private String TAG = "GradeCalculator";

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
    try {
      //_db.deleteAllSemesters();
     // _db.addSemester(2,"Testing", 3.0, 15);
     // _db.addSemester(1, "Spring 2009", 3.5, 18);
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
    List<SemestersTableRecord> semesters =  _db.getAllSemesters ();
    List<Map<String, String>> listData= new ArrayList<Map<String, String>>();
    for (SemestersTableRecord i : semesters)
    {
      String semester = "";
      semester += i.getId() + " " + i.getSequence() + " " + i.getName() + " " + i.getCredits() + " " + i.getGpa();
      Log.i(TAG, semester);
      Map<String, String> semesterItem = new HashMap<String, String>(3);
      semesterItem.put("name", i.getName());
      semesterItem.put("info", "Credits: " + i.getCredits() + "    GPA: " + i.getGpa());
      semesterItem.put("id", Integer.toString(i.getId()));
      listData.add(semesterItem);
    }
    _simpleAdapter = new SimpleAdapter (this, listData,android.R.layout.simple_list_item_2,new String[] {"name", "info"}, new int[] {android.R.id.text1, android.R.id.text2});
    _semestersListView.setAdapter(_simpleAdapter);
    _semestersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Log.i(TAG, "in onItemClick");
        Toast.makeText(getApplicationContext(), "item clicked", LENGTH_SHORT).show();
        HashMap<String, String> clickedSemester = (HashMap<String, String>) _semestersListView.getItemAtPosition(pos);
        String semesterId = clickedSemester.get("id");
        Log.i(TAG, "id " + semesterId);
      }
    });
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
      default:
        Log.i(TAG, "item ID" + item.getItemId());
    }
    return true;
  }

}
