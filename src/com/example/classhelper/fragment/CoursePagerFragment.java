package com.example.classhelper.fragment;

import java.util.ArrayList;
import java.util.Collections;

import com.example.classhelper.R;
import com.example.classhelper.adapter.ModuleAdapter;
import com.example.classhelper.data.ModuleDAO;
import com.example.classhelper.model.Course;
import com.example.classhelper.model.Module;

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
 * functionality for the courses.
 */
public class CoursePagerFragment extends Fragment
	implements OnItemSelectedListener
{
	public static final String TAG = "CoursePagerFragment";
	public static final String EXTRA_COURSE = "com.example.classhelper.model.Course";
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
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				((AppCompatActivity)getActivity()).
					getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
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
		// We want to position current course's module on top of the list.
		int i = 0;
		for (Module m : modules)
		{
			if (mCourse.getModule().getId() == m.getId())
				Collections.swap(modules, 0, i);
			i++;
		}
		ModuleAdapter spinnerAdapter = new ModuleAdapter(modules, getActivity());
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
	public static CoursePagerFragment newInstance(Course course)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_COURSE, course);
		
		CoursePagerFragment fragment = new CoursePagerFragment();
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
		Module module = (Module) parent.getItemAtPosition(position);
		mCourse.setModule(module);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		
	}

}
