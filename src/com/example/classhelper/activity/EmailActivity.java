package com.example.classhelper.activity;

import com.example.classhelper.fragment.EmailFragment;

import android.support.v4.app.Fragment;

public class EmailActivity extends SingleFragmentActivity
{
	public static final String TAG = "EmailActivity";

	@Override
	protected Fragment createFragment() 
	{
		return new EmailFragment();
	}
}
