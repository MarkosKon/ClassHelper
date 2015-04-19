package com.example.classhelper.activity;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.fragment.StudentPagerFragment;
import com.example.classhelper.model.Student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class StudentPagerActivity extends FragmentActivity 
	implements StudentPagerFragment.Callbacks
{
	public static final String TAG = "StudentPagerActivity";
	
	private ViewPager mViewPager;
	private ArrayList<Student> mStudents;
	
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		
		Student student = (Student) getIntent().getSerializableExtra(StudentPagerFragment.EXTRA_STUDENT);
		mStudents = StudentDAO.get(this).getAllStudents();
		
		// Awkward check if we are editing student details or creating a new one.
		boolean studentAlreadyExists = false;
		for(Student s : mStudents)
		{
			if (s.getId() == student.getId())
			{
				studentAlreadyExists = true;
				break;
			}
		}
		if (!studentAlreadyExists)
			mStudents.add(student);
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mStudents.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Student s = (Student) mStudents.get(pos);
				return StudentPagerFragment.newInstance(s);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				Student s = (Student) mStudents.get(pos);
				if (s.getLastName() != null)
					setTitle(s.getLastName());
				
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
		
		for(int i = 0; i < mStudents.size(); i++)
		{
			if(mStudents.get(i).getId() == student.getId())
			{
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
	
	public void onListItemUpdate(Student student)
	{
		int studentExists = StudentDAO.get(getApplicationContext()).update(student);
		if (studentExists == 0)
			StudentDAO.get(getApplicationContext()).insert(student);
		Toast toast = Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT);
		toast.show();
	}
}
