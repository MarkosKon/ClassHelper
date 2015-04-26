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
import com.example.classhelper.model.Test;

public class TestAdapter extends ArrayAdapter<Test> implements Filterable
{
	private Activity mAppContext;
	
	public TestAdapter(ArrayList<Test> tests, Activity activity)
	{
		super(activity, android.R.layout.simple_list_item_1, tests);
		mAppContext = activity;
	}
	
	@SuppressLint("InflateParams")
	@Override 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
			convertView = mAppContext.getLayoutInflater()
				.inflate(R.layout.list_item_test, null);
		
		Test t = getItem(position);
		
		TextView nameTextView = 
				(TextView) convertView.findViewById(R.id.test_list_item_name);
		nameTextView.setText(t.getName());
		
		return convertView;
	}
}
