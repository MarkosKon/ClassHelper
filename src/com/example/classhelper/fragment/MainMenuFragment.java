package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.activity.CourseListActivity;
import com.example.classhelper.activity.GradeListActivity;
import com.example.classhelper.activity.ModuleListActivity;
import com.example.classhelper.activity.StudentListActivity;
import com.example.classhelper.activity.TestListActivity;
import com.example.classhelper.data.CourseDAO;
import com.example.classhelper.data.ModuleDAO;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.model.Course;
import com.example.classhelper.model.Module;
import com.example.classhelper.model.Student;
import com.example.classhelper.model.Test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuFragment extends ListFragment 
{
	public static final String TAG = "MainMenuFragment";
	
	private ArrayList<String> mMenuItems = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mMenuItems.add(getResources().getString(R.string.module_crud));
		mMenuItems.add(getResources().getString(R.string.course_crud));
		mMenuItems.add(getResources().getString(R.string.student_crud));
		mMenuItems.add(getResources().getString(R.string.test_crud));
		mMenuItems.add(getResources().getString(R.string.grade_crud));
		
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.main_menu_title);
		
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, mMenuItems);
		setListAdapter(adapter);
		
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = super.onCreateView(inflater, container, savedInstanceState);
		return v;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		String s = (String) getListAdapter().getItem(position);
		if (s.equals("CRUD Student"))
		{
			ArrayList<Module> modules = ModuleDAO.get(getActivity()).getAllModules();
			if (modules.size() == 0)
			{
				Toast toast = Toast
						.makeText(getActivity(), "Create some modules first", Toast.LENGTH_SHORT);
				toast.show();
				
			}
			else
			{
				Intent i = new Intent(getActivity(), StudentListActivity.class);
				startActivity(i);
			}
		}
		else if(s.equals("CRUD Test"))
		{
			ArrayList<Course> courses = CourseDAO.get(getActivity()).getAllCourses();
			if (courses.size() == 0)
			{
				Toast toast = Toast
						.makeText(getActivity(), "Create some courses first", Toast.LENGTH_SHORT);
				toast.show();
				
			}
			else
			{
				Intent i = new Intent(getActivity(), TestListActivity.class);
				startActivity(i);
			}
		}
		else if(s.equals("CRUD Grade"))
		{
			ArrayList<Student> students = StudentDAO.get(getActivity()).getAllStudents();
			ArrayList<Test> tests = TestDAO.get(getActivity()).getAllTests();
			if (students.size() == 0)
			{
				Toast toast = Toast
						.makeText(getActivity(), "Create some students first", Toast.LENGTH_SHORT);
				toast.show();
			}
			else if (tests.size() == 0)
			{
				Toast toast = Toast
						.makeText(getActivity(), "Create some tests first", Toast.LENGTH_SHORT);
				toast.show();
			}
			else
			{
				Intent i = new Intent(getActivity(), GradeListActivity.class);
				startActivity(i);
			}
		}
		else if(s.equals("CRUD Course"))
		{
			ArrayList<Module> modules = ModuleDAO.get(getActivity()).getAllModules();
			if (modules.size() == 0)
			{
				Toast toast = Toast
						.makeText(getActivity(), "Create some modules first", Toast.LENGTH_SHORT);
				toast.show();
			}
			else
			{
				Intent i = new Intent(getActivity(), CourseListActivity.class);
				startActivity(i);	
			}
		}
		else if(s.equals("CRUD Module"))
		{
			Intent i = new Intent(getActivity(), ModuleListActivity.class);
			startActivity(i);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onResume()
	{
		super.onResume();
		((ArrayAdapter<String>) getListAdapter()).notifyDataSetChanged();
	}
}
