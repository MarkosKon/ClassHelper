package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.activity.CourseListActivity;
import com.example.classhelper.activity.GradeListActivity;
import com.example.classhelper.activity.ModuleListActivity;
import com.example.classhelper.activity.StudentListActivity;
import com.example.classhelper.activity.TestListActivity;
import com.example.classhelper.adapter.MainMenuAdapter;
import com.example.classhelper.data.CourseDAO;
import com.example.classhelper.data.ModuleDAO;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.model.Course;
import com.example.classhelper.model.MainMenuItem;
import com.example.classhelper.model.Module;
import com.example.classhelper.model.Student;
import com.example.classhelper.model.Test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuFragment extends ListFragment 
{
	public static final String TAG = "MainMenuFragment";
	
	private ArrayList<MainMenuItem> mMenuItems = new ArrayList<MainMenuItem>();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mMenuItems.add(new MainMenuItem(getResources().getString(R.string.module_crud), android.R.drawable.ic_menu_agenda));
		mMenuItems.add(new MainMenuItem(getResources().getString(R.string.course_crud), android.R.drawable.ic_menu_agenda));
		mMenuItems.add(new MainMenuItem(getResources().getString(R.string.student_crud), R.drawable.ic_menu_cc_am));
		mMenuItems.add(new MainMenuItem(getResources().getString(R.string.test_crud), R.drawable.ic_menu_compose));
		mMenuItems.add(new MainMenuItem(getResources().getString(R.string.grade_crud), android.R.drawable.ic_menu_edit));
		
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.main_menu_title);
		

		MainMenuAdapter adapter = new MainMenuAdapter(mMenuItems, getActivity());
		setListAdapter(adapter);
		
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		// If the device has hardware keyboard, the user can search the items.
		ListView listView = (ListView) v.findViewById(android.R.id.list);
		listView.setTextFilterEnabled(true); 
		
		return v;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		MainMenuItem m = (MainMenuItem) getListAdapter().getItem(position);
		
		if (m.getTitle().equals(getResources().getString(R.string.student_crud)))
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
		else if(m.getTitle().equals(getResources().getString(R.string.test_crud)))
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
		else if(m.getTitle().equals(getResources().getString(R.string.grade_crud)))
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
		else if(m.getTitle().equals(getResources().getString(R.string.course_crud)))
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
		else if(m.getTitle().equals(getResources().getString(R.string.module_crud)))
		{
			Intent i = new Intent(getActivity(), ModuleListActivity.class);
			startActivity(i);
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
}