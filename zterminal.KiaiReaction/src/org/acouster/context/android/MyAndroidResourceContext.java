package org.acouster.context.android;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import org.acouster.android.context.AndroidContextBitmap;
import org.acouster.android.context.AndroidResourceContext;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ResourceContext;

import org.acouster.android.kiai.R;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

public class MyAndroidResourceContext extends AndroidResourceContext {

	private View view;
	private Activity activity;
	public MyAndroidResourceContext(View view, Activity activity) {
		super(view, activity);
		this.view = view;
		this.activity = activity;
	}
	
	//------------------------------------------------------
	// IFileSystemContext members/overrides

	@Override
	public ContextBitmap LoadBitmap(String resourceName)
	{
		Bitmap bbb = null;
		if (resourceName.equals("arrow_up.png")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.arrow_up); 
		else if (resourceName.equals("arrow_up_red.png")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.arrow_up_red);
//		else
//		{
//			Log.v("======>>>", "cannot find resource: >>" + resourceName + "<<");
//			bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.icon);
//		}
		//Log.v("======>>>", "bbb: >>" + bbb + "<<");
		return new AndroidContextBitmap(bbb);
	}

	@Override
	public <T> T LoadAny(Class<? extends T> clazz, String filename) throws IOException {
		// this is not used
		return null;
	}

	//------------------------------------------------------
	//------------------------------------------------------
	//------------------------------------------------------
	
}
