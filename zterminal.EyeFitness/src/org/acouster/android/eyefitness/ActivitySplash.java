package org.acouster.android.eyefitness;

import org.acouster.android.ui.ActivityAcousterSplashAnim;
import org.acouster.android.ui.ActivityAcousterSplash;


import android.os.Bundle;

 
public class ActivitySplash extends ActivityAcousterSplashAnim
{
	public ActivitySplash() {
		super(ActivityMenu.class);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// needed to init resource context
		BaseActivity.commonOnCreate(this);
		super.onCreate(savedInstanceState);
	}
}

