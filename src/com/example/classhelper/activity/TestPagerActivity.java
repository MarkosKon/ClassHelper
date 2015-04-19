package com.example.classhelper.activity;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.fragment.TestPagerFragment;
import com.example.classhelper.model.Test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class TestPagerActivity extends FragmentActivity
	implements TestPagerFragment.Callbacks
{
	public static final String TAG = "TestPagerActivity";
	
	private ViewPager mViewPager;
	private ArrayList<Test> mTests;
	
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		
		Test test = (Test) getIntent().getSerializableExtra(TestPagerFragment.EXTRA_TEST);
		mTests = TestDAO.get(this).getAllTests();
		
		// Awkward check if we are editing student details or creating a new one.
		boolean testAlreadyExists = false;
		for(Test t : mTests)
		{
			if (t.getId() == test.getId())
			{
				testAlreadyExists = true;
				break;
			}
		}
		if (!testAlreadyExists)
			mTests.add(test);
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mTests.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Test t = (Test) mTests.get(pos);
				return TestPagerFragment.newInstance(t);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				Test t = (Test) mTests.get(pos);
				if (t.getName() != null)
					setTitle(t.getName());
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		for(int i = 0; i < mTests.size(); i++)
		{
			if(mTests.get(i).getId() == test.getId())
			{
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
	
	@Override
	public void onListItemUpdate(Test test) 
	{
		int testExists = TestDAO.get(getApplicationContext()).update(test);
		if (testExists == 0)
			TestDAO.get(getApplicationContext()).insert(test);
		Toast toast = Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
