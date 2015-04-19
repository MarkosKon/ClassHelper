package com.example.classhelper.myinterface;

/**
 * Required interface for hosting activities. p.443.
 */

public interface Callbacks<T> 
{
	void onListItemSelected(T item);
	// TODO onListItemUpdate(T item); It is for when returning from ModelPagerFragment.
}
