package org.acouster.kiai;

import org.acouster.graphics.utils.HtmlPositioner;

public class KiaiArrow extends HtmlPositioner {

	protected double angle;
	protected double accelVectorX, accelVectorY;
	
	// builder pattern
	public KiaiArrow(int width, int height, double angle, double accelVectorX, double accelVectorY) {
		super(width, height);
		this.angle = angle;
		this.accelVectorX = accelVectorX;
		this.accelVectorY = accelVectorY;
	}
	public KiaiArrow setTop(int top) {
		super.setTop(top);
		return this;
	}
	public KiaiArrow setBottom(int bottom) {
		super.setBottom(bottom);
		return this;
	}
	public KiaiArrow setLeft(int left) {
		super.setLeft(left);
		return this;
	}
	public KiaiArrow setRight(int right) {
		super.setRight(right);
		return this;
	}
	
	public double getAngle() {
		return angle;
	}
	public double getAccelVectorX() {
		return accelVectorX;
	}
	public double getAccelVectorY() {
		return accelVectorY;
	}
	

}
