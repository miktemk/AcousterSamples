package org.acouster.android.kyo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.GameEvent;
import org.acouster.IFunc;
import org.acouster.android.AnimationThread;
import org.acouster.context.ContextBitmap;
import org.acouster.context.android.CameraTestCode;
import org.acouster.kyo.NewAvatarGame;
import org.acouster.kyo.xml.CameraSchedule;

import org.acouster.android.kyo.R;
import org.acouster.android.ui.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//TODO: public class ScreenNew extends GameActivity

public class ScreenNew extends BaseActivity
implements
	//OnInitListener,
	GameContext
{
	
	private Activity THIS_ROOT = this;
	//private TextToSpeech mTts;
	private AnimationThread thread;
	private Camera cam;
	private View camPanel;
	private TextView txtField;
	private Game game;
	private String folderName;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_new);
		
		// context environment
        thread = new AnimationThread(null);
        
        // params from outside
        final String avatarFilename = getExtraString(ScreenGallery.EXTRA_avatarFilename);
		
        // set scheduler game
        game = new NewAvatarGame(this, avatarFilename, new IFunc<String, ContextBitmap>() {
			@Override
			public ContextBitmap lambda(String value) {
				//return ResourceContext.current().LoadBitmap(value + ".JPG");
				return null;
			}
		});
		setGame(game);
		
		// tts
		mTts = new TextToSpeech(this, this);
	
		// camera test
		
		// TODO: rewrite this
		CameraTestCode ccc = new CameraTestCode(this);
		cam = ccc.getCamera();
		camPanel = ccc.getPreviewPanel();
		LinearLayout botPanel = (LinearLayout)this.findViewById(R.id.pnlCamera);
		botPanel.addView(camPanel, 400, 300);
		
		// other ui
		final Button buttonGo = (Button) findViewById(R.id.btnScheduleGo);
		buttonGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	folderName = null;
            	game.handleCustomMessage(new GameEvent("game", CameraSchedule.CUSTOM_MESSAGE_GO));
            }
        });
		txtField = (TextView)this.findViewById(R.id.txtField);
		
		
	}
	
	// ============= listener events/interface members =====================

//	@Override
//	public void onInit(int status) {
//		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
//        if (status == TextToSpeech.SUCCESS) {
//            int result = mTts.setLanguage(Locale.US);
//            // Try this someday for some interesting results.
//            // int result mTts.setLanguage(Locale.FRANCE);
//            if (result == TextToSpeech.LANG_MISSING_DATA ||
//                result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("=======>", "Language is not available.");
//            } else {
//            	// success!
//            }
//        } else {
//            // Initialization failed.
//            Log.e("=======>", "Could not initialize TextToSpeech.");
//        }
//	}
	
	@Override
    public void onDestroy() {
        // kill tts
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        
        // kill camera
        if (cam != null) {
        	cam.release();
        }
        
        // kill game thread
        if (thread != null) {
        	thread.stopMe();
        }

        super.onDestroy();
    }
	
	@Override
	public void setGame(Game g) {
		if (g == null)
    		return;
    	thread.setFlashable(g);
        thread.start();
	}
	@Override
	public int getContextHeight() { return 0; }
	@Override
	public int getContextWidth() { return 0; }
	@Override
	public void triggerRepaint() {}
	@Override
	public void handleMessage(GameEvent message) {
		super.handleMessage(message);
		if (message.getTarget() == CameraSchedule.CUSTOM_MESSAGE_TARGET_TTS)
		{
			final String messageBody = message.getBody();
			speakText(messageBody);
			txtField.post(new Runnable() {
				public void run() {
					txtField.setText(messageBody);
				}
			});
		}
		else if (message.getTarget() == CameraSchedule.CUSTOM_MESSAGE_TARGET_CAM)
		{
			if (folderName == null)
			{
				//TODO: setup folder name by date and time up to the second
				folderName = KyoUtils.makeCurrentTimestampedFolderName();
				File destFolder = new File(Environment.getExternalStorageDirectory(), folderName);
				if (!destFolder.exists())
				{
					destFolder.mkdirs();
				}
			}
			String filename = folderName + "/" + message.getBody() + ".jpg";
			Log.v("camera picturiiing +========>>>>>>>>", filename);
			takePicture(filename);
		}
	}
	
	// =============== privates ==========================
	
//	private void speakText(String text) {
//        mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//    }
	private static final int FOTO_MODE = 0;
	private void takePicture(String filename) {
		final String filenameFinal = filename;
		Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
			public void onPictureTaken(byte[] imageData, Camera c) {
				if (imageData != null) {
					Intent mIntent = new Intent();
					StoreByteImage(THIS_ROOT, imageData, 50, filenameFinal);
					cam.startPreview();
					setResult(FOTO_MODE, mIntent);
					//finish();
				}
			}
		};
		cam.takePicture(null, mPictureCallback, mPictureCallback);
    }
	
	private static boolean StoreByteImage(Context mContext, byte[] imageData, int quality, String filename)
	{
		//File sdImageMainDirectory = new File("/sdcard/myImages");
		//File sdImageMainDirectory = new File("/piczzz");
		
		//Log.v("camera picturiiing", "" + Environment.getExternalStorageDirectory().getPath());
		File fileOut = new File(Environment.getExternalStorageDirectory(), filename);
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

}
