package com.example.classhelper.model;

import java.io.Serializable;

public class Grade extends Model implements Serializable
{
	private long mGradeId;
	private int mGradeValue;
	private Student mStudent;
	private Test mTest;
	
	public Grade()
	{
		mStudent = new Student();
		mTest = new Test();
	}
	
	public long getId() 
	{
		return mGradeId;
	}
	
	public void setId(long grade) 
	{
		this.mGradeId = grade;
	}
	
	public int getGradeValue() 
	{
		return mGradeValue;
	}
	
	public void setGradeValue(int mGradeValue) 
	{
		this.mGradeValue = mGradeValue;
	}
	
	public Student getStudent() 
	{
		return mStudent;
	}
	
	public void setStudent(Student student) 
	{
		this.mStudent = student;
	}
	
	public Test getTest() {
		return mTest;
	}
	
	public void setTest(Test test) 
	{
		this.mTest = test;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(mGradeValue);
	}
}
