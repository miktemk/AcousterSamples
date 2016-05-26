package org.acouster.guitarPractice;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.guitarPractice.xml.XGuitarSong;

public class MainGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_MAIN = 2;
	
	private WorldLayer layerBg, layerMain;
	private UIActionEngine uiActions;
	private XGuitarSong song;
	private StitchedNotesVisual stitchedNotes;
	

	public MainGame(GameContext gameContext, XGuitarSong song)
	{
		super(gameContext);
		
		this.song = song;
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerMain = new WorldLayer(LAYER_LEVEL_MAIN));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		layerBg.addRenderable(new FullScreenSolidColor(0x0));
		layerMain.addRenderable(stitchedNotes = new StitchedNotesVisual(song));
	}
	
	@Override
	public void incrementCharacters(int width, int height)
	{
		super.incrementCharacters(width, height);
		if (isContextDimensionsChanged())
		{
			// bump shit here... althought with the new API it is hardly necessary
		}
	}
	
	@Override
	public void keyPressed(ContextKeyEvent e)
	{
		super.keyPressed(e);
		
		System.out.println("key pressed " + e.getKeyCode() + " : " + e.getEventType());
		switch (e.getKeyCode())
		{
		case ContextKeyEvent.VK_UP:
		case ContextKeyEvent.VK_NUMPAD8:
		case ContextKeyEvent.ANDROID_NUMPAD8:
			stitchedNotes.setPermYOffset(-Constants.Values.YStep);
			break;
		case ContextKeyEvent.VK_DOWN:
		case ContextKeyEvent.VK_NUMPAD2:
		case ContextKeyEvent.ANDROID_NUMPAD2:
			stitchedNotes.setPermYOffset(Constants.Values.YStep);
			break;
		case ContextKeyEvent.VK_PAGE_UP:
		case ContextKeyEvent.VK_NUMPAD9:
		case ContextKeyEvent.ANDROID_NUMPAD9:
			stitchedNotes.prevPage();
			break;
		case ContextKeyEvent.VK_PAGE_DOWN:
		case ContextKeyEvent.VK_NUMPAD3:
		case ContextKeyEvent.ANDROID_NUMPAD3:
			stitchedNotes.nextPage();
			break;
		case ContextKeyEvent.VK_LEFT:
		case ContextKeyEvent.VK_NUMPAD4:
		case ContextKeyEvent.ANDROID_NUMPAD4:
			stitchedNotes.decZoom();
			break;
		case ContextKeyEvent.VK_RIGHT:
		case ContextKeyEvent.VK_NUMPAD6:
		case ContextKeyEvent.ANDROID_NUMPAD6:
			stitchedNotes.incZoom();
			break;
		case ContextKeyEvent.VK_MULTIPLY:
		case ContextKeyEvent.ANDROID_MULTIPLY:
			sendEventToContext(Game.DIRECTIVE_KILL_ACTIVITY);
			break;
		}
		gameContext.triggerRepaint();
	}
	
	int y0;
	
	@Override
	public void mousePressed(ContextMouseEvent e) {
		y0 = e.getY();
		gameContext.forceStartThread();
	}
	
	@Override
	public void mouseDragged(ContextMouseEvent e) {
		stitchedNotes.setTempYOffset(-(e.getY() - y0));
	}
	
	@Override
	public void mouseReleased(ContextMouseEvent e) {
		stitchedNotes.setPermYOffset(-(e.getY() - y0));
		gameContext.forceStopThread();
		gameContext.triggerRepaint();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		stitchedNotes.disposeAll();
	}

}
