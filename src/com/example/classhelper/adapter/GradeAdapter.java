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
import com.example.classhelper.model.Grade;

public class GradeAdapter extends ArrayAdapter<Grade> implements Filterable
{
	private Activity mAppContext;
	
	public GradeAdapter(ArrayList<Grade> grades, Activity activity)
	{
		super(activity, android.R.layout.simple_list_item_1, grades);
		mAppContext = activity;
	}
	
	@SuppressLint("InflateParams")
	@Override 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// If we weren't given a view, inflate one.
		if (convertView == null)
			convertView = mAppContext.getLayoutInflater()
				.inflate(R.layout.list_item_grade, null);
		
		// Configure the view for this grade.
		Grade g = getItem(position);
		
		TextView studentFirstNameTextView = 
				(TextView) convertView.findViewById(R.id.grade_list_item_student_firstName);
		studentFirstNameTextView.setText(g.getStudent().getFirstName());
		
		TextView studentLastNameTextView = 
				(TextView) convertView.findViewById(R.id.grade_list_item_student_lastName);
		studentLastNameTextView.setText(g.getStudent().getLastName());
		
		TextView courseNameTextView = 
				(TextView) convertView.findViewById(R.id.grade_list_item_course_name);
		courseNameTextView.setText(g.getTest().getCourse().getName());
		
		TextView testNameTextView = 
				(TextView) convertView.findViewById(R.id.grade_list_item_test_name);
		testNameTextView.setText(g.getTest().getName());
		
		TextView gradeTextView = 
				(TextView) convertView.findViewById(R.id.grade_list_item_value);
		gradeTextView.setText(String.valueOf(g.getGradeValue()));
		
		return convertView;
	}
}
