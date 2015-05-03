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
import com.example.classhelper.model.Student;

/**
 * A custom adapter class used by StudentListFragment and spinners.
 */
public class StudentAdapter extends ArrayAdapter<Student> implements Filterable
{
	private Activity mAppContext;
	private ArrayList<Student> mStudents;
	
	public StudentAdapter(ArrayList<Student> students, Activity activity)
	{
		super(activity, android.R.layout.simple_list_item_1, students);
		mAppContext = activity;
		mStudents = students;
	}
	
	@SuppressLint("InflateParams")
	@Override 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// If we weren't given a view, inflate one.
		if (convertView == null)
			convertView = mAppContext.getLayoutInflater()
				.inflate(R.layout.list_item_student, null);
		
		// Configure the view for this student.
		Student s = getItem(position);
		
		TextView firstNameTextView = 
				(TextView) convertView.findViewById(R.id.student_list_item_firstName);
		firstNameTextView.setText(s.getFirstName());
		
		TextView lastNameTextView = 
				(TextView) convertView.findViewById(R.id.student_list_item_lastName);
		lastNameTextView.setText(s.getLastName());
		
		return convertView;
	}
	
	public void updateAdapter(ArrayList<Student> students)
	{
		mStudents.clear();
		mStudents.addAll(students);
		this.notifyDataSetChanged();
	}
}
