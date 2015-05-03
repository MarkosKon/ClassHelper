package com.example.classhelper.activity;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.fragment.TestPagerFragment;
import com.example.classhelper.model.Test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * The purpose of this class is to instantiate a ViewPager item for the 
 * TestPagerFragment and respond to fragment / viewpager events.  
 */
public class TestPagerActivity extends AppCompatActivity
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
		
		// Awkward check if we are editing test details or creating a new one.
		boolean testAlreadyExists = false;
		for(Test t : mTests)
		{
			if (t.getId() == test.getId())
			{
				testAlreadyExists = true;
				setTitle(t.getName());
				break;
			}
		}
		if (!testAlreadyExists)
		{
			mTests.add(test);
			setTitle(getResources().getString(R.string.create_model));
		}
		
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
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			
			@Override
			public void onPageSelected(int pos) 
			{
				Test t = (Test) mTests.get(pos);
				if (t.getName() != null)
					setTitle(t.getName());
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) 
			{
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) 
			{
				
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
	
	/**
	 * This method responds to TestPagerFragment's save details button when
	 * the application runs on a phone.
	 */
	@Override
	public void onListItemUpdate(Test test) 
	{
		int testExists = TestDAO.get(getApplicationContext()).update(test);
		if (testExists == 0)
			TestDAO.get(getApplicationContext()).insert(test);
		Toast toast = Toast.makeText(this, 
									 getResources().getText(R.string.toast_item_saved), 
									 Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
