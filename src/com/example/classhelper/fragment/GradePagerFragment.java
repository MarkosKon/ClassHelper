package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class GradePagerFragment extends Fragment 
	implements OnItemSelectedListener
{
	public static final String TAG = "GradePagerFragment";
	public static final String EXTRA_GRADE = "com.example.criminalintent.grade";
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
		
		// p.325.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// p.327. the if on the statement inside the if.
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
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
		ArrayList<String> studentNames = new ArrayList<String>();
		for (Student s : students)
		{
			studentNames.add(s.getLastName());
		}
		if (mGrade.getStudent().getLastName() != null)
		{
			studentNames.remove(mGrade.getStudent().getLastName());
			studentNames.add(0, mGrade.getStudent().getLastName());
		}
		
		ArrayAdapter<String> spinnerAdapter	= new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, studentNames);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mStudentSpinner.setAdapter(spinnerAdapter);
		mStudentSpinner.setOnItemSelectedListener(this);
		
		// Setup a spinner with the available names of the tests.
		mTestSpinner = (Spinner) v.findViewById(R.id.grade_test_id);
		ArrayList<Test> tests = TestDAO.get(getActivity()).getAllTests();
		ArrayList<String> testNames = new ArrayList<String>();
		for (Test t : tests)
		{
			testNames.add(t.getName());
		}
		if (mGrade.getTest().getName() != null)
		{
			testNames.remove(mGrade.getTest().getName());
			testNames.add(0, mGrade.getTest().getName());
		}
		
		ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, testNames);
		spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mTestSpinner.setAdapter(spinnerAdapter1);
		mTestSpinner.setOnItemSelectedListener(this);
		
		mSaveButton = (Button) v.findViewById(R.id.grade_save);
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
	
	public static GradePagerFragment newInstance(Grade grade)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_GRADE, grade);
		
		GradePagerFragment fragment = new GradePagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) 
	{
		Spinner spinner = (Spinner) parent;
		if (spinner.getId() == R.id.grade_student_id)
		{
			Student student = StudentDAO.get(getActivity())
					.getStudentByLastName(parent.getItemAtPosition(position).toString());
			mGrade.setStudent(student);
		}
		else if (spinner.getId() == R.id.grade_test_id)
		{
			Test test = TestDAO.get(getActivity())
					.getTestByName(parent.getItemAtPosition(position).toString());
			mGrade.setTest(test);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
