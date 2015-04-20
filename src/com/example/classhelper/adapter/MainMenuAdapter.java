package com.example.classhelper.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.classhelper.R;
import com.example.classhelper.model.MainMenuItem;

public class MainMenuAdapter extends ArrayAdapter<MainMenuItem> implements Filterable
{
	private Activity mAppContext;
	
	public MainMenuAdapter(ArrayList<MainMenuItem> menuItems, Activity activity)
	{
		super(activity, android.R.layout.simple_list_item_1, menuItems);
		mAppContext = activity;
	}
	
	@SuppressLint("InflateParams")
	@Override 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// If we weren't given a view, inflate one.
		if (convertView == null)
			convertView = mAppContext.getLayoutInflater()
				.inflate(R.layout.list_item_main_menu_item, null);
		
		// Configure the view for this crime.
		MainMenuItem m = getItem(position);
		
		ImageView iconImageView = 
				(ImageView) convertView.findViewById(R.id.mainMenuItem_list_item_imageView);
		iconImageView.setImageResource(m.getImageResourceId());
		
		TextView titleTextView = 
				(TextView) convertView.findViewById(R.id.mainMenuItem_list_item_title);
		titleTextView.setText(m.getTitle());
		
		return convertView;
	}
}
