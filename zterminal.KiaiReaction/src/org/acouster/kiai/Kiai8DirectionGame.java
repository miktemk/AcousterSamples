package org.acouster.kiai;

import java.util.List;
import java.util.Vector;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ResourceContext;
import org.acouster.graphics.math.GameMath;

import android.graphics.Color;
import android.util.Log;

public class Kiai8DirectionGame extends Game {

	public static final int ARROW_SCREEN_W = 100;
	public static final  int ARROW_SCREEN_H = 100;
	
	protected ContextBitmap bmpArrowUp, bmpArrowUpRed;
	protected List<KiaiArrow> arrows;
	
	public Kiai8DirectionGame() {
		// redundant?
		super();
		
		bmpArrowUp = ResourceContext.instance().LoadBitmap("arrow_up.png");
		bmpArrowUpRed = ResourceContext.instance().LoadBitmap("arrow_up_red.png");
		
		arrows = new Vector<KiaiArrow>();
		// top left
		arrows.add(
			new KiaiArrow(ARROW_SCREEN_W, ARROW_SCREEN_H, -45, -1, 1)
			.setLeft(0)
			.setTop(0));
		// top right
		arrows.add(
			new KiaiArrow(ARROW_SCREEN_W, ARROW_SCREEN_H, 45, 1, 1)
			.setRight(0)
			.setTop(0));
		// bottom left
		arrows.add(
			new KiaiArrow(ARROW_SCREEN_W, ARROW_SCREEN_H, -135, -1, -1)
			.setLeft(0)
			.setBottom(0));
		// bottom right
		arrows.add(
			new KiaiArrow(ARROW_SCREEN_W, ARROW_SCREEN_H, 135, 1, -1)
			.setRight(0)
			.setBottom(0));
		
		// print test
//		for (KiaiArrow arrow : arrows)
//		{
//			Log.v(DebugUtil.TAG, arrow.computeX(400) + " : " + arrow.computeY(400) + " : " + arrow.getAngle());
//		}
	}

	// test shite
	public static final int ARROW_LIFE_TICKS = 20;
	public static final double CRITICAL_ACCEL_VECTOR_LENGTH_SQUARED = 20;
	private KiaiArrow curArrow;
	private int curArrowCountDown = 0;
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
		
		if (curArrowCountDown > 0)
			curArrowCountDown--;
		if (curArrowCountDown == 0)
			curArrow = null;
		
		//curA  += 5;
	}

	@Override
	public void paintCharacters(ContextGraphics g, int width, int height) {
		super.paintCharacters(g, width, height);
		
		//g.setColor(Color.WHITE);
		g.setFill(Color.WHITE);
		g.fillBackground();
		
		for (KiaiArrow arrow : arrows)
		{
			ContextBitmap bmp = bmpArrowUp;
			if (arrow == curArrow)
				bmp = bmpArrowUpRed;
			
			g.drawImageCentered(
				bmp,
				arrow.computeX(width),
				arrow.computeY(height),
				ARROW_SCREEN_W,
				ARROW_SCREEN_H,
				arrow.getAngle());
			
			final double accelK = 50;
			g.setColor(Color.BLACK);
			g.drawLine(200, 200, 200 + accelK*mSensorX, 200 - accelK*mSensorY);
			
			bmp = bmpArrowUp;
		}
		
//		g.drawImageCentered(bmpArrowUp, 50, 50, 100, 100, -45);
//		g.drawImage(bmpArrowUp, 20, 20, 100, 100);
//		g.drawImage(bmpArrowUp, 150, 20, 100, 100, 90);
//		g.drawImage(bmpArrowUp, 60, 400, 100, 100);
//		g.drawImage(bmpArrowUp, 70, 20, 100, 100);
//		g.drawImage(bmpArrowUp, 200, 20, 100, 100);
//		
//		if (Math.random() > 0.95)
//			g.drawImage(bmpArrowUpRed, 20, 20, 100, 100);
		
	}

	float mSensorX;
	float mSensorY;
	public void setAccelerometerCoords(float mSensorX, float mSensorY) {
		this.mSensorX = mSensorX;
		this.mSensorY = mSensorY;
		
		// see if this activates an arrow
		if ((curArrow == null) && (GameMath.distanceSquared(0, 0, mSensorX, mSensorY) >= CRITICAL_ACCEL_VECTOR_LENGTH_SQUARED))
		{
			double minDist = 0;
			KiaiArrow minArrow = null;
			for (KiaiArrow arrow : arrows)
			{
				double dist = GameMath.vectorDifferenceNormalized(mSensorX, mSensorY, arrow.getAccelVectorX(), arrow.getAccelVectorY());
				if (minArrow == null || dist < minDist)
				{
					minDist = dist;
					minArrow = arrow;
				}
			}
			curArrow = minArrow;
			curArrowCountDown = ARROW_LIFE_TICKS;
			Log.v(DebugUtil.TAG, "zzzzzzzzonk");
		}
	}

}
