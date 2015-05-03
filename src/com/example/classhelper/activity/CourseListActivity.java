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

/**
 * This activity inherits from ModelListActivity and SingleFragmentActivity.
 * Its purpose is to create a CourseListFragment (and a CoursePagerFragment
 * for tablets) and respond to events.
 */
public class CourseListActivity extends ModelListActivity 
	implements Callbacks<Course>, CoursePagerFragment.Callbacks 
{
	public static final String TAG = "CourseListActivity";
	
	@Override
	protected Fragment createFragment() 
	{
		return new CourseListFragment();
	}
	
	/**
	 * This method responds to the selection of a list item from CourseListFragment.
	 * If the application runs on a phone (detailFragmentContainer does not exist) starts
	 * a CoursePagerActivity and puts the current activity on the background. If the 
	 * application runs on a tablet, fills the detailFragmentContainer with a 
	 * CoursePagerFragment.
	 */
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
	
	/**
	 * This method responds to CoursePagerFragment's save details button when
	 * the application runs on a tablet.
	 */
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
		
		FragmentManager fm = getSupportFragmentManager();
		CourseListFragment listFragment = (CourseListFragment)
		fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateAdapter();
	}
}
