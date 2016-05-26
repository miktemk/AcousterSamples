package org.acouster.android.guitarPractice;

import java.io.IOException;

import org.acouster.GameEvent;
import org.acouster.IFuncVoidSolo;
import org.acouster.android.ui.ListFilesActivity;
import org.acouster.context.ImageManager;
import org.acouster.guitarPractice.Constants;
import org.acouster.guitarPractice.MainGame;
import org.acouster.guitarPractice.xml.XGuitarConfig;
import org.acouster.guitarPractice.xml.XGuitarSong;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class ActivityMain extends BaseActivity implements OnSharedPreferenceChangeListener
{
	private MainGame mainGame;
	private boolean changedPref = false;
    
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);
	    
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    	sp.registerOnSharedPreferenceChangeListener(this);
    	
    	boolean seenHowToBefore = showHowToPlayDialog(this, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				initGameShit();
			}
		}, false);
    	if (seenHowToBefore)
    		initGameShit();
    }
	
	private void initGameShit()
	{
		// set game
    	setOptionsAndConfigForGame();
    	
    	int id = getExtraInt(ActivityMainList.EXTRA_songId);
    	XGuitarConfig config = null;
    	try {
    		config = MyAndroidResourceContext.myCurrent().LoadAnyExternalXml(XGuitarConfig.class, Constants.Filenames.ExternalStoragePath + "/config.xml");
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Oops, config file got corrupted!", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Oops, config file got corrupted bad!", Toast.LENGTH_LONG).show();
		}
    	if (config != null)
    	{
    		XGuitarSong song = config.songs.get(id);
    		Toast.makeText(this, "Loading song \"" + song.name + "\" with " + song.images.size() + " pages", Toast.LENGTH_LONG).show();
    		//System.out.println(ImageManager.instance().toString());
    		
    		manualThreadControl();
    		setGame(game = mainGame = new MainGame(this, song));
    	}
	}

	@Override
	public void onTimeForRequestingFeatures()
	{
		super.onTimeForRequestingFeatures();
		//activateFullscreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		activateWakeLock();
		//activateTts();
		//activateAccelerometer();
		//showAds(getResources().getString(R.string.adUnitId));
	}
	
    @Override
    protected void onResume()
    {
    	super.onResume();
    	if (changedPref)
    	{
    		setOptionsAndConfigForGame();
        	if (mainGame != null)
        	{
        		// set options here
    	    	//mainGame.setConfig(OptionsStruct.instance());
    			//mainGame.reset();
        	}
    	}
    	changedPref = false;
    }
    
    @Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
    {
    	changedPref = true;
	}
    
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
    //-------------------
    private void setOptionsAndConfigForGame()
    {
    	refreshOptions(this);
    	//OptionsStruct.instance().UpdateLevelConfig(this);
    	//int difficulty = getExtraInt(MenuGame.EXTRA_DIFFICULTY);
    	//OptionsStruct.instance().levelConfig().setDifficultyLevel(difficulty);
    }
    

	
}