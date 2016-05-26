package org.acouster.android.platinum;

import java.io.IOException;

import org.acouster.DebugUtil;
import org.acouster.android.ui.BaseActivity;
import org.acouster.platinum.xml.menu.LessonCategory;
import org.acouster.platinum.xml.menu.LessonFile;
import org.acouster.platinum.xml.menu.Lessons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMain extends BaseActivity  {
	
	protected static final int SUB_ACTIVITY_PLAY = 0;
	protected static final int SUB_ACTIVITY_CATEGORY = 1;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        final String whereShitIs = getExtraString(Constants.EXTRA_externalPath, getString(R.string.ExternalStoragePath));
        final String xmlLessons = getExtraString(Constants.EXTRA_lessonFilename, MyResourceContext.LESSON_LISTING_DEFAULT_FRENCH_PATH);
        final String xmlReplacement = getExtraString(Constants.EXTRA_XmlReplacement, "xml");
        
        Log.v(DebugUtil.TAG, whereShitIs + "  " + xmlLessons);
        
        setContentView(R.layout.main);
        new MyResourceContext(this).makeInstance();
        new PlatUtils(whereShitIs).makeInstance();
        
        Lessons lessons; 
		try {
			lessons = MyResourceContext.myCurrent().LoadLessonsListing(xmlLessons);
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Could not load lesson list", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (lessons.categories.size() > 1)
		{
			LessonCategory[] catListing = lessons.categories.toArray(new LessonCategory[lessons.categories.size()]);
			
			final ArrayAdapter<LessonCategory> adapter = new ArrayAdapter<LessonCategory>(this, R.layout.list_item, catListing);
			
			ListView lv = getMyListView();
	        lv.setAdapter(adapter);
			lv.setTextFilterEnabled(true);
			lv.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	        	{
	        		Intent i = new Intent(ActivityMain.this, ActivityCategory.class);
	        		LessonCategory item = adapter.getItem(position);
	        		i.putExtra(ActivityCategory.EXTRA_categoryName, item.name);
	        		i.putExtra(Constants.EXTRA_externalPath, whereShitIs);
	        		i.putExtra(Constants.EXTRA_lessonFilename, xmlLessons);
	        		i.putExtra(Constants.EXTRA_XmlReplacement, xmlReplacement);
					startActivityForResult(i, SUB_ACTIVITY_CATEGORY);
				}

	        });
		}
		else
		{
			LessonFile[] fileListing = lessons.categories.get(0).lessons.toArray(new LessonFile[lessons.categories.size()]);
			
			final LessonListAdapter adapter = new LessonListAdapter(this, R.layout.list_item, fileListing);
			
			ListView lv = getMyListView();
	        lv.setAdapter(adapter);
			lv.setTextFilterEnabled(true);
			lv.setOnItemClickListener(new LessonStartClickListener(this, adapter, xmlReplacement, whereShitIs));
		}
		
		
    }
    
    private ListView getMyListView() {
		return (ListView)this.findViewById(R.id.list);
	}

}
