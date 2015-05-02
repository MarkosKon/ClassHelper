package com.example.classhelper.notused;
/** 
 * See the comment on ModelPagerFragment class.
*/
//package com.example.classhelper;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.ViewPager;
//
//public abstract class ModelPagerActivity extends FragmentActivity 
//{
//	protected ViewPager mViewPager;
//	protected ArrayList<Model> mModels;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		mViewPager = new ViewPager(this);
//		mViewPager.setId(R.id.viewPager);
//		setContentView(mViewPager);
//		
//		UUID modelId = (UUID) getIntent().getSerializableExtra(ModelPagerFragment.EXTRA_MODEL_ID);
//		for(int i = 0; i < mModels.size(); i++)
//		{
//			if(mModels.get(i).getId().equals(modelId))
//			{
//				mViewPager.setCurrentItem(i);
//				break;
//			}
//		}
//	}
//	
//}
