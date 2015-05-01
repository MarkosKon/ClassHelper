package com.example.classhelper.fragment;
/** 
 * Aborted because each concrete "ModelPagerFragment" was too specific for the superclass to exist.
*/

//package com.example.classhelper;
//
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.NavUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//public abstract class ModelPagerFragment extends Fragment 
//{
//	public static final String EXTRA_MODEL_ID = "com.example.criminalintent.model_id";
//	
//	protected Model mModel;
//	
//	@TargetApi(11)
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
//	{
//		View v = super.onCreateView(inflater, parent, savedInstanceState);
//		
//		// p.325.
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//		{
//			// p.327. the if on the statement inside the if.
//			if (NavUtils.getParentActivityName(getActivity()) != null)
//			{
//				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
//			}
//		}
//		
//		return v;
//	}
//}
