package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.fragment.TestListFragment;
import com.example.classhelper.fragment.TestPagerFragment;
import com.example.classhelper.model.Test;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class TestListActivity extends ModelListActivity 
	implements Callbacks<Test>
{
	public static final String TAG = "TestListActivity";
	@Override
	protected Fragment createFragment() 
	{
		return new TestListFragment();
	}
	@Override
	public void onListItemSelected(Test test) 
	{
		if (findViewById(R.id.detailFragmentContainer) == null) 
		{
			// Start an instance of StudentPagerActivity
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

}
