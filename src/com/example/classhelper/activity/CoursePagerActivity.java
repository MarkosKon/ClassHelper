package com.example.classhelper.activity;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.CourseDAO;
import com.example.classhelper.fragment.CoursePagerFragment;
import com.example.classhelper.model.Course;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CoursePagerActivity extends AppCompatActivity
	implements CoursePagerFragment.Callbacks
{
	public static final String TAG = "CoursePagerActivity";
	
	private ViewPager mViewPager;
	private ArrayList<Course> mCourses;
	
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		Course course = (Course) getIntent().getSerializableExtra(CoursePagerFragment.EXTRA_COURSE);
		mCourses = CourseDAO.get(this).getAllCourses();
		
		boolean courseAlreadyExists = false;
		for(Course c : mCourses)
		{
			if (c.getId() == course.getId())
			{
				courseAlreadyExists = true;
				break;
			}
		}
		if (!courseAlreadyExists)
			mCourses.add(course);
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mCourses.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Course course = (Course) mCourses.get(pos);
				return CoursePagerFragment.newInstance(course);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) 
			{
				Course course = (Course) mCourses.get(pos);
				if (course.getName() != null)
					setTitle(course.getName());
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
		
		for(int i = 0; i < mCourses.size(); i++)
		{
			if(mCourses.get(i).getId() == course.getId())
			{
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
	
	@Override
	public void onListItemUpdate(Course course) 
	{
		int courseExists = CourseDAO.get(getApplicationContext()).update(course);
		if (courseExists == 0)
			CourseDAO.get(getApplicationContext()).insert(course);
		Toast toast = Toast.makeText(this, 
									 getResources().getText(R.string.toast_item_saved), 
									 Toast.LENGTH_SHORT);
		toast.show();
	}

}
