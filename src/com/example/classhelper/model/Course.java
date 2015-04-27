package com.example.classhelper.model;

import java.io.Serializable;

public class Course extends Model implements Serializable
{
	private static final long serialVersionUID = 4383481182099146956L;
	private long mCourseId;
	private String mName;
	private Module mModule;
	
	public Course()
	{
		mModule = new Module();
	}
	
	public long getId() {
		return mCourseId;
	}
	
	public void setId(long mCourseId) {
		this.mCourseId = mCourseId;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String mName) {
		this.mName = mName;
	}
	
	public Module getModule() {
		return mModule;
	}
	
	public void setModule(Module module) {
		this.mModule = module;
	}
	
	@Override
	public String toString()
	{
		return mName;
	}
}
