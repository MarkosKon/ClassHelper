package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.data.GradeDAO;
import com.example.classhelper.fragment.GradeListFragment;
import com.example.classhelper.fragment.GradePagerFragment;
import com.example.classhelper.model.Grade;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class GradeListActivity extends ModelListActivity
	implements Callbacks<Grade>, GradePagerFragment.Callbacks
{
	public static final String TAG = "GradeListActivity";
	
	@Override
	protected Fragment createFragment() 
	{
		return new GradeListFragment();
	}

	@Override
	public void onListItemSelected(Grade grade) 
	{
		if (findViewById(R.id.detailFragmentContainer) == null) 
		{
			Intent i = new Intent(this, GradePagerActivity.class);
			i.putExtra(GradePagerFragment.EXTRA_GRADE, grade);
			startActivity(i);
		}
		else
		{
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = GradePagerFragment.newInstance(grade);
			
			if (oldDetail != null) 
			{
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
		}
	}

	@Override
	public void onListItemUpdate(Grade grade) 
	{
		int gradeExists = GradeDAO.get(getApplicationContext()).update(grade);
		if (gradeExists == 0)
			GradeDAO.get(getApplicationContext()).insert(grade);
		Toast toast = Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT);
		toast.show();
		
		FragmentManager fm = getSupportFragmentManager();
		GradeListFragment listFragment = (GradeListFragment)
		fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateAdapter();
	}
}
