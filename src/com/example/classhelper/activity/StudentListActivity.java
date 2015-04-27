package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.fragment.StudentListFragment;
import com.example.classhelper.fragment.StudentPagerFragment;
import com.example.classhelper.model.Student;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

/**
 * This activity inherits from ModelListActivity and SingleFragmentActivity.
 * It's purpose is to create a StudentListFragment (and a StudentPagerFragment
 * for tablets) and respond to events.
 */
public class StudentListActivity extends ModelListActivity
	implements Callbacks<Student>, StudentPagerFragment.Callbacks
{
	public static final String TAG = "StudentListActivity";
	
	@Override
	public Fragment createFragment()
	{
		return new StudentListFragment();
	}
	
	/**
	 * This method responds to the selection of a list item from StudentListFragment.
	 * If the application runs on a phone (detailFragmentContainer does not exist) starts
	 * a StudentPagerActivity and puts the current activity on the background. If the 
	 * application runs on a tablet, fills the detailFragmentContainer with a 
	 * StudentPagerFragment.
	 */
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
	
	/**
	 * This method responds to StudentPagerFragment's save details button when
	 * the application runs on a tablet.
	 */
	@Override
	public void onListItemUpdate(Student student) 
	{
		int studentExists = StudentDAO.get(getApplicationContext()).update(student);
		if (studentExists == 0)
			StudentDAO.get(getApplicationContext()).insert(student);
		Toast toast = Toast.makeText(this, 
									 getResources().getText(R.string.toast_item_saved), 
									 Toast.LENGTH_SHORT);
		toast.show();
		
		FragmentManager fm = getSupportFragmentManager();
		StudentListFragment listFragment = (StudentListFragment)
		fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateAdapter();
	}
}
