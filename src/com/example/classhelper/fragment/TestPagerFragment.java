package com.example.classhelper.fragment;

import java.util.ArrayList;
import java.util.Collections;

import com.example.classhelper.R;
import com.example.classhelper.adapter.CourseAdapter;
import com.example.classhelper.data.CourseDAO;
import com.example.classhelper.data.PDFHelper;
import com.example.classhelper.model.Course;
import com.example.classhelper.model.Test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * The purpose of this class is to provide create, read and update
 * functionality for the tests.
 */
public class TestPagerFragment extends Fragment
	implements OnItemSelectedListener
{
	public static final String TAG = "TestPagerFragment";
	public static final String EXTRA_TEST = "com.example.classhelper.model.Test";
	private Test mTest;
	
	private TextView mIdTextView;
	private EditText mNameEditText;
	private EditText mNotesEditText;
	private Spinner mSpinner;
	private Button mSaveButton;
	
	private Callbacks mCallbacks;
	
	public interface Callbacks
	{
		void onListItemUpdate(Test test);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mTest = (Test) getArguments().getSerializable(EXTRA_TEST);
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_test, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				((AppCompatActivity)getActivity()).
					getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		mIdTextView = (TextView) v.findViewById(R.id.test_id);
		mIdTextView.setText(mIdTextView.getText() + String.valueOf(mTest.getId()));
		
		mNameEditText = (EditText) v.findViewById(R.id.test_name);
		mNameEditText.setText(mTest.getName());
		mNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mTest.setName(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mNotesEditText = (EditText) v.findViewById(R.id.test_notes);
		mNotesEditText.setText(mTest.getNotes());
		mNotesEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				mTest.setNotes(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mSpinner = (Spinner) v.findViewById(R.id.test_course_id);
		ArrayList<Course> courses = CourseDAO.get(getActivity()).getAllCourses();
		// We want to position current test's course on top of the list.
		int i = 0;
		for (Course c : courses)
		{
			if (mTest.getCourse().getId() == c.getId())
				Collections.swap(courses, 0, i);
			i++;
		}
		CourseAdapter spinnerAdapter	= new CourseAdapter(courses, getActivity());
		mSpinner.setAdapter(spinnerAdapter);
		mSpinner.setOnItemSelectedListener(this);
		
		mSaveButton = (Button) v.findViewById(R.id.test_save);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				mCallbacks.onListItemUpdate(mTest);
			}
		});
		
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_report, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null)
					NavUtils.navigateUpFromSameTask(getActivity());
				return true;
			case R.id.menu_item_create_report:
				try 
				{
					PDFHelper pdfHelper = new PDFHelper(mTest, getActivity());
					pdfHelper.createReport();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 *  This is a convention where we want to attach arguments to
	 *  the fragment after is created but before is added to an activity.
	 */
	public static TestPagerFragment newInstance(Test test)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TEST, test);
		
		TestPagerFragment fragment = new TestPagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	/**
	 *  Spinner Listener Methods. 
	*/
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) 
	{
		Course course = (Course) parent.getItemAtPosition(position);
		mTest.setCourse(course);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		
	}

}
