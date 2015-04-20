package com.example.classhelper.model;

import java.io.Serializable;

public class MainMenuItem implements Serializable 
{
	private String mTitle;
	private int mImageResourceId; 
	
	public MainMenuItem()
	{
		
	}
	
	public MainMenuItem(String title, int resourceId)
	{
		mTitle = title;
		mImageResourceId = resourceId;
	}

	public String getTitle() 
	{
		return mTitle;
	}

	public void setTitle(String mTitle) 
	{
		this.mTitle = mTitle;
	}

	public int getImageResourceId() 
	{
		return mImageResourceId;
	}

	public void setImageResourceId(int mImageResourceId) 
	{
		this.mImageResourceId = mImageResourceId;
	}
	
	@Override
	public String toString()
	{
		return mTitle;
	}
}
