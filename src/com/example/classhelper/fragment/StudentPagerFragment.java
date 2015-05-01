package com.example.classhelper.fragment;

import java.util.ArrayList;
import java.util.Collections;

import com.example.classhelper.R;
import com.example.classhelper.activity.EmailActivity;
import com.example.classhelper.adapter.ModuleAdapter;
import com.example.classhelper.data.ModuleDAO;
import com.example.classhelper.data.PDFHelper;
import com.example.classhelper.model.Module;
import com.example.classhelper.model.Student;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class StudentPagerFragment extends Fragment 
	implements OnItemSelectedListener
{
	public static final String TAG = "StudentPagerFragment";
	public static final String EXTRA_STUDENT = "com.example.classhelper.model.student";
	private Student mStudent; 
	
	private TextView mIdTextView;
	private EditText mFirstNameEditText;
	private EditText mLastNameEditText;
	private EditText mPhoneNumberEditText;
	private EditText mEmailEditText;
	private Spinner mSpinner;
	private Button mSaveButton;
	
	private Callbacks mCallbacks;
	
	public interface Callbacks
	{
		void onListItemUpdate(Student student);
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
		
		mStudent = (Student) getArguments().getSerializable(EXTRA_STUDENT);
		
		setHasOptionsMenu(true);
		
		setRetainInstance(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_student, parent, false);
		
		// p.325.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// p.327. the if on the statement inside the if.
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		mIdTextView = (TextView) v.findViewById(R.id.student_id);
		mIdTextView.setText(mIdTextView.getText() + String.valueOf(mStudent.getId()));
		
		mFirstNameEditText = (EditText) v.findViewById(R.id.student_first_name);
		mFirstNameEditText.setText(mStudent.getFirstName());
		mFirstNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mStudent.setFirstName(s.toString());
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
		
		mLastNameEditText = (EditText) v.findViewById(R.id.student_last_name);
		mLastNameEditText.setText(mStudent.getLastName());
		mLastNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				mStudent.setLastName(s.toString());
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
		
		mPhoneNumberEditText = (EditText) v.findViewById(R.id.student_phone_number);
		mPhoneNumberEditText.setText(mStudent.getPhoneNumber());
		mPhoneNumberEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				mStudent.setPhoneNumber(s.toString());
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
		
		mEmailEditText = (EditText) v.findViewById(R.id.student_email);
		mEmailEditText.setText(mStudent.getEmail());
		mEmailEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				mStudent.setEmail(s.toString());
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
		
		mSpinner = (Spinner) v.findViewById(R.id.student_module_id);
		ArrayList<Module> modules = ModuleDAO.get(getActivity()).getAllModules();
		int i = 0;
		for (Module m : modules)
		{
			if (mStudent.getModule().getId() == m.getId())
				Collections.swap(modules, 0, i);
			i++;
		}
		ModuleAdapter spinnerAdapter = new ModuleAdapter(modules, getActivity());
		mSpinner.setAdapter(spinnerAdapter);
		mSpinner.setOnItemSelectedListener(this);
		
		mSaveButton = (Button) v.findViewById(R.id.student_save);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				mCallbacks.onListItemUpdate(mStudent);
			}
		});
		
		return v;
	}
	
	/**
	 * This method sets the options menu. Pre-Honeycomb and
	 * Honeycomb +
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_email, menu);
		inflater.inflate(R.menu.fragment_report, menu);
		MenuItem emailItem = menu.findItem(R.id.menu_item_send_email);
		MenuItemCompat.setShowAsAction(emailItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
		MenuItem reportItem = menu.findItem(R.id.menu_item_create_report);
		MenuItemCompat.setShowAsAction(reportItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
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
			case R.id.menu_item_send_email:
				Intent i = new Intent(getActivity(), EmailActivity.class);
				String[] recipientList = new String[]{mStudent.getEmail()};
				i.putExtra(EmailFragment.EXTRA_RECIPIENT_LIST, recipientList);
				startActivity(i);
				return true;
			case R.id.menu_item_create_report:
				PDFHelper pdfHelper = new PDFHelper(mStudent, getActivity());
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	// TODO: find why we create a fragment this way.
	public static StudentPagerFragment newInstance(Student student)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_STUDENT, student);
		
		StudentPagerFragment fragment = new StudentPagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	/**
	 * Spinner listeners from implemented interface.
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) 
	{	
		Module module = (Module) parent.getItemAtPosition(position);
		mStudent.setModule(module);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		
	}
}
