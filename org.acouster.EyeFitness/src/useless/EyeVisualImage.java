package useless;

import org.acouster.android.eyefitness.EyeInstruction;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;


public class EyeVisualImage extends EyeVisual
{
	protected ContextBitmap bmp;
	protected double radiusX, radiusY;
	
	public EyeVisualImage(EyeSprite eye, double radiusX, double radiusY, ContextBitmap bmp)
	{
		super(eye, radiusX);
		
		this.bmp = bmp;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		double x = sprite.getTransform().getX();
		double y = sprite.getTransform().getY();
		double eyeballRadius = radius+12;
		
		g.setColor(0x0);
		switch (eye.getState())
		{
		case EyeInstruction.STATE_OPEN:
			g.drawImageCentered(bmp, (int)(x + radiusX * eye.getEyeVectorX()), (int)(y + radiusY * eye.getEyeVectorY()), bmp.getWidth(), bmp.getHeight());
			break;
		case EyeInstruction.STATE_CLOSED:
			//g.drawCircle(x, y, eyeballRadius);
			//g.drawLine(x - eyeballRadius, y, x + eyeballRadius, y);
			break;
		case EyeInstruction.STATE_SHUT_TIGHT:
			//g.drawLine(x - eyeballRadius, y, x + eyeballRadius, y + eyeballRadius);
			//g.drawLine(x - eyeballRadius, y, x + eyeballRadius, y - eyeballRadius);
			break;
		default:
			break;
		}
	}

}
