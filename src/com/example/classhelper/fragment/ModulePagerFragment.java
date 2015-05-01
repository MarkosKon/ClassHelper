package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.activity.EmailActivity;
import com.example.classhelper.data.StudentDAO;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModulePagerFragment extends Fragment 
{
	public static final String EXTRA_MODULE = "com.example.criminalintent.module";
	private Module mModule;
	
	private TextView mIdTextView;
	private EditText mNameEditText;
	private Button mSaveButton;
	
	private CallBacks mCallbacks;
	
	public interface CallBacks
	{
		void onListItemUpdate(Module module);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mCallbacks = (CallBacks) activity;
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
		mModule = (Module) getArguments().getSerializable(EXTRA_MODULE);
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_module, parent, false);
		
		// p.325.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// p.327. the if on the statement inside the if.
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		mIdTextView = (TextView) v.findViewById(R.id.module_id);
		mIdTextView.setText(mIdTextView.getText() + String.valueOf(mModule.getId()));
		
		mNameEditText = (EditText) v.findViewById(R.id.module_name);
		mNameEditText.setText(mModule.getName());
		mNameEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mModule.setName(s.toString());
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
		
		mSaveButton = (Button) v.findViewById(R.id.module_save);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				mCallbacks.onListItemUpdate(mModule);
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
		MenuItem emailItem = menu.findItem(R.id.menu_item_send_email);
		MenuItemCompat.setShowAsAction(emailItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
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
				ArrayList<Student> students = new ArrayList<Student>();
				students = StudentDAO.get(getActivity())
						.getStudentsByModule(mModule.getId());
				ArrayList<String> emails = new ArrayList<String>();
				for (Student s : students)
				{
					emails.add(s.getEmail());
				}
				String[] recipientList = new String[emails.size()];
				recipientList = emails.toArray(recipientList);
				i.putExtra(EmailFragment.EXTRA_RECIPIENT_LIST, recipientList);
				startActivity(i);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public static ModulePagerFragment newInstance(Module module)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_MODULE, module);
		
		ModulePagerFragment fragment = new ModulePagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
