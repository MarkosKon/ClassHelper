package com.example.classhelper.fragment;

import java.util.ArrayList;

import com.example.classhelper.R;
import com.example.classhelper.myinterface.Callbacks;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * The ListFragment of all models are subclasses of ModelListFragment.
 * The class contains as much code as possible to avoid duplication.
 */ 
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
	
	/** 
	 * Return a simple list view.
	 */
	@TargetApi (11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		// If the device has hardware keyboard, the user can search the items on the list.
		mListView = (ListView)v.findViewById(android.R.id.list);
		mListView.setTextFilterEnabled(true);
		
		// Show the symbol < (navigate to parent activity) in Actionbar.
		((AppCompatActivity)getActivity())
			.getSupportActionBar().setDisplayHomeAsUpEnabled(true);	
		
		return v;
	}
	
	/**
	 *  We override this method to show the user a message when the ListView is empty.
	 */
	@Override
	public void onActivityCreated (Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setEmptyText(getResources().getString(R.string.empty_list_view));
	}
	
	/**
	 * This method sets the options menu. Pre-Honeycomb and
	 * Honeycomb +
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_model_list, menu);
	}
	
	/**
	 *  Respond to the < ActionBar item in Honeycomb+.
	 */
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
	 * This method sets a contextual action bar (Honeycomb+) 
	 * and a floating context menu (less than Honeycomb)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.model_list_item_context, menu);
	}
	
	/**
	 *  Subclasses override this method to change the title.
	 */
	protected abstract int getActivityTitle();
	
	/**
	 * Subclasses override this method to update their corresponding adapter.
	 */
	protected abstract void updateAdapter();
}
