package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.model.Test;
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

public class TestListFragment extends ModelListFragment<Test> 
{
	public static final String TAG = "TestListFragment";
	private Callbacks<Test> mCallbacks;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mCallbacks = (Callbacks<Test>) activity;
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
							TestAdapter adapter = (TestAdapter) getListAdapter();
							for (int i = adapter.getCount() - 1; i >= 0; i--)
							{
								if (getListView().isItemChecked(i))
									TestDAO.get(getActivity()).delete(adapter.getItem(i));
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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Test t = ((TestAdapter) getListAdapter()).getItem(position);
		mCallbacks.onListItemSelected(t);
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
				Test test = new Test();
				mCallbacks.onListItemSelected(test);
				return true;
			default:
				return super.onOptionsItemSelected(item);
					
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		TestAdapter adapter = (TestAdapter) getListAdapter();
		
		switch (item.getItemId())
		{
			case R.id.menu_item_delete_model:
				TestDAO.get(getActivity()).delete(adapter.getItem(position));
				updateAdapter();
				return true;
		}
		return super.onContextItemSelected(item);
	}
	
	private void updateAdapter()
	{
		mModel = TestDAO.get(getActivity()).getAllTests();
		TestAdapter testAdapter = new TestAdapter(mModel);
		setListAdapter(testAdapter);
	}
	
	@Override
	protected int getActivityTitle() 
	{
		return R.string.test_crud;
	}
	
	private class TestAdapter extends ArrayAdapter<Test>
	{
		public TestAdapter(ArrayList<Test> tests)
		{
			super(getActivity(), android.R.layout.simple_list_item_1, tests);
		}
		
		@SuppressLint("InflateParams")
		@Override 
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
				convertView = getActivity().getLayoutInflater()
					.inflate(R.layout.list_item_test, null);
			
			Test t = getItem(position);
			
			TextView nameTextView = 
					(TextView) convertView.findViewById(R.id.test_list_item_name);
			nameTextView.setText(t.getName());
			
			return convertView;
		}
	}
}