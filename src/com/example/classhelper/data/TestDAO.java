package com.example.classhelper.data;

import java.util.ArrayList;

import com.example.classhelper.model.Course;
import com.example.classhelper.model.Test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This singleton class uses DatabaseHelper in order to interact 
 * with Test table.
 */
public class TestDAO 
{
	private static TestDAO sTestDAO;
	private Context mAppContext;
	
	private static final String TABLE_TEST = "test";
	private static final String COLUMN_TEST_ID = "_id";
	private static final String COLUMN_TEST_NAME = "name";
	private static final String COLUMN_TEST_NOTES = "notes";
	private static final String COLUMN_TEST_COURSE_ID = "course_id";
	
	public static TestDAO get(Context context)
	{
		if (sTestDAO == null)
			sTestDAO = new TestDAO(context);
		return sTestDAO;
	}
	
	private TestDAO(Context context)
	{
		mAppContext = context;
	}
	
	public long insert(Test test)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_TEST_NAME, test.getName());
		values.put(COLUMN_TEST_NOTES, test.getNotes());
		values.put(COLUMN_TEST_COURSE_ID, test.getCourse().getId());
		return DatabaseHelper.get(mAppContext)
				.getWritableDatabase().insert(TABLE_TEST, null, values);
	}
	
	public void delete(Test test)
	{
		String selection = COLUMN_TEST_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(test.getId())};
		DatabaseHelper.get(mAppContext).getWritableDatabase()
			.delete(TABLE_TEST, selection, selectionArgs);
	}
	
	public int update(Test test)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_TEST_NAME, test.getName());
		values.put(COLUMN_TEST_NOTES, test.getNotes());
		values.put(COLUMN_TEST_COURSE_ID, test.getCourse().getId());
		
		String selection = COLUMN_TEST_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(test.getId())};
		return DatabaseHelper.get(mAppContext)
				.getReadableDatabase()
				.update(TABLE_TEST, values, selection, selectionArgs);
	}
	
	public Test getTestById(long testId)
	{
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_TEST, 
				null, // all columns.
				COLUMN_TEST_ID + " = ?", // look for test id
				new String[]{String.valueOf(testId)}, // with this value
				null, // group by
				null, // having
				null, // order by
				"1"); // limit 1 row
		if (cursor != null)
			cursor.moveToFirst();
		
		Test test = cursorToTest(cursor);
		return test;
	}
	
	public Test getTestByName(String testName)
	{
		Test test = null;
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_TEST, 
				null, // all columns.
				COLUMN_TEST_NAME + " = ?", // look for test name
				new String[]{testName}, // with this value
				null, // group by
				null, // having
				null, // order by
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			test = cursorToTest(cursor);
		
		return test;
	}
	
	public ArrayList<Test> getAllTests()
	{
		ArrayList<Test> tests = new ArrayList<Test>();
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_TEST, 
				null, 
				null, 
				null, 
				null, 
				null, 
				COLUMN_TEST_COURSE_ID + " asc, " + COLUMN_TEST_NAME + " asc");
		if (cursor != null)
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				Test test = cursorToTest(cursor);
				tests.add(test);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		return tests;
	}
	
	protected Test cursorToTest(Cursor cursor)
	{
		Test test = new Test();
		test.setId(cursor.getLong(0));
		test.setName(cursor.getString(1));
		test.setNotes(cursor.getString(2));
		
		long courseId = cursor.getLong(3);
		Course course = CourseDAO.get(mAppContext).getCourseById(courseId);
		if (course != null)
			test.setCourse(course);

		return test;
	}
}
