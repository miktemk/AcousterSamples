package useless;

import org.acouster.android.eyefitness.EyeInstruction;
import org.acouster.logic.Sprite2D;
import org.acouster.util.*;

public class EyeSprite extends Sprite2D {

	private int state;
	private double dx, dy;
	
	public EyeSprite()
	{
		super();
		state = EyeInstruction.STATE_OPEN;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void setEyeVector(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
		normalizeIfNeeded();
	}
	public void setEyeByAngle(double angle)
	{
		this.dx = Math.cos(angle);
		this.dy = Math.sin(angle);
		normalizeIfNeeded();
	}
	public double getEyeVectorX() {
		return dx;
	}
	public double getEyeVectorY() {
		return dy;
	}

	private void normalizeIfNeeded()
	{
		double len = MathUtils.distance(0, 0, dx, dy);
		if (len < EyeVisual.RADIUS)
			len = EyeVisual.RADIUS;
		dx /= len;
		dy /= len;
	}

}
