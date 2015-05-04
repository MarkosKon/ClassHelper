package com.example.classhelper.fragment;

import com.example.classhelper.R;
import com.example.classhelper.adapter.TestAdapter;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.model.Test;
import com.example.classhelper.myinterface.Callbacks;

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
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * The purpose of this class is to present to the user the available 
 * tests and delete them.
 */
public class TestListFragment extends ModelListFragment<Test> 
{
	public static final String TAG = "TestListFragment";
	
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
		
		mModel = TestDAO.get(getActivity()).getAllTests();
		
		TestAdapter testAdapter = new TestAdapter(mModel, getActivity());
		setListAdapter(testAdapter);
	}
	
	@TargetApi (11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			// Use floating context menus on Froyo and Gingerbread. See method onContextItemSelected for more.
			registerForContextMenu(mListView);
		}
		else
		{
			// Use contextual action bar on Honeycomb and higher.
			mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
				
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
	
	/**
	 * The activity that hosts the fragment responds to the selection of
	 * a list item.
	 */
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
	
	/**
	 * The activity that hosts the fragment responds to the options menu selection.
	 */
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
	
	/**
	 *  In this method we handle the floating context menu for android versions 
	 *  less than Honeycomb.
	 */
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
	
	@Override
	protected int getActivityTitle() 
	{
		return R.string.test_crud;
	}
	
	/**
	 * Update fragment's test list and TestAdapter's test list.
	 */
	@Override
	public void updateAdapter()
	{
		mModel = TestDAO.get(getActivity()).getAllTests();
		((TestAdapter) getListAdapter()).updateAdapter(mModel);
	}
}
