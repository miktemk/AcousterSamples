package org.acouster.android.kyo;

import org.acouster.android.kyo.R;
import org.acouster.android.ui.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.util.Log;
import android.view.View;


public class ScreenGallery extends BaseActivity
{
	public static String EXTRA_avatarFilename = "avatarFilename";
	
	protected static final int SUB_ACTIVITY_NEW = 101;
	protected static final int SUB_ACTIVITY_PLAY = 102;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.screen_gallery);

        // params from outside
        //final String avatarFilename = getExtraString(ScreenGallery.EXTRA_avatarFilename);
        final String avatarFilename = "xml/sample.xml";
        
        // new screen
        final Button buttonNew = (Button) findViewById(R.id.btnTakePicture);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Toast.makeText(ScreenGallery.this, "" + position, Toast.LENGTH_SHORT).show();
            	//Log.v("==========================>", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
            	Intent i = new Intent(ScreenGallery.this, ScreenNew.class);
            	i.putExtra(EXTRA_avatarFilename, avatarFilename);
				startActivityForResult(i, SUB_ACTIVITY_NEW);
            }
        });
        
        // game screen
        final ImageGalleryAdapter adapter = new ImageGalleryAdapter(this);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(ScreenGallery.this, "" + position, Toast.LENGTH_SHORT).show();
            	Intent i = new Intent(ScreenGallery.this, ScreenGame.class);
            	i.putExtra(ScreenGame.EXTRA_imageDirPrefix, adapter.getFolderPrefix(position));
            	i.putExtra(EXTRA_avatarFilename, avatarFilename);
				startActivityForResult(i, SUB_ACTIVITY_PLAY);
            }
        });
        
    }
	
}

