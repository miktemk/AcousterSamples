package org.acouster.eyefitness;

import org.acouster.math.Vector2D;

public class EyeInstruction
{
	public static final int STATE_OPEN = 0;
	public static final int STATE_CLOSED = 1;
	public static final int STATE_SHUT_TIGHT = 2;
	
	protected double dxL, dyL, dxR, dyR;
	protected int state;
	public String snd, stroke;
	
	public EyeInstruction(int state, String snd, String stroke) {
		this(state, 0, 0, snd, stroke);
	}
	public EyeInstruction(double dx, double dy, String snd, String stroke) {
		this(STATE_OPEN, dx, dy, dx, dy, snd, stroke);
	}
	public EyeInstruction(int state, double dx, double dy, String snd, String stroke) {
		this(state, dx, dy, dx, dy, snd, stroke);
	}
	public EyeInstruction(int state, double dxL, double dyL, double dxR, double dyR, String snd, String stroke) {
		super();
		this.state = state;
		this.dxL = dxL;
		this.dyL = dyL;
		this.dxR = dxR;
		this.dyR = dyR;
		this.snd = snd;
		this.stroke = stroke;
	}
	
//	public double getDx() {
//		return dxL;
//	}
//	public double getDy() {
//		return dyL;
//	}
	public void getVectorL(Vector2D tome) {
		tome.set(dxL, dyL);
	}
	public void getVectorR(Vector2D tome) {
		tome.set(dxR, dyR);
	}
	public int getState() {
		return state;
	}
	
	public void reset() {}	
	public void increment() {}	
	
}
