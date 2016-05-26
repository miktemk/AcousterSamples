package org.acouster.android.eyefitness;

import org.acouster.GameEvent;
import org.acouster.IFuncVoidSolo;
import org.acouster.android.DialogUtils;
import org.acouster.android.ui.ActivityOptions;
import org.acouster.eyefitness.MenuGame;

import android.content.Intent;
import android.os.Bundle;

public class ActivityMenu extends BaseActivity
{
	private static final int SUB_ACTIVITY_WORKOUT = 1;
	private static final int SUB_ACTIVITY_OPTIONS = 2;
	
	int soundID, soundID2;

	@Override
    public void onCreate(final Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);
	    
    	boolean alreadyAccepted = DialogUtils.showEulaRequireAcceptance(this, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				initGameShit(savedInstanceState);
			}
		});
    	
    	if (alreadyAccepted)
    		initGameShit(savedInstanceState);
    }
	
	private void initGameShit(Bundle savedInstanceState)
	{
		// set game
		setGame(game = new MenuGame(this));
    	//setGame(game = new _DeleteThis_NumTest(this));
	}
	
	//TODO: remove this... only have ads in the game itself???
	@Override
	public void onTimeForRequestingFeatures()
	{
		super.onTimeForRequestingFeatures();
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//showAds(getResources().getString(R.string.adUnitId));
	}
	
    @Override
	public void handleMessage(GameEvent message) 
    {
		super.handleMessage(message);
    	//DebugUtil.sss("menu activity");
    	if (message.getBody() == MenuGame.OPTION_GO)
		{
    		Intent i = new Intent(ActivityMenu.this, ActivityMain.class);
    		//i.putExtra(MenuGame.EXTRA_DIFFICULTY, someValueSeeAnts);
			startActivityForResult(i, SUB_ACTIVITY_WORKOUT);
		}
		else if (message.getBody() == MenuGame.OPTION_OPT)
		{
			Intent i = new Intent(ActivityMenu.this, ActivityOptions.class);
			startActivityForResult(i, SUB_ACTIVITY_OPTIONS);
		}
	}
}