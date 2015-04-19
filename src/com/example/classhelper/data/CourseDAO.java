package com.example.classhelper.data;

import java.util.ArrayList;

import com.example.classhelper.model.Course;
import com.example.classhelper.model.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CourseDAO 
{
	private static CourseDAO sCourseDAO;
	private Context mAppContext;
	
	private static final String TABLE_COURSE = "course";
	private static final String COLUMN_COURSE_ID = "_id";
	private static final String COLUMN_COURSE_NAME = "name";
	private static final String COLUMN_COURSE_MODULE_ID = "module_id";
	
	public static CourseDAO get(Context context)
	{
		if (sCourseDAO == null)
			sCourseDAO = new CourseDAO(context);
		return sCourseDAO;
	}
	
	private CourseDAO(Context context)
	{
		mAppContext = context;
	}
	
	public long insert(Course course)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_COURSE_NAME, course.getName());
		values.put(COLUMN_COURSE_MODULE_ID, course.getModule().getId());
		return DatabaseHelper.get(mAppContext).getWritableDatabase().insert(TABLE_COURSE, null, values);
	}
	
	public void delete(Course course)
	{
		String selection = COLUMN_COURSE_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(course.getId())};
		DatabaseHelper.get(mAppContext).getWritableDatabase()
			.delete(TABLE_COURSE, selection, selectionArgs);
	}
	
	public int update(Course course)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_COURSE_NAME, course.getName());
		values.put(COLUMN_COURSE_MODULE_ID, course.getModule().getId());
		
		String selection = COLUMN_COURSE_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(course.getId())};
		return DatabaseHelper.get(mAppContext).getReadableDatabase().update(TABLE_COURSE, values, selection, selectionArgs);
	}
	
	public Course getCourseById(long courseId)
	{
		Course course = null;
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_COURSE, 
				null, // all columns.
				COLUMN_COURSE_ID + " = ?", // look for module id
				new String[]{String.valueOf(courseId)}, // with this value
				null, // group by
				null, // order by
				null, // having
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			course = cursorToCourse(cursor);
		
		return course;
	}
	
	public Course getCourseByName(String courseName)
	{
		Course course = null;
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_COURSE, 
				null, // all columns.
				COLUMN_COURSE_NAME + " = ?", // look for module id
				new String[]{courseName}, // with this value
				null, // group by
				null, // order by
				null, // having
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			course = cursorToCourse(cursor);
		
		return course;
	}
	
	public ArrayList<Course> getAllCourses()
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_COURSE, 
				null, 
				null, 
				null, 
				null, 
				null, 
				COLUMN_COURSE_NAME + " asc");
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Course course = cursorToCourse(cursor);
				courses.add(course);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		return courses;
	}
	
	protected Course cursorToCourse(Cursor cursor)
	{
		Course course = new Course();
		course.setId(cursor.getLong(0));
		course.setName(cursor.getString(1));
		
		long moduleId = cursor.getLong(2);
		Module module = ModuleDAO.get(mAppContext).getModuleById(moduleId);
		if (module != null)
			course.setModule(module);

		return course;
	}
}
