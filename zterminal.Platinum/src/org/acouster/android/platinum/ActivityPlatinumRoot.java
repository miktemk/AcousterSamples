package org.acouster.android.platinum;

import java.io.IOException;

import org.acouster.DebugUtil;
import org.acouster.android.ui.BaseActivity;
import org.acouster.platinum.xml.menu.PlatMain;
import org.acouster.platinum.xml.menu.PlatMainLanguage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityPlatinumRoot extends BaseActivity  {
	
	protected static final int SUB_ACTIVITY_PLAY = 0;
	protected static final String EXTRA_categoryName = "categoryName";
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_main);
        new MyResourceContext(this).makeInstance();
        new PlatUtils(getString(R.string.ExternalStoragePath)).makeInstance();
        
        Log.v(DebugUtil.TAG, "i am weasel");
        
        // get passed parameters
        final String categoryName = getExtraString(EXTRA_categoryName);
        
        // load the shite
        final PlatMain metadata = loadMeta();
        final PlatMainLanguage[] catListing = metadata.languages_toArray();
        final ArrayAdapter<PlatMainLanguage> adapter = new ArrayAdapter<PlatMainLanguage>(this, R.layout.root_list_item, catListing) {
			@Override
			public View getView (int position, View convertView, ViewGroup parent) {
				View v = convertView;
				if (v == null) {
					LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.root_list_item, null);
				}
				PlatMainLanguage record = catListing[position];
				if (record != null) {
					ImageView img = (ImageView) v.findViewById(R.id.img);
					if (img != null)
						//img.setImageResource(record.getImgResourceId());
						img.setImageBitmap(MyResourceContext.myCurrent().loadBitmapFromFile(MyResourceContext.EXTERNAL_XML_PREFIX + record.imgFilename));
					TextView txt = (TextView) v.findViewById(R.id.txt);
					if(txt != null)
						txt.setText(record.name);
				}
				return v;
			}
		};
		
		ListView lv = getMyListView();
        lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	{
        		Intent i = new Intent(ActivityPlatinumRoot.this, ActivityMain.class);
        		PlatMainLanguage item = adapter.getItem(position);
        		i.putExtra(Constants.EXTRA_lessonFilename, MyResourceContext.EXTERNAL_XML_PREFIX + item.xmlPath);
        		i.putExtra(Constants.EXTRA_XmlReplacement, item.xmlReplacement);
        		i.putExtra(Constants.EXTRA_externalPath, item.externalPath);
				startActivityForResult(i, SUB_ACTIVITY_PLAY);
			}

        });
    }
    
    private PlatMain loadMeta()
    {
		try {
			return MyResourceContext.myCurrent().LoadAnyExternal(PlatMain.class, MyResourceContext.EXTERNAL_XML_PREFIX + "main.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PlatMain();
	}

	private ListView getMyListView() {
		return (ListView)this.findViewById(R.id.list);
	}
    
    
    
}