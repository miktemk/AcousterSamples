package org.acouster.lingorepeater;

import java.io.IOException;

import org.acouster.android.context.AndroidResourceContext;
import org.acouster.context.ContextBitmap;
import org.acouster.simplexml.ObjectFactoryFunc;

import android.app.Activity;

public class MyAndroidResourceContext extends AndroidResourceContext {

	public MyAndroidResourceContext(Activity activity) {
		super(activity);
	}

	@Override
	public <T> T LoadAny(Class<? extends T> clazz, String filename) throws IOException {
		return LoadStructure(clazz, filename, new ObjectFactoryFunc<T>());
	}
	@Override
	public <T> T LoadAnyExternal(Class<? extends T> clazz, String filename) throws IOException {
		return LoadStructureExternal(clazz, filename, new ObjectFactoryFunc<T>());
	}

	@Override
	public ContextBitmap LoadBitmapFromFile(String arg0) {
		return null;
	}
	
	public static void initIfNotReady(Activity activity)
	{
		if (MyAndroidResourceContext.instance() == null)
			new MyAndroidResourceContext(activity).makeInstance();
	}

}
