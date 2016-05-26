package org.acouster.android.platinum;

import java.io.IOException;
import java.util.HashMap;

import org.acouster.DebugUtil;
import org.acouster.Flashable;
import org.acouster.android.AnimationThread;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.util.Log;

public class SegmentedAudio
{
	// listener interface..........................
	public interface ISegmentChangeListener {
		void enteredSegment(int id);
		void donePlayback();
	}
	
	// SegmentedAudioSegment..........................
	public class SegmentedAudioSegment {
		private int millisIn;
		private int millisOut;
		public SegmentedAudioSegment(int millisIn, int millisOut) {
			super();
			this.millisIn = millisIn;
			this.millisOut = millisOut;
		}
		public int getMillisIn() {
			return millisIn;
		}
		public int getMillisOut() {
			return millisOut;
		}
		public boolean isBetween(int position) {
			return (position >= millisIn && position < millisOut);
		}
	}

	private static final int INVALID_SEGMENT_ID = -1;

	protected MediaPlayer mp;
	protected AnimationThread tracker;
	protected Flashable tackerCallback;
	protected ISegmentChangeListener segmentListener = null;
	protected HashMap<Integer, SegmentedAudio.SegmentedAudioSegment> segmentMap;
	protected SegmentedAudioSegment lastEndingSegment;
	protected int curSegmentId = INVALID_SEGMENT_ID;
	protected SegmentedAudioSegment stopWithinSegment = null;
	protected boolean do_stopWithinSegment = true;
	protected boolean reachedEnd = false;

	//private boolean isContinuousPlayingOn = false;
	
	public SegmentedAudio(String filename)
	{
		segmentMap = new HashMap<Integer, SegmentedAudio.SegmentedAudioSegment>();
		load(filename);
	}
	
