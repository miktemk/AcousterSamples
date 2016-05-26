package useless;

import org.acouster.android.eyefitness.EyeInstruction;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;


public class EyeVisual extends RenderableObject2D
{
	public static final double RADIUS = 20;
	
	protected EyeSprite eye;
	protected double radius;
	
	public EyeVisual(EyeSprite eye)
	{
		this(eye, RADIUS);
	}
	public EyeVisual(EyeSprite eye, double radius)
	{
		super(eye);
		this.eye = eye;
		this.radius = radius;
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
			g.drawCircle(x, y, eyeballRadius);
			g.setFill(0x0);
			g.fillCircle(x + radius * eye.getEyeVectorX(), y + radius * eye.getEyeVectorY(), 10);
			break;
		case EyeInstruction.STATE_CLOSED:
			g.drawCircle(x, y, eyeballRadius);
			g.drawLine(x - eyeballRadius, y, x + eyeballRadius, y);
			break;
		case EyeInstruction.STATE_SHUT_TIGHT:
			g.drawLine(x - eyeballRadius, y, x + eyeballRadius, y + eyeballRadius);
			g.drawLine(x - eyeballRadius, y, x + eyeballRadius, y - eyeballRadius);
			break;
		default:
			break;
		}
	}

}
