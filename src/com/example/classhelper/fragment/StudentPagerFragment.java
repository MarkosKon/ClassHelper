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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * The purpose of this class is to provide create, read and update
 * functionality for the students.
 */
public class StudentPagerFragment extends Fragment 
	implements OnItemSelectedListener
{
	public static final String TAG = "StudentPagerFragment";
	public static final String EXTRA_STUDENT = "com.example.classhelper.model.Student";
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
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				((AppCompatActivity)getActivity()).
					getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_email, menu);
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
			case R.id.menu_item_send_email:
				Intent i = new Intent(getActivity(), EmailActivity.class);
				String[] recipientList = new String[]{mStudent.getEmail()};
				i.putExtra(EmailFragment.EXTRA_RECIPIENT_LIST, recipientList);
				startActivity(i);
				return true;
			case R.id.menu_item_create_report:
				try 
				{
					PDFHelper pdfHelper = new PDFHelper(mStudent, getActivity());
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
	public static StudentPagerFragment newInstance(Student student)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_STUDENT, student);
		
		StudentPagerFragment fragment = new StudentPagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	/**
	 * Spinner listener methods.
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
