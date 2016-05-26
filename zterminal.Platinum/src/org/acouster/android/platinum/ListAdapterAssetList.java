package org.acouster.android.platinum;
//package acouster.game.platdeu;
//
//import java.io.IOException;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.database.DataSetObserver;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//
//
//// TODO: move this merde to common
//public class ListAdapterAssetList extends BaseAdapter {
//
//	protected String[] dirListing;
//	protected Context context;
//	protected String pathPrefix;
//
//	public ListAdapterAssetList(Context c, String path) {
//		context = c;
//		pathPrefix = path;
//		AssetManager assetManager = c.getAssets();
//        try {
//			dirListing = assetManager.list(path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		//dirListing = new String[] {"aaa", "bbb12121", "bb2b", "bbb3", "bdbb4"};
//	}
//	
//	@Override
//	public int getCount() {
//		return dirListing.length;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return dirListing[position];
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		TextView textView = null;
//		if (convertView == null)
//		{
//			textView = new TextView(context);
//			textView.setTextSize(26);
//		}
//		else
//		{
//			textView = (TextView) convertView;
//		}
//		
//		String text = dirListing[position];
//		textView.setText(text);
//		
//		return textView;
//	}
//
//	public String getFileAssetPath(int position) {
//		return pathPrefix + "/" + dirListing[position];
//	}
//
//}
