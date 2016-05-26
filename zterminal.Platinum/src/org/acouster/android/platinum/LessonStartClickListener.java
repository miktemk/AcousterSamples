package org.acouster.android.platinum;

import org.acouster.platinum.xml.menu.LessonFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class LessonStartClickListener implements OnItemClickListener
{
	private Activity packageContext;
	private ArrayAdapter<LessonFile> adapter;
	private String xmlReplacement, whereShitIs;
	
	public LessonStartClickListener(Activity packageContext,
			ArrayAdapter<LessonFile> adapter, String xmlReplacement,
			String whereShitIs) {
		super();
		//LessonStartClickListener = activityCategory;
		this.packageContext = packageContext;
		this.adapter = adapter;
		this.xmlReplacement = xmlReplacement;
		this.whereShitIs = whereShitIs;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent i = new Intent(packageContext, ActivityLesson.class);
		LessonFile item = adapter.getItem(position);
		String filename = MyResourceContext.EXTERNAL_XML_PREFIX + item.getFilename().replace("xml/", xmlReplacement + "/");
		i.putExtra(Constants.EXTRA_externalPath, whereShitIs);
		i.putExtra(Constants.EXTRA_lessonFilename, filename);
		packageContext.startActivityForResult(i, ActivityCategory.SUB_ACTIVITY_PLAY);
	}
}