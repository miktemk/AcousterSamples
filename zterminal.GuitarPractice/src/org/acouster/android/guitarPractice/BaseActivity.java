package org.acouster.android.guitarPractice;

import org.acouster.IFuncVoidSolo;
import org.acouster.android.DialogUtils;
import org.acouster.android.context.AndroidResourceContext;
import org.acouster.android.ui.ActivityOptions;
import org.acouster.android.ui.GameActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends GameActivity
{
	public static final int MENU_ITEM_OPTIONS = 0;
	public static final int MENU_ITEM_HOWTO = 1;
	public static final int MENU_ITEM_ACOUSTER_URL = 2;
	
	public static final String PREFERENCES_howToVisited = "howToVisited";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	commonOnCreate(this);
    }
	
	@Override
	public void onTimeForRequestingFeatures()
	{
		super.onTimeForRequestingFeatures();
		activateFullscreen();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
		commonOnCreateOptionsMenu(menu, this);
		return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return commonOnOptionsItemSelected(this, item);
    }
    
    //------------------------------------------------------------------------------------
    // static functions for use by activities that do not inherit EyeBaseActivity
    public static void commonOnCreateOptionsMenu(Menu menu, Context context)
    {
        //menu.add(Menu.NONE, MENU_ITEM_OPTIONS, 0, context.getString(R.string.txt_menu_show_settings));
    	menu.add(Menu.NONE, MENU_ITEM_OPTIONS, 0, R.string.menu_show_settings);
    	menu.add(Menu.NONE, MENU_ITEM_HOWTO, 0, R.string.menu_how_to_play);
    	menu.add(Menu.NONE, MENU_ITEM_ACOUSTER_URL, 0, R.string.menu_acouster_url);
    }
    public static boolean commonOnOptionsItemSelected(Activity activity, MenuItem item)
    {
        switch (item.getItemId())
        {
            case MENU_ITEM_OPTIONS:
            	activity.startActivity(new Intent(activity, ActivityOptions.class));
                return true;
            case MENU_ITEM_HOWTO:
            	//Intent intentPrefable = new Intent(activity, ActivityHowToPlay.class);
            	//intentPrefable.putExtra(ActivityHowToPlay.EXTRA_goToGame, false);
            	//activity.startActivity(intentPrefable);
            	showHowToPlayDialog(activity, null, true);
                return true;
            case MENU_ITEM_ACOUSTER_URL:
            	Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( activity.getString(R.string.acouster_url) ) );
                activity.startActivity(browse);
                return true;
        }
        return false;
    }
	public static void commonOnCreate(Activity activity)
    {
    	MyAndroidResourceContext.initIfNotReady(activity);
    	refreshOptions(activity);
//    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity);
//    	settings.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
//			@Override
//			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//				updateOptionsStruct(sharedPreferences);
//			}
//		});
    }
	public static void refreshOptions(Activity activity)
    {
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity);
    	updateOptionsStruct(settings);
    }
	
	public static boolean showHowToPlayDialog(Activity activity, IFuncVoidSolo onOk, boolean override)
    {
    	return DialogUtils.showRichTextDialogIfNeverBefore(activity,
    			activity.getString(R.string.howto_title),
    			activity.getString(R.string.howto_text),
    			activity.getString(R.string.howto_got_it),
    			PREFERENCES_howToVisited,
    			onOk,
    			override,
    			new ImageGetter() {
					@Override
					public Drawable getDrawable(String source) {
						Log.v("=======>>>>", "ImageGetter:" + source);
						Drawable ddd = AndroidResourceContext.ainstance().loadDrawableByName(source);
						Log.v("=======>>>>", "ImageGetter:ddd=" + ddd);
						return ddd;
						//return MyAndroidResourceContext.myCurrent().loadDrawableByName(source);
					}
				});
    }
	
	protected static void updateOptionsStruct(SharedPreferences settings)
	{	
    	OptionsStruct options = new OptionsStruct(
    			settings.getInt("optInt", 5),
    			settings.getBoolean("optBool", true)
    	);
    	options.makeInstance();
	}
}