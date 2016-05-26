package org.acouster.android.platinum;

import java.io.IOException;

import org.acouster.android.ui.BaseActivity;
import org.acouster.platinum.xml.menu.LessonFile;
import org.acouster.platinum.xml.menu.Lessons;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityCategory extends BaseActivity  {
	
	protected static final int SUB_ACTIVITY_PLAY = 0;
	protected static final String EXTRA_categoryName = "categoryName";
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final String whereShitIs = getExtraString(Constants.EXTRA_externalPath, getString(R.string.ExternalStoragePath));
        final String xmlLessons = getExtraString(Constants.EXTRA_lessonFilename, MyResourceContext.LESSON_LISTING_DEFAULT_FRENCH_PATH);
        final String xmlReplacement = getExtraString(Constants.EXTRA_XmlReplacement, "xml");
        
        new MyResourceContext(this).makeInstance();
        new PlatUtils(whereShitIs).makeInstance();
        
        // get passed parameters
        final String categoryName = getExtraString(EXTRA_categoryName);
        
        // load the shite
        LessonFile[] catListing = new LessonFile[] {};
		try {
			Lessons lessons = MyResourceContext.myCurrent().LoadLessonsListing(xmlLessons);
			catListing = lessons.getCategory(categoryName).lessons.toArray(new LessonFile[lessons.categories.size()]);
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Could not load lesson list", Toast.LENGTH_SHORT).show();
		}
		
		final LessonListAdapter adapter = new LessonListAdapter(this, R.layout.list_item, catListing);
		
		ListView lv = getMyListView();
        lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new LessonStartClickListener(this, adapter, xmlReplacement, whereShitIs));
    }
    
    private ListView getMyListView() {
		return (ListView)this.findViewById(R.id.list);
	}

}