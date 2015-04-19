package com.example.classhelper.activity;

import com.example.classhelper.R;
import com.example.classhelper.fragment.ModuleListFragment;
import com.example.classhelper.fragment.ModulePagerFragment;
import com.example.classhelper.model.Module;
import com.example.classhelper.myinterface.Callbacks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ModuleListActivity extends ModelListActivity 
	implements Callbacks<Module>
{
	public static final String TAG = "ModuleListActivity";
	
	@Override
	protected Fragment createFragment() 
	{
		return new ModuleListFragment();
	}

	@Override
	public void onListItemSelected(Module module) 
	{
		if (findViewById(R.id.detailFragmentContainer) == null) 
		{
			// Start an instance of ModulePagerActivity
			Intent i = new Intent(this, ModulePagerActivity.class);
			i.putExtra(ModulePagerFragment.EXTRA_MODULE, module);
			startActivity(i);
		}
		else
		{
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDetail = ModulePagerFragment.newInstance(module);
			
			if (oldDetail != null) 
			{
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
		}
	}

}
