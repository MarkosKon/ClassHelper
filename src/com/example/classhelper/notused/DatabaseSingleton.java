package com.example.classhelper.notused;
//package com.example.classhelper;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//import android.content.Context;
//import android.util.Log;
//
///**
// *  Singleton class holding database contents.
// */
//
//public class DatabaseSingleton 
//{
//	private static final String TAG = "DatabaseSingleton";
//	
//	private Context mAppContext;
//	private static DatabaseSingleton sDatabaseSingleton;
//	private DatabaseHelper mDatabaseHelper;
//	
//	private DatabaseSingleton(Context appContext)
//	{
//		mAppContext = appContext;
//		
//		mDatabaseHelper = new DatabaseHelper(appContext);
//	}
//	
//	public static DatabaseSingleton get(Context c)
//	{
//		if (sDatabaseSingleton == null)
//			sDatabaseSingleton = new DatabaseSingleton(c);
//		return sDatabaseSingleton;
//	}
//	
//	public void addStudent(Student student)
//	{
//		mDatabaseHelper.insertStudent(student);
//	}
//	
//	public void addTest(Test test)
//	{
//		mDatabaseHelper.insertTest(test);
//	}
//	
//	public void addGrade(Grade grade)
//	{
//		mDatabaseHelper.insertGrade(grade);
//	}
//	
//	public void addCourse(Course course)
//	{
//		long courseId = mDatabaseHelper.insertCourse(course);
//	}
//	
//	public void addModule(Module module)
//	{
//		mDatabaseHelper.insertModule(module);
//	}
//	
//	public Student getStudent(long studentId)
//	{
//		for (Student s:mStudents)
//		{
//			if (s.getId() == studentId)
//				return s;
//		}
//		return null;
//	}
//	
//	public Test getTest(UUID testId)
//	{
//		for (Test t:mTests)
//		{
//			if (t.getTestId().equals(testId))
//				return t;
//		}
//		return null;
//	}
//	
//	public Grade getGrade(UUID gradeId)
//	{
//		for (Grade g:mGrades)
//		{
//			if (g.getGradeId().equals(gradeId))
//				return g;
//		}
//		return null;
//	}
//	
//	public Course getCourse(UUID courseId)
//	{
//		for (Course c:mCourses)
//		{
//			if (c.getCourseId().equals(courseId))
//				return c;
//		}
//		return null;
//	}
//	
//	public Module getModule(UUID moduleId)
//	{
//		for (Module m:mModules)
//		{
//			if (m.getModuleId().equals(moduleId))
//				return m;
//		}
//		return null;
//	}
//	
//	public ArrayList<Student> getStudents()
//	{
//		return mStudents;
//	}
//	
//	public ArrayList<Test> getTests()
//	{
//		return mTests;
//	}
//	
//	public ArrayList<Grade> getGrades()
//	{
//		return mGrades;
//	}
//	
//	public ArrayList<Course> getCourses()
//	{
//		return mCourses;
//	}
//	
//	public ArrayList<Module> getModules()
//	{
//		return mModules;
//	}
//	
//	public void deleteStudent(Student student)
//	{
//		mStudents.remove(student);
//	}
//	
//	public void deleteTest(Test test)
//	{
//		mTests.remove(test);
//	}
//	
//	public void deleteGrade(Grade grade)
//	{
//		mGrades.remove(grade);
//	}
//	
//	public void deleteCourse(Course course)
//	{
//		mCourses.remove(course);
//	}
//	
//	public void deleteModule(Module module)
//	{
//		mModules.remove(module);
//	}
//	
//	public boolean saveData()
//	{
//		return true;
//	}
//}
