package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.data.CourseDAO;
import com.example.classhelper.fragment.CourseListFragment;
import com.example.classhelper.fragment.CoursePagerFragment;
import com.example.classhelper.model.Course;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class CourseListActivity extends ModelListActivity 
	implements Callbacks<Course>, CoursePagerFragment.Callbacks 
{

	@Override
	protected Fragment createFragment() 
	{
		return new CourseListFragment();
	}

	@Override
	public void onListItemSelected(Course course) 
	{
		if (findViewById(R.id.detailFragmentContainer) == null) 
		{
			// Start an instance of CoursePagerActivity
			Intent i = new Intent(this, CoursePagerActivity.class);
			i.putExtra(CoursePagerFragment.EXTRA_COURSE, course);
			startActivity(i);
		}
		else
		{
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = CoursePagerFragment.newInstance(course);
			
			if (oldDetail != null) 
			{
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
		}
	}

	@Override
	public void onListItemUpdate(Course course) 
	{
		int courseExists = CourseDAO.get(getApplicationContext()).update(course);
		if (courseExists == 0)
			CourseDAO.get(getApplicationContext()).insert(course);
		Toast toast = Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT);
		toast.show();
		
		FragmentManager fm = getSupportFragmentManager();
		CourseListFragment listFragment = (CourseListFragment)
		fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateAdapter();
	}
}
