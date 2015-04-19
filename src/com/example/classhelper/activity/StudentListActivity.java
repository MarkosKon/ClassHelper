package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.fragment.StudentListFragment;
import com.example.classhelper.fragment.StudentPagerFragment;
import com.example.classhelper.model.Student;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class StudentListActivity extends ModelListActivity
	implements Callbacks<Student>
{
	public static final String TAG = "StudentListActivity";
	
	@Override
	public Fragment createFragment()
	{
		return new StudentListFragment();
	}
	
	public void onListItemSelected(Student student)
	{
		if (findViewById(R.id.detailFragmentContainer) == null) 
		{
			// Start an instance of StudentPagerActivity
			Intent i = new Intent(this, StudentPagerActivity.class);
			i.putExtra(StudentPagerFragment.EXTRA_STUDENT, student);
			startActivity(i);
		}
		else
		{
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = StudentPagerFragment.newInstance(student);
			
			if (oldDetail != null) 
			{
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
		}
	}
}
