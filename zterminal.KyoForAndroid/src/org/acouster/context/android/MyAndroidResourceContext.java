package org.acouster.context.android;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import org.acouster.IFunc;
import org.acouster.android.context.AndroidContextBitmap;
import org.acouster.android.context.AndroidResourceContext;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ResourceContext;
import org.acouster.kyo.xml.Avatar;
import org.acouster.simplexml.ObjectFactory;
import org.acouster.simplexml.ObjectFactoryFunc;
import org.acouster.xml.Menu.Menu;
import org.acouster.android.kyo.R;
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
//		if (resourceName.equals("mike_center.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_center); 
//		else if (resourceName.equals("mike_center2.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_center2); 
//		else if (resourceName.equals("mike_left1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_left1); 
//		else if (resourceName.equals("mike_left2.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_left2); 
//		else if (resourceName.equals("mike_right1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_right1); 
//		else if (resourceName.equals("mike_right2.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_right2);
//		else if (resourceName.equals("mike_gl1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_gl1);
//		else if (resourceName.equals("mike_gl2.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_gl2);
//		else if (resourceName.equals("mike_gr1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_gr1);
//		else if (resourceName.equals("mike_gr2.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_gr2);
//		else if (resourceName.equals("mike_ngl1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_ngl1);
//		else if (resourceName.equals("mike_ngr1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_ngr1);
//		else if (resourceName.equals("mike_tip1.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_tip1);
//		else if (resourceName.equals("mike_tip2.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_tip2);
//		else if (resourceName.equals("mike_tip3.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_tip3);
//		else if (resourceName.equals("mike_tip4.jpg")) bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.mike_tip4);
//		else
//		{
//			Log.v("======>>>", "cannot find resource: >>" + resourceName + "<<");
//			bbb = BitmapFactory.decodeResource(view.getResources(), R.drawable.icon);
//		}
		Log.v("======>>>", "bbb: >>" + bbb + "<<");
		return new AndroidContextBitmap(bbb);
	}

	//----------- assets -------------------------------------------
	
	public Menu LoadMenu() throws IOException
	{
		return LoadAny(Menu.class, XML_ASSET_FOLDER + "/" + Menu.MENU_XML_FILENAME);
	}
	
	public Avatar LoadAndCompileAvatar(String filename, IFunc<String, ContextBitmap> id2image) throws IOException
	{
		Avatar avatar = LoadAny(Avatar.class, filename);
		compileAvatar(avatar, id2image);
		return avatar;
	}
	
	// -------------- privates --------------
	
	// TODO: move upstairs??
	@Override
	public <T> T LoadAny(Class<? extends T> clazz, String filename) throws IOException
	{
		return LoadStructure(clazz, filename, new ObjectFactoryFunc<T>());
//		InputStream stream = this.OpenAssetFile(filename);
//		T lesson = ObjectFactory.parseXml(clazz, stream);
//		stream.close();
//		return lesson;
	}
	private static void compileAvatar(Avatar avatar, IFunc<String, ContextBitmap> id2image)
	{
		avatar.compile(id2image);
	}
	
	
	// --------- singleton -----------
	
	public static MyAndroidResourceContext myCurrent()
	{
		return (MyAndroidResourceContext)instance;
	}
	
}