	// ========== lifecycle =====================
	public void load(String filename)
	{
		// free any old resources
		release();
		
		// create media player
		mp = new MediaPlayer();
		try {
			mp.reset();
			mp.setDataSource(filename);
			mp.prepare();
			mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				public void onCompletion(MediaPlayer arg0) {
					do_stopWithinSegment = true;
					reachedEnd = true;
					mpSafeStop();
					if (segmentListener != null)
						segmentListener.donePlayback();
				}
			});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			mp = null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			mp = null;
		} catch (IOException e) {
			e.printStackTrace();
			mp = null;
		}
		
		
		// create tracker to check on mp's progress every 200 ms :)
		tackerCallback = new Flashable() {
			@Override
			public void flash() {
				//Log.v(DebugUtil.TAG, "running shit.....");
				
				if (mp == null)
					return;
				
				if (mp.isPlaying())
				//if (mp_playing)
					headAdvanced();
				return;
			}
		};
		startTracker();
	}
	
	public boolean loadSuccess() {
		return (mp != null);
	}
	
	public void addSegment(int id, long millisIn, long millisOut)
	{
		if (id == INVALID_SEGMENT_ID)
			throw new IllegalArgumentException("INVALID_SEGMENT_ID given!");
		
		SegmentedAudioSegment newSegment = new SegmentedAudioSegment((int)millisIn, (int)millisOut);
		segmentMap.put(id, newSegment);
		
		// keep track of the last ending segment
		if (lastEndingSegment == null || millisOut > lastEndingSegment.getMillisOut())
			lastEndingSegment = newSegment;
	}
	
	public void setSegmentChangeListener(ISegmentChangeListener segmentListener)
	{
		this.segmentListener = segmentListener;
	}
	
	public void playSegment(int id)
	{
		if (mp == null)
			return;
		
		if (!segmentMap.containsKey(id))
			return;
		
		SegmentedAudioSegment seg = segmentMap.get(id);
		mpSafeSeek((int)seg.getMillisIn());
		
		// only reset this guy if this is not the last segment
		if (seg != lastEndingSegment)
			reachedEnd = false;
		
		//stoppppppp at seg.getMillisOut()
		do_stopWithinSegment = true;
		stopWithinSegment = seg;
		
		mpSafeStart();
	}
	
	public void playToEnd()
	{
		if (mp == null)
			return;
		
		// go to the beginning
		if (reachedEnd)
			mpSafeSeek(0);
		
		reachedEnd = false;
		do_stopWithinSegment = false;
		mpSafeStart();
		
		performSegmentChangeCheck();
	}
	
	public boolean isPlaying() {
		if (mp == null)
			return false;
		return mp.isPlaying();
	}
	
	// called exclusively by the thread
	protected void headAdvanced()
	{
		// insanity check: this should never happen...
		if (mp == null)
			return;
		
		int position = mp.getCurrentPosition();
		//Log.v(DebugUtil.TAG, "head advanced" + position);
		
		// if we are programmed to stop at stopAtWhatMillisecond
		if (do_stopWithinSegment && position >= stopWithinSegment.getMillisOut())
		{
			mpSafeStop();
			// go back to beginning.... or end? no, beginning makes more sence from the frontend logic
			mpSafeSeek(stopWithinSegment.getMillisIn());
			//do_stopWithinSegment = false;
		}
		performSegmentChangeCheck();
	}
	
	public void pauseAndFloorToCurrentSegment()
	{
		if (mp == null)
			return;
		
		// pause
		mpSafeStop();
		
		// Floor To Current Segment
		int curId = getCurrentOrNearestLeftSegmentId();
		if (curId != INVALID_SEGMENT_ID)
		{
			SegmentedAudioSegment seg = segmentMap.get(curId);
			mpSafeSeek((int)seg.getMillisIn());
		}
		
		performSegmentChangeCheck();
	}
	
	public void startTracker()
	{
		// kill tracker thread
		if (tracker == null)
		{
			tracker = new AnimationThread(tackerCallback, 200);
			tracker.startMe();
		}
	}
	public void stopTracker()
	{
		// kill tracker thread
		if (tracker != null)
		{
			tracker.stopMe();
			tracker = null;
		}
	}
	
	public void release()
	{
		stopTracker();
		
		// kill player
		if (mp != null)
		{
			mp.release();
			mp = null;
		}
	}
	
	// ========== privates =====================
	
	// ========== privates =====================
	protected void performSegmentChangeCheck()
	{
		if (do_stopWithinSegment)
			return;
		int newSegmentId = getCurrentOrNearestLeftSegmentId();
		if ((newSegmentId != curSegmentId) && (segmentListener != null))
			segmentListener.enteredSegment(newSegmentId);
		curSegmentId = newSegmentId;
	}
	protected void mpSafeSeek(int position)
	{
		if (mp == null)
			return;
		//if (mp.isPlaying())
		//	return;
		mp.seekTo(position);
		//Log.v(DebugUtil.TAG, "mp seek:" + position);
	}
	protected void mpSafeStart()
	{
		if (mp == null)
			return;
		if (mp.isPlaying())
			return;
		mp.start();
		//Log.v(DebugUtil.TAG, "mp start called");
	}
	protected void mpSafeStop()
	{
		if (mp == null)
			return;
		if (!mp.isPlaying())
			return;
		mp.stop();
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.v(DebugUtil.TAG, "mp stop called");
	}
	
	// ========== helpers =====================
	
	public int getCurrentSegmentId()
	{
		if (mp == null)
			return INVALID_SEGMENT_ID;
		
		int position = mp.getCurrentPosition();
		for (int key : segmentMap.keySet())
		{
			SegmentedAudioSegment seg = segmentMap.get(key);
			if (seg.isBetween(position))
				return key;
		}
		return INVALID_SEGMENT_ID;
	}
	
	public int getCurrentOrNearestLeftSegmentId()
	{
		if (mp == null)
			return INVALID_SEGMENT_ID;
		
		// try between algorithm first
		int keyBetween = getCurrentSegmentId();
		if (keyBetween != INVALID_SEGMENT_ID)
			return keyBetween;
		
		// not within any segment... get the closest one to the left
		int position = mp.getCurrentPosition();
		SegmentedAudioSegment segMin = null; 
		int keyMin = INVALID_SEGMENT_ID;
		for (int key : segmentMap.keySet())
		{
			SegmentedAudioSegment seg = segmentMap.get(key);
			if (seg.getMillisIn() < position)
			{
				// either #1 or its to the right of last one
				if (segMin == null || seg.getMillisIn() > segMin.getMillisIn())
				{
					segMin = seg;
					keyMin = key;
				}
			}
		}
		// invariant: if we didn't find anything keyMin == INVALID_SEGMENT_ID
		return keyMin;
	}

}
