package org.acouster.android.kyo;

//import acouster.game.R;
//import acouster.game.context.android.CameraTestCode;
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.LinearLayout;
//
//public class CameraTest extends Activity {
//	
//	/** Called when the activity is first created. */
////  @Override
////  public void onCreate(Bundle savedInstanceState) {
////      super.onCreate(savedInstanceState);
////      setContentView(R.layout.main);
////  }
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//	
//		CameraTestCode ccc = new CameraTestCode(this);
//	
//		LinearLayout botPanel = (LinearLayout)this.findViewById(R.id.widget45);
//		botPanel.addView(ccc.getPreviewPanel(), 400, 300);
//	
//		Log.v("======>", "width: " + ccc.getPreviewPanel().getWidth());
//		Log.v("======>", "hei" + ccc.getPreviewPanel().getHeight());
//	
//	}
//	
//
//}

import org.acouster.android.kyo.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.acouster.android.ui.BaseActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;



public class CameraTest extends BaseActivity
implements SurfaceHolder.Callback, OnClickListener
{
	static final int FOTO_MODE = 0;
	private static final String TAG = "CameraTest============>";
	Camera mCamera;
	boolean mPreviewRunning = false;
	private Context mContext = this;
	

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Log.e(TAG, "onCreate");
		
		Bundle extras = getIntent().getExtras();
		

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		mSurfaceView.setOnClickListener(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		Log.v(TAG, "" + getExternalFilesDir("file.txt"));
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {

			if (imageData != null) {

				Intent mIntent = new Intent();

				StoreByteImage(mContext, imageData, 50, "testimg");
				mCamera.startPreview();
				
				setResult(FOTO_MODE,mIntent);
				//finish();
			

			}
		}
	};
	
	public static boolean StoreByteImage(Context mContext, byte[] imageData, int quality, String expName)
	{
		//File sdImageMainDirectory = new File("/sdcard/myImages");
		//File sdImageMainDirectory = new File("/piczzz");
		
		Log.v(TAG, "" + Environment.getExternalStorageDirectory().getPath());
		File fileOut = new File(Environment.getExternalStorageDirectory(), expName + ".jpg");
		FileOutputStream fileOutputStream = null;
		try {

			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 5;
			
			Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0, imageData.length,options);

			//fileOutputStream = new FileOutputStream(sdImageMainDirectory.toString() +"/" + expName + ".jpg");
			fileOutputStream = new FileOutputStream(fileOut);
  
			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			myImage.compress(CompressFormat.JPEG, quality, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}


	protected void onResume() {
		Log.e(TAG, "onResume");
		super.onResume();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	protected void onStop() {
		Log.e(TAG, "onStop");
		super.onStop();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.e(TAG, "surfaceCreated");
		mCamera = Camera.open(1);
	
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//		Log.e(TAG, "surfaceChanged");
//
		// XXX stopPreview() will crash if preview is not running
		if (mPreviewRunning) {
			mCamera.stopPreview();
		}
		
////		if (www > hhh)
////			p.setPreviewSize(hhh, www);
//		//p.setSceneMode(Camera.Parameters.SCENE_MODE_LANDSCAPE);
//		p.setRotation(0);
//		//p.setRotation(270);
//		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//        {   
//            //p.set("orientation", "portrait");
//            //p.set("rotation",90);
//        }
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {                               
//            //p.set("orientation", "landscape");          
//            //p.set("rotation", 90);
//        }
		
		
//		int count = 0;
//		List<Size> mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
//		
//		for (Size size : mSupportedPreviewSizes)
//		{
//			Log.e(TAG, count + ":::::::: " + size.width + " : " + size.height);
//			if (count >= 2)
//			{
//				Log.e(TAG, "setting size... " + size.width + " : " + size.height);
//				//p.setPreviewSize(size.height, size.width);
//				p.setPictureSize(size.height, size.width);
//				//p.setSceneMode(Camera.Parameters.SCENE_MODE_PORTRAIT);
//				break;
//			}
//			count++;
//		}
		
		
		Camera.Parameters p = mCamera.getParameters();
		int width = p.getPreviewSize().width;
		int height = p.getPreviewSize().height;

        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        if(display.getRotation() == Surface.ROTATION_0)
        {
            //p.setPreviewSize(height, width);                           
            mCamera.setDisplayOrientation(90);
        }

        if(display.getRotation() == Surface.ROTATION_90)
        {
            //p.setPreviewSize(width, height);                           
        }

        if(display.getRotation() == Surface.ROTATION_180)
        {
            //p.setPreviewSize(height, width);               
        }

        if(display.getRotation() == Surface.ROTATION_270)
        {
           // p.setPreviewSize(width, height);
            mCamera.setDisplayOrientation(180);
        }

		
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}

		mCamera.setParameters(p);
		mCamera.startPreview();
		
		mPreviewRunning = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e(TAG, "surfaceDestroyed");
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}

	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	public void onClick(View arg0) {

		mCamera.takePicture(null, mPictureCallback, mPictureCallback);

	}

}
