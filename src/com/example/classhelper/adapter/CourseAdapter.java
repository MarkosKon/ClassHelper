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
import com.example.classhelper.model.Course;

/**
 * A custom adapter class used by CourseListFragment and spinners.
 */
public class CourseAdapter extends ArrayAdapter<Course> implements Filterable
{
	private Activity mAppContext;
	private ArrayList<Course> mCourses;
	
	public CourseAdapter(ArrayList<Course> courses, Activity activity)
	{
		super(activity, android.R.layout.simple_list_item_1, courses);
		mAppContext = activity;
		mCourses = courses;
	}
	
	@SuppressLint("InflateParams")
	@Override 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// If we weren't given a view, inflate one.
		if (convertView == null)
			convertView = mAppContext.getLayoutInflater()
				.inflate(R.layout.list_item_course, null);
		
		// Configure the view for this course.
		Course c = getItem(position);
		
		TextView nameTextView = 
				(TextView) convertView.findViewById(R.id.course_list_item_name);
		nameTextView.setText(c.getName());
		
		TextView moduleNameTextView = 
				(TextView) convertView.findViewById(R.id.course_list_item_module_name);
		moduleNameTextView.setText(c.getModule().getName());
		
		return convertView;
	}
	
	public void updateAdapter(ArrayList<Course> courses)
	{
		mCourses.clear();
		mCourses.addAll(courses);
		this.notifyDataSetChanged();
	}
}
