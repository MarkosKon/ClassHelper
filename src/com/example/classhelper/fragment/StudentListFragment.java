package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.model.Student;
import com.example.classhelper.myinterface.Callbacks;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class StudentListFragment extends ModelListFragment<Student> 
{
	public static final String TAG = "StudentListFragment";
	private Callbacks<Student> mCallbacks;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mCallbacks = (Callbacks<Student>) activity;
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
		
		updateAdapter();
	}
	
	// p.332.
	@TargetApi (11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		// p.349. 
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		
		// p.353.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			// Use floating context menus on Froyo and Gingerbread. See method onContextItemSelected for more.
			registerForContextMenu(listView);
		}
		else
		{
			// Use contextual action bar on Honeycomb and higher.
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.model_list_item_context, menu);
					return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					switch(item.getItemId())
					{
						case R.id.menu_item_delete_model:
							StudentAdapter adapter = (StudentAdapter) getListAdapter();
							for (int i = adapter.getCount() - 1; i >= 0; i--)
							{
								if (getListView().isItemChecked(i))
									StudentDAO.get(getActivity()).delete(adapter.getItem(i));
							}
							updateAdapter();
							mode.finish();
							adapter.notifyDataSetChanged();
							return true;
						default:
							return false;
					}
				}
				
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return v;
	}
	
	// p.227. The commented out stuff, the new at p.445.
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Student s = ((StudentAdapter) getListAdapter()).getItem(position);
		mCallbacks.onListItemSelected(s);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		updateAdapter();
	}
	
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.menu_item_new_model:
				Student student = new Student();
				mCallbacks.onListItemSelected(student);
				return true;
			default:
				return super.onOptionsItemSelected(item);
					
		}
	}
	
	// p.351. In this method we handle the context menu for android versions lesser that Honeycomb.
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		StudentAdapter adapter = (StudentAdapter) getListAdapter();
		
		switch (item.getItemId())
		{
			case R.id.menu_item_delete_model:
				StudentDAO.get(getActivity()).delete(adapter.getItem(position));
				updateAdapter();
				return true;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public int getActivityTitle()
	{
		return R.string.student_crud;
	}
	
	/**
	 * More like a create a new adapter and less update (notifyDataSetChanged method). This is
	 * because when we create or edit a model from PagerFragment
	 * we do not know what happened to the database. It is not elegant
	 * but i can't think of something better.
	 */
	public void updateAdapter()
	{
		mModel = StudentDAO.get(getActivity()).getAllStudents();
		StudentAdapter studentAdapter = new StudentAdapter(mModel);
		setListAdapter(studentAdapter);
	}
	
	private class StudentAdapter extends ArrayAdapter<Student>
	{
		public StudentAdapter(ArrayList<Student> students)
		{
			super(getActivity(), android.R.layout.simple_list_item_1, students);
		}
		
		@SuppressLint("InflateParams")
		@Override 
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// If we weren't given a view, inflate one.
			if (convertView == null)
				convertView = getActivity().getLayoutInflater()
					.inflate(R.layout.list_item_student, null);
			
			// Configure the view for this crime.
			Student s = getItem(position);
			
			TextView firstNameTextView = 
					(TextView) convertView.findViewById(R.id.student_list_item_firstName);
			firstNameTextView.setText(s.getFirstName());
			
			TextView lastNameTextView = 
					(TextView) convertView.findViewById(R.id.student_list_item_lastName);
			lastNameTextView.setText(s.getLastName());
			
			return convertView;
		}
	}
}
