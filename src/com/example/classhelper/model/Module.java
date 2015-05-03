package com.example.classhelper.model;

import java.io.Serializable;

/**
 * The model for a course.
 */
public class Module extends Model implements Serializable
{
	private static final long serialVersionUID = -342107266916199862L;
	private long mModuleId;
	private String mName;
	
	public Module()
	{
		
	}
	
	public long getId() 
	{
		return mModuleId;
	}
	
	public void setId(long mModuleId) 
	{
		this.mModuleId = mModuleId;
	}
	
	public String getName() 
	{
		return mName;
	}
	
	public void setName(String mName) 
	{
		this.mName = mName;
	}
	
	@Override
	public String toString()
	{
		return mName;
	}
}
