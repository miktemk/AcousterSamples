package org.acouster.lingorepeater;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.acouster.android.context.AndroidResourceContext;
import org.acouster.android.lingorepeater.R;
import org.acouster.android.ui.ListFilesActivity;

import android.os.Bundle;

public class MenuActivity extends ListFilesActivity
{
	private static final int SUB_ACTIVITY_LESSON = 1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	}
	
	@Override
	protected ListFilesActivityElement[] CreateList()
	{
		MyAndroidResourceContext.initIfNotReady(this);
		
		List<ListFilesActivityElement> list = new Vector<ListFilesActivityElement>();
		
		File dir = AndroidResourceContext.ainstance().getExternalFile(getString(R.string.ExternalStoragePath));
		File[] lisdd = dir.listFiles();
		for (File fff : lisdd)
		{
			list.add(new ListFilesActivityElement(fff.getName(), fff.getPath()));
		}

		return list.toArray(new ListFilesActivityElement[list.size()]);
    }
	
	@Override
	protected Class<?> getSubActivityClass() {
		return LingoRepeaterActivity.class;
	}

	@Override
	protected int getSubActivityId() {
		return SUB_ACTIVITY_LESSON;
	}

	
//	// menu
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//		KarateBaseActivity.commonOnCreateOptionsMenu(menu);
//		return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return KarateBaseActivity.commonOnOptionsItemSelected(this, item);
//    }
}
