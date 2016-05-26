package org.acouster.android.platinum;

import java.io.IOException;

import org.acouster.android.R;
import org.acouster.android.context.AndroidResourceContext;
import org.acouster.context.ContextBitmap;
import org.acouster.platinum.xml.Lesson;
import org.acouster.platinum.xml.menu.Lessons;
import org.acouster.simplexml.ObjectFactoryFunc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class MyResourceContext extends AndroidResourceContext {

	public static final String EXTERNAL_XML_PREFIX = ".Platinum/_xml/";
	public static final String LESSON_LISTING_DEFAULT_FRENCH_PATH = EXTERNAL_XML_PREFIX + "fra/lesson.xml";
	
	public MyResourceContext(Activity activity) {
		super(activity);
	}

	public Lessons LoadLessonsListing() throws IOException
	{
		return LoadLessonsListing(LESSON_LISTING_DEFAULT_FRENCH_PATH);
	}
	public Lessons LoadLessonsListing(String path) throws IOException
	{
		return LoadAnyExternal(Lessons.class, path);
	}
	
	public Lesson LoadLesson(String filename) throws IOException
	{
		return LoadAnyExternal(Lesson.class, filename);
	}
	
	// -------------- privates --------------
	
	public <T> T LoadAny(Class<? extends T> clazz, String filename) throws IOException
	{
//		InputStream stream = this.OpenAssetFile(filename);
//		T lesson = ObjectFactory.parseXml(clazz, stream);
//		stream.close();
//		return lesson;
		return LoadStructure(clazz, filename, new ObjectFactoryFunc<T>());
	}
	public <T> T LoadAnyExternal(Class<? extends T> clazz, String filename) throws IOException
	{
		return LoadStructureExternal(clazz, filename, new ObjectFactoryFunc<T>());
	}
	
	// --------- singleton -----------
	
	public static MyResourceContext myCurrent()
	{
		return (MyResourceContext)instance;
	}

	@Override
	public ContextBitmap LoadBitmapFromFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
