package com.example.classhelper.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.classhelper.R;
import com.example.classhelper.model.Module;

public class ModuleAdapter extends ArrayAdapter<Module> implements Filterable
{
	private Activity mAppContext;
	
	public ModuleAdapter(ArrayList<Module> modules, Activity activity)
	{
		super(activity, android.R.layout.simple_list_item_1, modules);
		mAppContext = activity;
	}
	
	@SuppressLint("InflateParams")
	@Override 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// If we weren't given a view, inflate one.
		if (convertView == null)
			convertView = mAppContext.getLayoutInflater()
				.inflate(R.layout.list_item_module, null);
		
		// Configure the view for this crime.
		Module m = getItem(position);
		
		TextView nameTextView = 
				(TextView) convertView.findViewById(R.id.module_list_item_name);
		nameTextView.setText(m.getName());
		
		return convertView;
	}
}
