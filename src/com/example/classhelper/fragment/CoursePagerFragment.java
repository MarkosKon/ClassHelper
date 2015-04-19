package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.ModuleDAO;
import com.example.classhelper.model.Course;
import com.example.classhelper.model.Module;

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

public class CoursePagerFragment extends Fragment
	implements OnItemSelectedListener
{
	public static final String TAG = "CoursePagerFragment";
	public static final String EXTRA_COURSE = "com.example.criminalintent.course";
	private Course mCourse;
	
	private TextView mIdTextView;
	private EditText mNameEditText;
	private Spinner mSpinner;
	private Button mSaveButton;
	
	private Callbacks mCallbacks;
	
	public interface Callbacks
	{
		void onListItemUpdate(Course course);
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
		mCourse = (Course) getArguments().getSerializable(EXTRA_COURSE);
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_course, parent, false);
		
		// p.325.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// p.327. the if on the statement inside the if.
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		mIdTextView = (TextView) v.findViewById(R.id.course_id);
		mIdTextView.setText(mIdTextView.getText() + String.valueOf(mCourse.getId()));
		
		mNameEditText = (EditText) v.findViewById(R.id.course_name);
		mNameEditText.setText(mCourse.getName());
		mNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCourse.setName(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) 
			{
				
			}
			
			@Override
			public void afterTextChanged(Editable s) 
			{
				
			}
		});
		
		mSpinner = (Spinner) v.findViewById(R.id.course_module_id);
		ArrayList<Module> modules = ModuleDAO.get(getActivity()).getAllModules();
		ArrayList<String> moduleNames = new ArrayList<String>();
		for (Module m : modules)
		{
			moduleNames.add(m.getName());
		}
		if (mCourse.getModule().getName() != null)
		{
			moduleNames.remove(mCourse.getModule().getName());
			moduleNames.add(0, mCourse.getModule().getName());
		}
		
		ArrayAdapter<String> spinnerAdapter	= new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, moduleNames);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(spinnerAdapter);
		mSpinner.setOnItemSelectedListener(this);
		
		mSaveButton = (Button) v.findViewById(R.id.course_save);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				mCallbacks.onListItemUpdate(mCourse);
			}
		});
		
		return v;
	}
	
	// p.326.
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
	
	public static CoursePagerFragment newInstance(Course course)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_COURSE, course);
		
		CoursePagerFragment fragment = new CoursePagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) 
	{
		Module module = ModuleDAO.get(getActivity())
				.getModuleByName(parent.getItemAtPosition(position).toString());
		mCourse.setModule(module);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		
	}

}
