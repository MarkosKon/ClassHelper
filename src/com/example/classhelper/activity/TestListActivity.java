package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.fragment.TestListFragment;
import com.example.classhelper.fragment.TestPagerFragment;
import com.example.classhelper.model.Test;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

/**
 * This activity inherits from ModelListActivity and SingleFragmentActivity.
 * Its purpose is to create a TestListFragment (and a TestPagerFragment
 * for tablets) and respond to events.
 */
public class TestListActivity extends ModelListActivity 
	implements Callbacks<Test>, TestPagerFragment.Callbacks
{
	public static final String TAG = "TestListActivity";
	
	@Override
	protected Fragment createFragment() 
	{
		return new TestListFragment();
	}
	
	/**
	 * This method responds to the selection of a list item from TestListFragment.
	 * If the application runs on a phone (detailFragmentContainer does not exist) starts
	 * a TestPagerActivity and puts the current activity on the background. If the 
	 * application runs on a tablet, fills the detailFragmentContainer with a 
	 * TestPagerFragment.
	 */
	@Override
	public void onListItemSelected(Test test) 
	{
		if (findViewById(R.id.detailFragmentContainer) == null) 
		{
			// Start an instance of TestPagerActivity
			Intent i = new Intent(this, TestPagerActivity.class);
			i.putExtra(TestPagerFragment.EXTRA_TEST, test);
			startActivity(i);
		}
		else
		{
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = TestPagerFragment.newInstance(test);
			
			if (oldDetail != null) 
			{
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
		}
	}
	
	/**
	 * This method responds to TestPagerFragment's save details button when
	 * the application runs on a tablet.
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
		
		FragmentManager fm = getSupportFragmentManager();
		TestListFragment listFragment = (TestListFragment)
		fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateAdapter();
	}
}
