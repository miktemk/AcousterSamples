package org.acouster.android.kyo;

import java.io.File;
import java.io.FileFilter;

import org.acouster.android.kyo.R;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGalleryAdapter extends BaseAdapter {
    private Context mContext;
    private File[] dirList;
    
    public ImageGalleryAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
    	File rootDir = new File(Environment.getExternalStorageDirectory(), KyoUtils.PREFIX_FOLDER);
        dirList = rootDir.listFiles();
    	if (dirList == null)
    		return 0;
        return dirList.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        File firstImageFile = dirList[position].listFiles()[0];
        Uri imgUri = Uri.fromFile(firstImageFile);
        imageView.setImageURI(imgUri);
        return imageView;
    }

	public String getFolderPrefix(int position) {
		if (dirList == null)
			return null;
		return dirList[position].getPath();
	}
}