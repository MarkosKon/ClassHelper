package com.example.classhelper.data;

import java.util.ArrayList;

import com.example.classhelper.model.Module;
import com.example.classhelper.model.Student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This singleton class uses DatabaseHelper in order to interact 
 * with Student table.
 */
public class StudentDAO 
{
	private static StudentDAO sStudentDAO;
	private Context mAppContext;
	
	private static final String TABLE_STUDENT = "student";
	private static final String COLUMN_STUDENT_ID = "_id";
	private static final String COLUMN_STUDENT_FIRST_NAME = "first_name";
	private static final String COLUMN_STUDENT_LAST_NAME = "last_name";
	private static final String COLUMN_STUDENT_PHONE_NUMBER = "phone_number";
	private static final String COLUMN_STUDENT_EMAIL = "email";
	private static final String COLUMN_STUDENT_MODULE_ID = "module_id";
	
	public static StudentDAO get(Context context)
	{
		if (sStudentDAO == null)
			sStudentDAO = new StudentDAO(context);
		return sStudentDAO;
	}
	
	private StudentDAO(Context context)
	{
		mAppContext = context;
	}
	
	public long insert(Student student)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_STUDENT_FIRST_NAME, student.getFirstName());
		values.put(COLUMN_STUDENT_LAST_NAME, student.getLastName());
		values.put(COLUMN_STUDENT_PHONE_NUMBER, student.getPhoneNumber());
		values.put(COLUMN_STUDENT_EMAIL, student.getEmail());
		values.put(COLUMN_STUDENT_MODULE_ID, student.getModule().getId());
		return DatabaseHelper.get(mAppContext)
				.getWritableDatabase().insert(TABLE_STUDENT, null, values);
	}
	
	public void delete(Student student)
	{
		String selection = COLUMN_STUDENT_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(student.getId())};
		DatabaseHelper.get(mAppContext).getWritableDatabase()
			.delete(TABLE_STUDENT, selection, selectionArgs);
	}
	
	public int update(Student student)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_STUDENT_FIRST_NAME, student.getFirstName());
		values.put(COLUMN_STUDENT_LAST_NAME, student.getLastName());
		values.put(COLUMN_STUDENT_PHONE_NUMBER, student.getPhoneNumber());
		values.put(COLUMN_STUDENT_EMAIL, student.getEmail());
		values.put(COLUMN_STUDENT_MODULE_ID, student.getModule().getId());
		
		String selection = COLUMN_STUDENT_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(student.getId())};
		return DatabaseHelper.get(mAppContext)
				.getReadableDatabase()
				.update(TABLE_STUDENT, values, selection, selectionArgs);
	}
	
	public Student getStudentById(long studentId)
	{
		Student student = null;
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_STUDENT, 
				null, // all columns.
				COLUMN_STUDENT_ID + " = ?", // look for student id
				new String[]{String.valueOf(studentId)}, // with this value
				null, // group by
				null, // having
				null, // order by
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			student = cursorToStudent(cursor);
		
		return student;
	}
	
	public Student getStudentByLastName(String studentLastName)
	{
		Student student = null;
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_STUDENT, 
				null, // all columns.
				COLUMN_STUDENT_LAST_NAME + " = ?", // look for student name
				new String[]{studentLastName}, // with this value
				null, // group by
				null, // having
				null, // order by
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			student = cursorToStudent(cursor); 
		
		return student;
	}
	
	public ArrayList<Student> getStudentsByModule(long moduleId)
	{
		ArrayList<Student> students = new ArrayList<Student>();
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_STUDENT, 
				null, // all columns.
				COLUMN_STUDENT_MODULE_ID + " = ?", // look for module id
				new String[]{String.valueOf(moduleId)}, // with this value
				null, // group by
				null, // having
				null, // order by
				null); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Student student = cursorToStudent(cursor);
				students.add(student);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return students;
	}
	
	public ArrayList<Student> getAllStudents()
	{
		ArrayList<Student> students = new ArrayList<Student>();
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_STUDENT, 
				null, 
				null, 
				null, 
				null, 
				null, 
				COLUMN_STUDENT_LAST_NAME + " asc");
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Student student = cursorToStudent(cursor);
				students.add(student);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		return students;
	}
	
	protected Student cursorToStudent(Cursor cursor)
	{
		Student student = new Student();
		student.setId(cursor.getLong(0));
		student.setFirstName(cursor.getString(1));
		student.setLastName(cursor.getString(2));
		student.setPhoneNumber(cursor.getString(3));
		student.setEmail(cursor.getString(4));
		
		long moduleId = cursor.getLong(5);
		Module module = ModuleDAO.get(mAppContext).getModuleById(moduleId);
		if (module != null)
			student.setModule(module);

		return student;
	}
}
