package com.example.classhelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper 
{
	private static DatabaseHelper sDatabaseHelper;
	
	private static final String DATABASE_NAME = "ClassHelper9000.db";
	private static final int DATABASE_VERSION = 1;
	
	public static synchronized DatabaseHelper get(Context context)
	{
		if (sDatabaseHelper == null)
			sDatabaseHelper = new DatabaseHelper(context.getApplicationContext());
		return sDatabaseHelper;
	}
	private DatabaseHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// Create the module table.
		db.execSQL("create table module (" + 
					"_id integer primary key autoincrement," +
					"name varchar(100))");
		
		// Create the student table.
		db.execSQL("create table student (" + 
					"_id integer primary key autoincrement," + 
					"first_name varchar(100)," + 
					"last_name varchar(100)," +
					"module_id integer references module(_id))");
		
		// Create the course table.
		db.execSQL("create table course (" +
					"_id integer primary key autoincrement," + 
					"name varchar(100)," + 
					"module_id integer references module(_id))");
		
		// Create the test table.
		db.execSQL("create table test (" + 
					"_id integer primary key autoincrement," + 
					"name varchar(100)," + 
					"notes varchar(600)," + 
					"course_id integer references course(_id))");
		
		// Create the grade table.
		db.execSQL("create table grade (" + 
					"_id integer primary key autoincrement," + 
					"value integer," + 
					"student_id integer references student(_id)," + 
					"test_id integer references test(_id))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub

	}
}
