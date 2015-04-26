package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.myinterface.Callbacks;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

// All models ListFragment are subclasses of this class.
public abstract class ModelListFragment<T> extends ListFragment 
{
	protected ListView mListView;
	
	protected ArrayList<T> mModel;
	
	protected Callbacks<T> mCallbacks;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		
		getActivity().setTitle(getActivityTitle());
		
		setRetainInstance(true);
	}
	
	// p.325.
	@TargetApi (11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		// If the device has hardware keyboard, the user can search the items on the list.
		mListView = (ListView)v.findViewById(android.R.id.list);
		mListView.setTextFilterEnabled(true); 
		
		// p.325 This is to show the < actionbar item in Honeycomb +.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// p.327. the if on the statement inside the if.
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		return v;
	}
	
	// We override this method to show the user a message if the listview is empty.
	@Override
	public void onActivityCreated (Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setEmptyText(getResources().getString(R.string.empty_list_view));
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_model_list, menu);
	}
	
	// We use this method to respond to the < actionbar item in Honeycomb+.
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
	
	// p.348.
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		getActivity().getMenuInflater().inflate(R.menu.model_list_item_context, menu);
	}
	
	// Subclasses override this method to change title.
	protected abstract int getActivityTitle();
}
