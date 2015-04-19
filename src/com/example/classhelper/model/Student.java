package com.example.classhelper.model;

import java.io.Serializable;

public class Student extends Model implements Serializable
{
	private long mStudentId;
	private String mFirstName;
	private String mLastName;
	private Module mModule;
	
	public Student()
	{
		mModule = new Module();
	}
	
	public long getId() 
	{
		return mStudentId;
	}
	
	public void setId(long mStudentId) 
	{
		this.mStudentId = mStudentId;
	}
	
	public String getFirstName() 
	{
		return mFirstName;
	}
	
	public void setFirstName(String mFirstName) 
	{
		this.mFirstName = mFirstName;
	}
	
	public String getLastName() 
	{
		return mLastName;
	}
	
	public void setLastName(String mLastName) 
	{
		this.mLastName = mLastName;
	}
	
	public Module getModule() 
	{
		return mModule;
	}
	
	public void setModule(Module mModule) 
	{
		this.mModule = mModule;
	}
	
	@Override
	public String toString()
	{
		return mFirstName + mLastName;
	}
}
