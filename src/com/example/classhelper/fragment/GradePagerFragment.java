package com.example.classhelper.fragment;

import java.util.ArrayList;
import java.util.Collections;

import com.example.classhelper.R;
import com.example.classhelper.adapter.StudentAdapter;
import com.example.classhelper.adapter.TestAdapter;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.model.Grade;
import com.example.classhelper.model.Student;
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
 * functionality for the grades.
 */
public class GradePagerFragment extends Fragment 
	implements OnItemSelectedListener
{
	public static final String TAG = "GradePagerFragment";
	public static final String EXTRA_GRADE = "com.example.classhelper.model.Grade";
	private Grade mGrade;
	
	private TextView mIdTextView;
	private EditText mValueEditText;
	private Spinner mStudentSpinner;
	private Spinner mTestSpinner;
	private Button mSaveButton;
	
	private Callbacks mCallbacks;
	
	public interface Callbacks
	{
		void onListItemUpdate(Grade grade);
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
		mGrade = (Grade) getArguments().getSerializable(EXTRA_GRADE);
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_grade, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				((AppCompatActivity)getActivity()).
					getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		mIdTextView = (TextView) v.findViewById(R.id.grade_id);
		mIdTextView.setText(mIdTextView.getText() + String.valueOf(mGrade.getId()));
		
		mValueEditText = (EditText) v.findViewById(R.id.grade_grade_value);
		mValueEditText.setText(String.valueOf(mGrade.getGradeValue()));
		mValueEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				if (s.length() > 0)
					mGrade.setGradeValue(Integer.parseInt(s.toString())); // oh boy.
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
		
		// Setup a spinner with the available last names of the students.
		mStudentSpinner = (Spinner) v.findViewById(R.id.grade_student_id);
		ArrayList<Student> students = StudentDAO.get(getActivity()).getAllStudents();
		
		// We want to position current's grade student on top of the list.
		int i =0;
		for (Student s : students)
		{
			if (mGrade.getStudent().getId() == s.getId())
				Collections.swap(students, 0, i);
			i++;
		}
		StudentAdapter studentAdapter = new StudentAdapter(students, getActivity());
		mStudentSpinner.setAdapter(studentAdapter);
		mStudentSpinner.setOnItemSelectedListener(this);
		
		
		// Setup a spinner with the available names of the tests.
		mTestSpinner = (Spinner) v.findViewById(R.id.grade_test_id);
		ArrayList<Test> tests = TestDAO.get(getActivity()).getAllTests();
		
		// Same here but for grade's test.
		i = 0;
		for (Test t : tests)
		{
			if (mGrade.getTest().getId() == t.getId())
				Collections.swap(tests, 0, i);
			i++;
		}
		TestAdapter testAdapter = new TestAdapter(tests, getActivity());
		mTestSpinner.setAdapter(testAdapter);
		mTestSpinner.setOnItemSelectedListener(this);
		
		
		mSaveButton = (Button) v.findViewById(R.id.grade_save);
		// The activity that hosts the fragment will decide what to do on button click.
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				mCallbacks.onListItemUpdate(mGrade);
			}
		});
		
		return v;
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 *  This is a convention where we want to attach arguments to
	 *  the fragment after is created but before is added to an activity.
	 */
	public static GradePagerFragment newInstance(Grade grade)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_GRADE, grade);
		
		GradePagerFragment fragment = new GradePagerFragment();
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
		Spinner spinner = (Spinner) parent;
		if (spinner.getId() == R.id.grade_student_id)
		{
			Student student = (Student) parent.getItemAtPosition(position);
			mGrade.setStudent(student);
		}
		else if (spinner.getId() == R.id.grade_test_id)
		{
			Test test = (Test) parent.getItemAtPosition(position);
			mGrade.setTest(test);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		
	}
}
