package org.acouster.eyefitness;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BasicGraphics;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;
import org.acouster.math.Vector2D;

public class ZorroMask extends RenderableObject2D
{
	// TODO: skin this shit
	private static final float PUPUL_POS_FACTOR = 0.25f;
	private static final float FACTOR_PUPIL_SIZE = 0.1f;
	private static final float FACTOR_EYE_RADIUSX = 0.1f;
	private static final float FACTOR_EYE_RADIUSY = 0.05f;
	
	private EyeInstruction instruction;
	private ContextBitmap bmpPupil, bmpOpen, bmpClosed, bmpShutTight;
	private int pupilWidth, pupilHeight;
	private float eyeRadiusX, eyeRadiusY;
	
	public ZorroMask(Sprite2D trans) {
		super(trans);
		// TODO: skinning
		bmpPupil = ImageManager.loadR(Constants.Filenames.pupil1);
		bmpOpen = ImageManager.loadR(Constants.Filenames.zorro1);
		bmpClosed = ImageManager.loadR(Constants.Filenames.zorro1_closed);
		bmpShutTight = ImageManager.loadR(Constants.Filenames.zorro1_closed);
		setDefaultDimensions(bmpOpen.getWidth(), bmpOpen.getHeight());
	}

	public EyeInstruction getInstructions() {
		return instruction;
	}
	public void setInstructions(EyeInstruction instructions) {
		this.instruction = instructions;
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
//		double xCenter = (spriteEyeL.getTransform().getX() + spriteEyeR.getTransform().getX()) / 2;
//		double yCenter = (spriteEyeL.getTransform().getY() + spriteEyeR.getTransform().getY()) / 2;
		int xMask = sprite.getIntX();
		int yMask = sprite.getIntY();
		
		BasicGraphics.drawCrosshair(g, xMask, yMask);
		
		if (instruction == null)
		{
			g.drawImage(bmpClosed, xMask, yMask, getResultWidth(), getResultHeight());
			return;
		}
		
		switch (instruction.getState())
		{
		case EyeInstruction.STATE_OPEN:
			drawPupils(g);
			g.drawImage(bmpOpen, xMask, yMask, getResultWidth(), getResultHeight());
			break;
		case EyeInstruction.STATE_CLOSED:
			g.drawImage(bmpClosed, xMask, yMask, getResultWidth(), getResultHeight());
			break;
		case EyeInstruction.STATE_SHUT_TIGHT:
			g.drawImage(bmpShutTight, xMask, yMask, getResultWidth(), getResultHeight());
			break;
		default:
			break;
		}

	}

	private void drawPupils(ContextGraphics g)
	{
		int xCenter = sprite.getIntX() + getResultWidth()/2;
		int yCenter = sprite.getIntY() + getResultHeight()/2;
		float xL = xCenter - getResultWidth() * PUPUL_POS_FACTOR;
		float xR = xCenter + getResultWidth() * PUPUL_POS_FACTOR;
		float yL = yCenter, yR = yCenter;
		
		BasicGraphics.drawCrosshair(g, (int)xL, (int)yL);
		BasicGraphics.drawCrosshair(g, (int)xR, (int)yR);
		
		instruction.getVectorL(Vector2D.shared);
		g.drawImageCentered(bmpPupil, (int)(xL + Vector2D.shared.getX() * eyeRadiusX), (int)(yL + Vector2D.shared.getY() * eyeRadiusY), pupilWidth, pupilHeight);
		instruction.getVectorR(Vector2D.shared);
		g.drawImageCentered(bmpPupil, (int)(xR + Vector2D.shared.getX() * eyeRadiusX), (int)(yR + Vector2D.shared.getY() * eyeRadiusY), pupilWidth, pupilHeight);
	}
	
	@Override
	protected void onDimensionsChanged(int w, int h)
	{
		int diagonal = w + h;
		pupilWidth = (int) (diagonal * FACTOR_PUPIL_SIZE);
		pupilHeight = (int) (diagonal * FACTOR_PUPIL_SIZE * bmpPupil.getHeight() / bmpPupil.getWidth());
		eyeRadiusX = diagonal * FACTOR_EYE_RADIUSX;
		eyeRadiusY = diagonal * FACTOR_EYE_RADIUSY;
	}
}
