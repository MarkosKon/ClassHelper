package com.example.classhelper.data;

import java.util.ArrayList;

import com.example.classhelper.model.Module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This singleton class uses DatabaseHelper in order to interact 
 * with Module table.
 */
public class ModuleDAO 
{
	private static ModuleDAO sModuleDAO;
	private Context mAppContext;
	
	private static final String TABLE_MODULE = "module";
	private static final String COLUMN_MODULE_ID = "_id";
	private static final String COLUMN_MODULE_NAME = "name";
	
	public static ModuleDAO get(Context context)
	{
		if(sModuleDAO == null)
			sModuleDAO = new ModuleDAO(context);
		return sModuleDAO;
	}
	
	private ModuleDAO(Context context)
	{
		mAppContext = context;
	}
	
	public long insert(Module module)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_MODULE_NAME, module.getName());
		return DatabaseHelper.get(mAppContext)
				.getWritableDatabase().insert(TABLE_MODULE, null, values);
	}
	
	public void delete(Module module)
	{
		String selection = COLUMN_MODULE_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(module.getId())};
		DatabaseHelper.get(mAppContext)
			.getWritableDatabase().delete(TABLE_MODULE, selection, selectionArgs);
	}
	
	public int update(Module module)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_MODULE_NAME, module.getName());
		
		String selection = COLUMN_MODULE_ID + " LIKE ?";
		String[] selectionArgs = {String.valueOf(module.getId())};
		return DatabaseHelper.get(mAppContext)
				.getReadableDatabase()
				.update(TABLE_MODULE, values, selection, selectionArgs);
	}
	
	public Module getModuleById(long moduleId)
	{
		Module module = null;
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_MODULE, 
				null, // all columns.
				COLUMN_MODULE_ID + " = ?", // look for module id
				new String[]{String.valueOf(moduleId)}, // with this value
				null, // group by
				null, // having
				null, // order by
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			module = cursorToModule(cursor);
		
		return module;
	}
	
	public Module getModuleByName(String moduleName)
	{
		Module module = null;
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_MODULE, 
				null, // all columns.
				COLUMN_MODULE_NAME + " = ?", // look for module id
				new String[]{moduleName}, // with this value
				null, // group by
				null, // having
				null, // order by
				"1"); // limit 1 row
		if (cursor != null && cursor.moveToFirst())
			module = cursorToModule(cursor);
		
		return module;
	}
	
	public ArrayList<Module> getAllModules()
	{
		ArrayList<Module> modules = new ArrayList<Module>();
		Cursor cursor = DatabaseHelper.get(mAppContext)
							.getReadableDatabase().query(TABLE_MODULE, 
				null, 
				null, 
				null, 
				null, 
				null, 
				COLUMN_MODULE_NAME + " asc");
		if (cursor != null && cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				Module module = cursorToModule(cursor);
				modules.add(module);
				cursor.moveToNext();
			}
			cursor.close();
		}
		
		return modules;
	}
	
	protected Module cursorToModule(Cursor cursor)
	{
		Module module = new Module();
		module.setId(cursor.getLong(0));
		module.setName(cursor.getString(1));
		
		return module;
	}
}
