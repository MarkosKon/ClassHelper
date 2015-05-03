package com.example.classhelper.activity;

import com.example.classhelper.fragment.MainMenuFragment;

import android.support.v4.app.Fragment;

/**
 * A list activity that serves the purpose of the application
 * menu.
 */
public class MainMenuActivity extends SingleFragmentActivity 
{
	@Override
	public Fragment createFragment()
	{
		return new MainMenuFragment();
	}
}
