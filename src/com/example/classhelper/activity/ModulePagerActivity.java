package com.example.classhelper.activity;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.ModuleDAO;
import com.example.classhelper.fragment.ModulePagerFragment;
import com.example.classhelper.model.Module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ModulePagerActivity extends AppCompatActivity 
	implements ModulePagerFragment.CallBacks
{
	public static final String TAG = "ModulePagerActivity";
	
	private ViewPager mViewPager;
	private ArrayList<Module> mModules;
	
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		Module module = (Module) getIntent().getSerializableExtra(ModulePagerFragment.EXTRA_MODULE);
		mModules = ModuleDAO.get(this).getAllModules();
		
		// Awkward check if we are editing module details or creating a new one.
		boolean moduleAlreadyExists = false;
		for(Module m : mModules)
		{
			if (m.getId() == module.getId())
			{
				moduleAlreadyExists = true;
				setTitle(m.getName());
				break;
			}
		}
		if (!moduleAlreadyExists)
		{
			mModules.add(module);
			setTitle(getResources().getString(R.string.create_model));
		}
				
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mModules.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Module module = (Module) mModules.get(pos);
				return ModulePagerFragment.newInstance(module);
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				Module module = (Module) mModules.get(pos);
				if (module.getName() != null)
					setTitle(module.getName());
				
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
		
		for(int i = 0; i < mModules.size(); i++)
		{
			if(mModules.get(i).getId() == module.getId())
			{
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
	
	@Override
	public void onListItemUpdate(Module module) 
	{
		int moduleExists = ModuleDAO.get(getApplicationContext()).update(module);
		if (moduleExists == 0)
			ModuleDAO.get(getApplicationContext()).insert(module);
		Toast toast = Toast.makeText(this, 
									 getResources().getText(R.string.toast_item_saved), 
									 Toast.LENGTH_SHORT);
		toast.show();
	}

}
