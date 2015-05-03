package com.example.classhelper.activity;

import com.example.classhelper.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * A reusable abstract activity class that can be extended by
 * concrete activities.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity 
{
	protected abstract Fragment createFragment();
	
	/** 
	 * Subclasses may choose to override this method for a different layout.  
	 */
	protected int getLayoutResId() 
	{
		return R.layout.activity_fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResId());
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null)
		{
			fragment = createFragment();
			fm.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
		}
	}
}
