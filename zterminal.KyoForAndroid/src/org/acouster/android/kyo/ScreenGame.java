package org.acouster.android.kyo;

import org.acouster.IFunc;
import org.acouster.android.context.AndroidContextBitmap;
import org.acouster.android.ui.GameActivity;
import org.acouster.context.ContextBitmap;
import org.acouster.context.android.MyAndroidResourceContext;
import org.acouster.kyo.KyoGame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class ScreenGame extends GameActivity {
	
	public static String EXTRA_imageDirPrefix = "imageDirPrefix";
	
	protected KyoGame game;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // resources
        new MyAndroidResourceContext(gamePanel, this).makeInstance();
        
        // params from outside
        final String imageDirPrefix = getExtraString(EXTRA_imageDirPrefix);
        final String avatarFilename = getExtraString(ScreenGallery.EXTRA_avatarFilename);
        
        // set game
        setGame(game = new KyoGame(this, avatarFilename, new IFunc<String, ContextBitmap>() {
			@Override
			public ContextBitmap lambda(String value) {
				//return ResourceContext.current().LoadBitmap(value + ".JPG");
				Log.v("========>>>>>>>>>", imageDirPrefix + "/" + value + ".JPG");
				Bitmap bitmap = BitmapFactory.decodeFile(imageDirPrefix + "/" + value + ".JPG");
				ContextBitmap bbb = new AndroidContextBitmap(bitmap);
				return bbb;
			}
		}));
        
        
    }

	@Override
    public void onTimeForRequestingFeatures() {
    	activateFullscreen();
        //activateWakeLock(); //?????????????
    }
	
	@Override
    public void onDestroy() {
    	super.onDestroy();
    	//TODO: delete bitmaps???????
    }
}
