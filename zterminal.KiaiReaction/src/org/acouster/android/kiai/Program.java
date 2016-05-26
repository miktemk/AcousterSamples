package org.acouster.android.kiai;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.android.AnimationThread;
import org.acouster.android.ui.AndroidGamePanel;
import org.acouster.android.ui.BaseActivity;
import org.acouster.android.ui.GameActivity;
import org.acouster.context.android.MyAndroidResourceContext;
import org.acouster.kiai.Kiai8DirectionGame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

public class Program extends GameActivity {
	
	
	protected Kiai8DirectionGame game;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // resources
        new MyAndroidResourceContext(gamePanel, this).makeInstance();
        
        // set game
        setGame(game = new Kiai8DirectionGame());
        
        
    }
    
    @Override
    public void onTimeForRequestingFeatures() {
    	activateFullscreen();
        activateWakeLock();
        activateAccelerometer();
    }

    @Override
    protected void onAccelerometerCoordsChanged(float mSensorX, float mSensorY)
	{
		game.setAccelerometerCoords(mSensorX, mSensorY);
        //Log.v(DebugUtil.TAG, mSensorX + " : " + mSensorY);
	}
}