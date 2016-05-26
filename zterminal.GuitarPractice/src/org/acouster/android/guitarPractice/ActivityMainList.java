package org.acouster.android.guitarPractice;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.acouster.android.ui.ActivityListGeneric;
import org.acouster.guitarPractice.Constants;
import org.acouster.guitarPractice.xml.XGuitarConfig;
import org.acouster.guitarPractice.xml.XGuitarSong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityMainList extends ActivityListGeneric<Integer>
{
	public static final int SUB_ACTIVITY_GUITAR = 3;
	public static final String EXTRA_songId = "songId";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		BaseActivity.commonOnCreate(this);
	    super.onCreate(savedInstanceState);
	}
	
	@Override
	protected List<ListActivityElement> CreateList()
	{
    	List<ListActivityElement> list = new Vector<ListActivityElement>();
    	
    	// fill the list
    	try {
    		//File fConfig = MyAndroidResourceContext.ainstance().getExternalFile(Constants.Filenames.ExternalStoragePath + "/config.xml");
    		//XGuitarConfig config = ObjectFactory.loadXml(XGuitarConfig.class, fConfig);
    		XGuitarConfig config = MyAndroidResourceContext.myCurrent().LoadAnyExternalXml(XGuitarConfig.class, Constants.Filenames.ExternalStoragePath + "/config.xml");
    		int iii = 0;
			for (XGuitarSong sss : config.songs)
			{
				list.add(new ListActivityElement(sss.name, iii));
				iii++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Oops, config file got corrupted!", Toast.LENGTH_LONG).show();
		}
    	
		return list;
    }
	
	@Override
	protected Class<?> getSubActivityClass() {
		return ActivityMain.class;
	}

	@Override
	protected int getSubActivityId() {
		return SUB_ACTIVITY_GUITAR;
	}

	@Override
	protected void putExtras(Integer data, Intent i)
	{
		i.putExtra(EXTRA_songId, data);
	}
}