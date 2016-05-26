package org.acouster.android.platinum;

import org.acouster.platinum.xml.menu.LessonFile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LessonListAdapter extends ArrayAdapter<LessonFile>
{
	private LessonFile[] objects;
	private Context context;
	
	public LessonListAdapter(Context context, int textViewResourceId, LessonFile[] objects)
	{
		super(context, textViewResourceId, objects);
		this.objects = objects;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.lesson_list_item, null);
		}
		LessonFile record = objects[position];
		if (record != null) {
			TextView txtNum = (TextView) v.findViewById(R.id.txt_num);
			if (txtNum != null)
				txtNum.setText("" + (1+position));
			TextView txt = (TextView) v.findViewById(R.id.txt);
			if(txt != null)
				txt.setText(record.getUiTitle());
		}
		return v;
	}
	
}
