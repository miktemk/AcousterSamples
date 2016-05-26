package org.acouster.android.platinum;

import java.io.IOException;

import org.acouster.DebugUtil;
import org.acouster.platinum.xml.Lesson;
import org.acouster.platinum.xml.LessonLine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLesson extends Activity implements OnClickListener {
	
	private static final int MENU_ITEM_ID_LANG1 = 0;

    // utils
    //protected PlatUtils utils;
    
    // logic
    protected Lesson lesson;
    protected SegmentedAudio audio;
    protected int curIndex = -1;
    protected ImageButton playButton;
    //protected boolean isPlayingOneSegement = false;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        //utils = new PlatUtils(this);
        
        ((ImageButton)this.findViewById(R.id.btnPlay)).setOnClickListener(this);
        ((ImageButton)this.findViewById(R.id.btnRepeat)).setOnClickListener(this);
        ((ImageButton)this.findViewById(R.id.btnNext)).setOnClickListener(this);
        ((ImageButton)this.findViewById(R.id.btnPrev)).setOnClickListener(this);
        
        // get passed parameters
        final String filename;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	filename = extras.getString(Constants.EXTRA_lessonFilename);
        }
        else {
        	filename = "";
        }
        Log.v(DebugUtil.TAG, filename);
        
        // load the xml
        try {
			lesson = MyResourceContext.myCurrent().LoadLesson(filename);
		} catch (IOException e) {
			e.printStackTrace();
			lesson = null;
		}
		
		if (lesson != null)
		{
			// setup first frame visuals
			curIndex = 0;
			setupCurrentSegmentVisuals();
			curIndex = -1;
			
			// load audio
			String audioFile = PlatUtils.instance().getPlatinumAudioPath(lesson);
			audio = new SegmentedAudio(audioFile);
			if (!audio.loadSuccess())
			{
				Toast.makeText(getApplicationContext(), "Could not load audio", Toast.LENGTH_SHORT).show();
			}
			
			// add markers
			int segmentId = 0;
			for (LessonLine line : lesson.getLines())
			{
				audio.addSegment(segmentId, line.getAudioTime().getIn(), line.getAudioTime().getOut());
				//Log.v(DebugUtil.TAG, "deutsch: " + line.getLang1());
				segmentId++;
			}
			//set the marker listener
			final View sampleUI = this.findViewById(R.id.imgView);
			playButton = (ImageButton)this.findViewById(R.id.btnPlay);
			audio.setSegmentChangeListener(new SegmentedAudio.ISegmentChangeListener() {
				@Override
				public void enteredSegment(int id) {
					//Log.v(DebugUtil.TAG, "MARKER CHANGE: " + id);
					if (id >= 0 && id < lesson.getLines().size())
						curIndex = id;
					sampleUI.post(new Runnable() {
						public void run() {
							setupCurrentSegmentVisuals();
						}
					});
				}
				@Override
				public void donePlayback() {
					playButton.setImageResource(R.drawable.btn_play);
				}
			});
		}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Menu.NONE, MENU_ITEM_ID_LANG1, 0, "Show/Hide lang 2");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case MENU_ITEM_ID_LANG1:
            	TextView txtLang1 = (TextView)this.findViewById(R.id.txtLang1);
            	if (txtLang1.getVisibility() == View.VISIBLE)
            		txtLang1.setVisibility(View.INVISIBLE);
            	else
            		txtLang1.setVisibility(View.VISIBLE);
            	return true;
        }
        return false;
    }
    
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(DebugUtil.TAG, "onResume thread " + this);
        if (audio != null)
        	audio.startTracker();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(DebugUtil.TAG, "onPause thread " + this);
        if (audio != null)
        	audio.stopTracker();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.v(DebugUtil.TAG, "onDestroy thread " + this);
        // Don't forget to shutdown!
    	//gamePanel.killThread();
    	if (audio != null) {
    		audio.stopTracker();
    		audio.release();
    	}
    }
    
    @Override
	public void onClick(View v) {
		if (lesson == null)
		{
			Toast.makeText(getApplicationContext(), "Could not load lesson", Toast.LENGTH_SHORT).show();
			return;
		}
		
		ImageButton b = (ImageButton)v;
		
		if (v.getId() == R.id.btnPlay)
		{
			//Toast.makeText(getApplicationContext(), "btnPlayOne", Toast.LENGTH_SHORT).show();
			if (!audio.isPlaying())
			{
				playButton.setImageResource(R.drawable.btn_pause);
				audio.playToEnd();
			}
			else
			{
				playButton.setImageResource(R.drawable.btn_play);
				audio.pauseAndFloorToCurrentSegment();
			}
		}
		else if (v.getId() == R.id.btnRepeat)
		{
			//Toast.makeText(getApplicationContext(), "btnPlayAll", Toast.LENGTH_SHORT).show();
			if (curIndex == -1)
				curIndex = 0;
			if (curIndex < lesson.getLines().size())
				playCurrentSegmentOnly();
			playButton.setImageResource(R.drawable.btn_play);
		}
		else if (v.getId() == R.id.btnNext)
		{
			//Toast.makeText(getApplicationContext(), "btnNext", Toast.LENGTH_SHORT).show();
			if (curIndex < lesson.getLines().size() - 1)
			{
				curIndex++;
				playCurrentSegmentOnly();
				playButton.setImageResource(R.drawable.btn_play);
			}
			
		}
		else if (v.getId() == R.id.btnPrev)
		{
			//Toast.makeText(getApplicationContext(), "btnPrev", Toast.LENGTH_SHORT).show();
			
			// set this guy to one so we come to 0 and play it for the firts time
			if (curIndex == -1)
				curIndex = 1;
			
			if (curIndex > 0)
			{
				curIndex--;
				playCurrentSegmentOnly();
				playButton.setImageResource(R.drawable.btn_play);
			}
		}
	}

	private void playCurrentSegmentOnly() {
		LessonLine line = PlatUtils.instance().getLineSafe(lesson, curIndex);
		if (line == null)
			return;
		
		setupCurrentSegmentVisuals();
		
		//do audio
		audio.playSegment(curIndex);
	}
	
	private Bitmap prevBitmap = null;
	private void setupCurrentSegmentVisuals() {
		LessonLine line = PlatUtils.instance().getLineSafe(lesson, curIndex);
		if (line == null)
			return;
		
		TextView txtLang1 = (TextView)this.findViewById(R.id.txtLang1);
		TextView txtLang2 = (TextView)this.findViewById(R.id.txtLang2);
		ImageView imgView = (ImageView)this.findViewById(R.id.imgView);
		
		//set text
		txtLang1.setText(PlatUtils.instance().makeLessonCurrentTripleLine(lesson, curIndex, PlatUtils.WHICH_LANGUAGE_1));
		txtLang2.setText(PlatUtils.instance().makeLessonCurrentTripleLine(lesson, curIndex, PlatUtils.WHICH_LANGUAGE_2));
		
		//set image
		String imgPath = PlatUtils.instance().getPlatinumImagePath(line);
		Bitmap img = MyResourceContext.myCurrent().loadBitmapFromFile(imgPath, R.drawable.missing_image);
		imgView.setImageBitmap(img);
		
		// free previous bitmap of it exists
		if (prevBitmap != null)
			prevBitmap.recycle();
		prevBitmap = img;
	}
	
}