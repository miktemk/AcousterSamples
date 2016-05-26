package org.acouster.lingorepeater;

import java.io.IOException;

import org.acouster.android.lingorepeater.R;
import org.acouster.android.ui.BaseActivity;
import org.acouster.android.ui.ListFilesActivity;
import org.acouster.context.ResourceContext;
import org.acouster.lingorepeater.xml.PhraseList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LingoRepeaterActivity extends BaseActivity 
{
    private PhraseList phrases;
    private int indexNext;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MyAndroidResourceContext.initIfNotReady(this);
        
        setContentView(R.layout.lesson);
        
        //btnRandIndex
        //btnNext
        
        Button b;
        b = (Button)findViewById(R.id.btnNext);
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (phrases == null)
					return;
				if (phrases.phrases.size() == 0)
					return;
				playNextPhrase();
				indexNext++;
				if (indexNext >= phrases.phrases.size())
					indexNext = 0;
			}
		});
        b = (Button)findViewById(R.id.btnRandIndex);
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (phrases == null)
					return;
				if (phrases.phrases.size() == 0)
					return;
				indexNext = (int)(Math.random() * phrases.phrases.size());
				playNextPhrase();
			}
		});
        
        // lesson setup
        indexNext = 0;
        String filename = getExtraString(ListFilesActivity.EXTRA_FileName);
        try {
        	phrases = ResourceContext.instance().LoadAnyExternal(PhraseList.class, filename);
        	
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //
    }
	
	private void playNextPhrase()
	{
		if (indexNext < 0 || indexNext >= phrases.phrases.size())
		{
			Toast.makeText(this, "Index out of range: " + indexNext, Toast.LENGTH_LONG).show();
			return;
		}
		String text = phrases.phrases.get(indexNext).value;
		TextView txt = (TextView)findViewById(R.id.txtPhrase);
		txt.setText(text);
		speakText(text);
	}
	
	@Override
	public void onTimeForRequestingFeatures()
	{
		//activateAccelerometer();
		activateTts();
	}
}