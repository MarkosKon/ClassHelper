package com.example.classhelper.activity;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.model.Grade;
import com.example.classhelper.data.GradeDAO;
import com.example.classhelper.fragment.GradePagerFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * The purpose of this class is to instantiate a ViewPager item for the 
 * GradePagerFragment and respond to fragment / viewpager events.  
 */
public class GradePagerActivity extends AppCompatActivity 
	implements GradePagerFragment.Callbacks
{
	public static final String TAG = "GradePagerActivity";
	
	private ViewPager mViewPager;
	private ArrayList<Grade> mGrades;
	
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		
		Grade grade = (Grade) getIntent().getSerializableExtra(GradePagerFragment.EXTRA_GRADE);
		mGrades = GradeDAO.get(this).getAllGrades();
		
		// Awkward check if we are editing grade details or creating a new one.
		boolean gradeAlreadyExists = false;
		for(Grade g : mGrades)
		{
			if (g.getId() == grade.getId())
			{
				gradeAlreadyExists = true;
				break;
			}
		}
		if (!gradeAlreadyExists)
		{
			mGrades.add(grade);
			setTitle(getResources().getString(R.string.create_model));
		}
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mGrades.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Grade g = (Grade) mGrades.get(pos);
				return GradePagerFragment.newInstance(g);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			
			@Override
			public void onPageSelected(int pos) 
			{
				
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
		
		for(int i = 0; i < mGrades.size(); i++)
		{
			if(mGrades.get(i).getId() == grade.getId())
			{
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
	
	/**
	 * This method responds to GradePagerFragment's save details button when
	 * the application runs on a phone.
	 */
	@Override
	public void onListItemUpdate(Grade grade) 
	{
		int gradeExists = GradeDAO.get(getApplicationContext()).update(grade);
		if (gradeExists == 0)
			GradeDAO.get(getApplicationContext()).insert(grade);
		Toast toast = Toast.makeText(this, 
									 getResources().getText(R.string.toast_item_saved), 
									 Toast.LENGTH_SHORT);
		toast.show();
	}
}
