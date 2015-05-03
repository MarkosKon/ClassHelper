package com.example.classhelper.myinterface;

/**
 * ModelList activities implement this interface to respond to 
 * a selection of a list item that was made from the fragment 
 * they are hosting.
 */
public interface Callbacks<T> 
{
	void onListItemSelected(T item);
}
