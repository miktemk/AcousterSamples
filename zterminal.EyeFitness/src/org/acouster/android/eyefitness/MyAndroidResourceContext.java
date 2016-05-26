package org.acouster.android.eyefitness;

import java.io.IOException;

import org.acouster.DebugUtil;
import org.acouster.android.context.AndroidContextBitmap;
import org.acouster.android.context.AndroidResourceContext;
import org.acouster.context.ContextBitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MyAndroidResourceContext extends AndroidResourceContext {

	private Activity activity;
	public MyAndroidResourceContext(Activity activity) {
		super(activity);
		this.activity = activity;
	}
	
	//------------------------------------------------------
	// IFileSystemContext members/overrides

	@Override
	public ContextBitmap LoadBitmap(String resource, boolean preciseSize)
	{
		//Log.v(DebugUtil.TAG, "---> LoadBitmap " + resource);
		Bitmap bbb = loadBitmapByName(resource, preciseSize);
		if (bbb == null)
		{
			Log.v("======>>>", "cannot find resource: >>" + resource + "<<");
			bbb = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher);
		}
		
		return new AndroidContextBitmap(bbb);
	}
	
	public ContextBitmap LoadBitmapFromResource(int id)
	{
		return LoadBitmapFromResource(id, R.drawable.ic_launcher);
	}
	
	// -------------- privates --------------
	
	@Override
	public <T> T LoadAny(Class<? extends T> clazz, String filename)
			throws IOException {
		return null;
	}
	@Override
	public <T> T LoadAnyExternal(Class<? extends T> clazz, String filename)
			throws IOException {
		return null;
	}
	
	// --------- singleton -----------
	
	public static MyAndroidResourceContext myCurrent()
	{
		return (MyAndroidResourceContext)instance;
	}

	public static void initIfNotReady(Activity activity)
	{
		if (MyAndroidResourceContext.instance() == null)
			new MyAndroidResourceContext(activity).makeInstance();
	}


	//------------------------------------------------------
	//------------------------------------------------------
	//------------------------------------------------------
	
}
