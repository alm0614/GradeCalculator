package com.example.gradecalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
  public MySQLiteHelper(Context context){
    super(context,DatabaseConstants.DATABASE_NAME,null, DatabaseConstants.DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(DatabaseConstants.Semesters.CREATE_TABLE);
    sqLiteDatabase.execSQL(DatabaseConstants.Courses.CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + i + " to " + i2);
  }
}
