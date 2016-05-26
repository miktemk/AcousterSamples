package org.acouster.kyo;

import org.acouster.ActionEngine;
import org.acouster.Game;
import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;



public class KyoActionEngine extends ActionEngine {

	private boolean slapIssued = false;
	private int x0 = 0, y0 = 0;
	
	public KyoActionEngine(Game game)
	{
		super(game);
	}
	
	public void mouseClicked(ContextMouseEvent arg0){}
	public void mouseEntered(ContextMouseEvent arg0){}
	public void mouseExited(ContextMouseEvent arg0){}
	public void mouseMoved(ContextMouseEvent arg0){}
	
	public void mousePressed(ContextMouseEvent arg0)
	{
		x0 = arg0.getX();
		y0 = arg0.getY();
	}
	public void mouseDragged(ContextMouseEvent arg0)
	{
		//TODO: this is some pretty random logic
		if (slapIssued)
			return;
		if (Math.abs(x0 - arg0.getX()) > 100)
		{
			slapIssued = true;
			if (x0 > arg0.getX())
				sendRecycledMessage("avatar", "L");
			else
				sendRecycledMessage("avatar", "R");
		}
	}

	public void mouseReleased(ContextMouseEvent arg0)
	{
		slapIssued = false;
	}
	
	// kb events
	public void keyPressed(ContextKeyEvent arg0) {}
	public void keyReleased(ContextKeyEvent arg0) {}
	public void keyTyped(ContextKeyEvent arg0) {}

}
