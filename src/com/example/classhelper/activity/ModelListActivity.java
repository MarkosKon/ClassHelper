package com.example.classhelper.activity;

import com.example.classhelper.R;

/**
 * Concrete list activities of all models are subclasses of ModelListActivity.
 */
public abstract class ModelListActivity extends SingleFragmentActivity
{
	@Override
	protected int getLayoutResId() 
	{
		return R.layout.activity_masterdetail;
	}
}
