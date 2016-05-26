package org.acouster.android.eyefitness;

import org.acouster.Game;
import org.acouster.GameEvent;
import org.acouster.IFuncVoidSolo;
import org.acouster.eyefitness.EyeFitnessGame;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ActivityMain extends BaseActivity implements OnSharedPreferenceChangeListener
{
	private EyeFitnessGame mainGame;
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
    	
    	setGame(game = mainGame = new EyeFitnessGame(this));
    	mainGame.constructExerciseGraph(5);
    	// TODO: !!! delete before production!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	//displayHints();
    	
    	// sounds
//		addSoundResourceToMultiMap(Constants.Sounds.CONGRADS, R.raw.congrads);
//		addSoundResourceToMultiMap(Constants.Sounds.EEEEK, R.raw.eeeh);
//		addSoundResourceToMultiMap(Constants.Sounds.POOF, R.raw.pop);
//		addSoundResourceToMultiMap(Constants.Sounds.DRUMS, R.raw.drums);
	}

	@Override
	public void onTimeForRequestingFeatures()
	{
		super.onTimeForRequestingFeatures();
		//activateFullscreen();
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//activateWakeLock();
		//activateTts();
		//activateAccelerometer();
		//showAds(getResources().getString(R.string.adUnitId));
	}
	
	int musicId, musicStreamId;
    @Override
	public void handleMessage(GameEvent message)
    {
		super.handleMessage(message);
		if (message.isDirective(Game.DIRECTIVE_SOUND))
		{
			String sound = message.getLatterPart();
			playSoundFromMultiMap(sound, false, 1.0f);
		}
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