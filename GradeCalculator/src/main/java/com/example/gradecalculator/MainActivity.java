package com.example.gradecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
  String [] testArray = {"TESTING1", "TESTING2", "TESTING3", "TESTING4", "TESTING5", "TESTING6", "TESTING7"};
  private ListView _testListView;
  private ArrayAdapter _arrayAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    _testListView = (ListView) findViewById(R.id.semesterListView);
    _arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, testArray);
    _testListView.setAdapter(_arrayAdapter);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId())
    {
      case R.id.action_settings:
        Log.i("GradeCalculator", "Menu clicked");
        break;
      default:
        Log.i("GradeCalculator", "item ID" + item.getItemId());
    }
    return true;
  }

}
