package com.example.classhelper.model;

import java.io.Serializable;

public class Test extends Model implements Serializable
{
	private long mTestId;
	private String mName;
	private String mNotes;
	private Course mCourse;
	
	public Test()
	{
		mCourse = new Course();
	}
	
	public long getId() {
		return mTestId;
	}
	
	public void setId(long mTestId) {
		this.mTestId = mTestId;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String mName) {
		this.mName = mName;
	}
	
	public String getNotes() {
		return mNotes;
	}
	
	public void setNotes(String mNotes) {
		this.mNotes = mNotes;
	}
	
	public Course getCourse() {
		return mCourse;
	}
	
	public void setCourse(Course course) {
		this.mCourse = course;
	}
	
	@Override
	public String toString()
	{
		return mName + " - " + mCourse.getName();
	}
}
