package com.example.classhelper.data;

import java.util.ArrayList;

import com.example.classhelper.model.Grade;
import com.example.classhelper.model.Student;
import com.example.classhelper.model.Test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class GradeDAO 
{
	private static GradeDAO sGradeDAO;
	private Context mAppContext;
	
	private static final String TABLE_GRADE = "grade";
	private static final String COLUMN_GRADE_ID = "_id";
	private static final String COLUMN_GRADE_VALUE = "value";
	private static final String COLUMN_GRADE_STUDENT_ID = "student_id";
	private static final String COLUMN_GRADE_TEST_ID = "test_id";
	
	public static GradeDAO get(Context context)
	{
		if (sGradeDAO == null)
			sGradeDAO = new GradeDAO(context);
		return sGradeDAO;
	}
	
	private GradeDAO(Context context)
	{
		mAppContext = context;
	}
	
	public long insert(Grade grade)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_GRADE_VALUE, grade.getGradeValue());
		values.put(COLUMN_GRADE_STUDENT_ID, grade.getStudent().getId());
		values.put(COLUMN_GRADE_TEST_ID, grade.getTest().getId());
		return DatabaseHelper.get(mAppContext).getWritableDatabase().insert(TABLE_GRADE, null, values);
	}
	
	public void delete(Grade grade)
	{
		String selection = COLUMN_GRADE_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(grade.getId())};
		DatabaseHelper.get(mAppContext).getWritableDatabase()
			.delete(TABLE_GRADE, selection, selectionArgs);
	}
	
	public int update(Grade grade)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_GRADE_VALUE, grade.getGradeValue());
		values.put(COLUMN_GRADE_STUDENT_ID, grade.getStudent().getId());
		values.put(COLUMN_GRADE_TEST_ID, grade.getTest().getId());
		
		String selection = COLUMN_GRADE_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(grade.getId())};
		return DatabaseHelper.get(mAppContext).getReadableDatabase().update(TABLE_GRADE, values, selection, selectionArgs);
	}
	
	public Grade getGradeById(long gradeId)
	{
		Grade grade = null;
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_GRADE, 
				null, // all columns.
				COLUMN_GRADE_ID + " = ?", // look for module id
				new String[]{String.valueOf(gradeId)}, // with this value
				null, // group by
				null, // order by
				null, // having
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			grade = cursorToGrade(cursor);
		
		return grade;
	}
	
	public ArrayList<Grade> getGradesByTest(Test test)
	{
		ArrayList<Grade> grades = new ArrayList<Grade>();
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_GRADE, 
				null, // all columns.
				COLUMN_GRADE_TEST_ID + " = ?", // look for module id
				new String[]{String.valueOf(test.getId())}, // with this value
				null, // group by
				null, // having
				COLUMN_GRADE_VALUE, // order by
				null); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Grade grade = cursorToGrade(cursor);
				grades.add(grade);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return grades;
	}
	
	public ArrayList<Grade> getGradesByStudent(Student student)
	{
		ArrayList<Grade> grades = new ArrayList<Grade>();
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_GRADE, 
				null, // all columns.
				COLUMN_GRADE_STUDENT_ID + " = ?", // look for module id
				new String[]{String.valueOf(student.getId())}, // with this value
				null, // group by
				null, // having
				COLUMN_GRADE_VALUE, // order by
				null); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Grade grade = cursorToGrade(cursor);
				grades.add(grade);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return grades;
	}
	
	public ArrayList<Grade> getAllGrades()
	{
		ArrayList<Grade> grades = new ArrayList<Grade>();
		Cursor cursor = DatabaseHelper.get(mAppContext).getReadableDatabase().query(TABLE_GRADE, 
				null, 
				null, 
				null, 
				null, 
				null, 
				COLUMN_GRADE_VALUE + " asc");
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Grade grade = cursorToGrade(cursor);
				grades.add(grade);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		return grades;
	}
	
	protected Grade cursorToGrade(Cursor cursor)
	{
		Grade grade = new Grade();
		grade.setId(cursor.getLong(0));
		grade.setGradeValue(cursor.getInt(1));
		
		long studentId = cursor.getLong(2);
		Student student = StudentDAO.get(mAppContext).getStudentById(studentId);
		if (student != null)
			grade.setStudent(student);
		
		long testId = cursor.getLong(3);
		Test test = TestDAO.get(mAppContext).getTestById(testId);
		if (test != null)
			grade.setTest(test);
		
		return grade;
	}
}
