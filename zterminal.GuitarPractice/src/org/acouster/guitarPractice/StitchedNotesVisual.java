package org.acouster.guitarPractice;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.guitarPractice.xml.XGuitarImage;
import org.acouster.guitarPractice.xml.XGuitarSong;

public class StitchedNotesVisual extends RenderableObject2D
{
	
	private ContextBitmap bmp, bmp2;
	private int yTmpOffset;
	//private int yPos;
	
	private String[] filenames;
	private int typicalW, typicalH;
	private int curIndex = 0,
			frameY, frameW;
	private double zoomFactor = 1.0;
	
	public StitchedNotesVisual(XGuitarSong song)
	{
		super(null);
		
		filenames = new String[song.images.size()];
		int iii = 0;
		for (XGuitarImage img : song.images)
		{
			filenames[iii] = img.src;
			iii++;
		}
		
		curIndex = 0;
		loadImages();
		typicalW = bmp.getWidth();
		typicalH = bmp.getHeight();
		frameY = 0;
		frameW = typicalW;
		
		framePositionChanged();
	}
	
	private void loadImages()
	{
		disposeAll();
		bmp = ImageManager.instance().loadFromFile(Constants.Filenames.ExternalStoragePath + "/" + filenames[curIndex]);
		if (curIndex+1 < filenames.length)
			bmp2 = ImageManager.instance().loadFromFile(Constants.Filenames.ExternalStoragePath + "/" + filenames[curIndex+1]);
		else
			bmp2 = null;
	}
	private void loadImagesBumpUp()
	{
		if (bmp2 != null)
			ImageManager.dispose(bmp2);
		bmp2 = bmp;
		bmp = ImageManager.instance().loadFromFile(Constants.Filenames.ExternalStoragePath + "/" + filenames[curIndex]);
	}
	private void loadImagesBumpDown()
	{
		if (bmp != null)
			ImageManager.dispose(bmp);
		bmp = bmp2;
		if (curIndex+1 < filenames.length)
			bmp2 = ImageManager.instance().loadFromFile(Constants.Filenames.ExternalStoragePath + "/" + filenames[curIndex+1]);
		else
			bmp2 = null;
	}
	public void disposeAll()
	{
		if (bmp != null)
			ImageManager.dispose(bmp);
		if (bmp2 != null)
			ImageManager.dispose(bmp2);
		bmp = bmp2 = null;
	}

	private void framePositionChanged()
	{
		frameW = (int)(typicalW / zoomFactor);
		
		// scroll up
		if (frameY < 0 && curIndex > 0)
		{
			frameY += typicalH;
			curIndex--;
			loadImagesBumpUp();
		}
		
		// scroll down
		if (frameY > typicalH && curIndex < filenames.length-1)
		{
			frameY = 0;
			curIndex++;
			loadImagesBumpDown();
		}
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		// TODO: add zooming
		int wi = w * typicalW / frameW;
		int hi = typicalH * wi / typicalW;
		
		int offsetX = -(wi - w) / 2;
		int offsetY = hi * frameY / typicalH + yTmpOffset;
		
		if (bmp != null)
			g.drawImage(bmp, offsetX, -offsetY, wi, hi);
		// TODO: check if 2 is even visible
		if (bmp2 != null)
			g.drawImage(bmp2, offsetX, -offsetY + hi, wi, hi);
		//if (offsetY > hi - h)
		//	g.drawImage(bmp2, offsetX, -offsetY + hi, wi, hi);
	}
	
	@Override
	protected void onDimensionsChanged(int w, int h) {
		super.onDimensionsChanged(w, h);
	}

	public void setTempYOffset(int yOffset)
	{
		yTmpOffset = yOffset;
	}
	public void setPermYOffset(int yOffset)
	{
		frameY += yOffset;
		yTmpOffset = 0;
		framePositionChanged();
	}

	public void prevPage() {
		frameY = 0;
		curIndex--;
		if (curIndex < 0)
			curIndex = 0;
		loadImages();
	}
	public void nextPage() {
		frameY = 0;
		curIndex++;
		if (curIndex >= filenames.length)
			curIndex = filenames.length-1;
		loadImages();
	}
	
	public void incZoom()
	{
		zoomFactor *= Constants.Values.ZoomStep;
		framePositionChanged();
	}
	public void decZoom()
	{
		zoomFactor /= Constants.Values.ZoomStep;
		if (zoomFactor < 1.0)
			zoomFactor = 1.0;
		framePositionChanged();
	}

}
