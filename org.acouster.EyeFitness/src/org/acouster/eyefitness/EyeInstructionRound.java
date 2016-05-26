package org.acouster.eyefitness;

public class EyeInstructionRound extends EyeInstruction
{
	private static final double RADIUS = 100;
	protected double startClock;
	protected boolean isCounter;
	protected long loopTime;
	protected long now;
	public EyeInstructionRound(
			double startClock,
			boolean isCounter,
			long loopTimeSeconds,
			String snd,
			String stk)
	{
		super(0, 0, snd, stk);
		this.startClock = startClock;
		this.isCounter = isCounter;
		this.loopTime = loopTimeSeconds * 1000;
	}
	
	@Override
	public void reset() {
		super.reset();
		now = System.currentTimeMillis();
		increment();
	}
	
	@Override
	public void increment() {
		long dt = System.currentTimeMillis() - now;
		if (dt > loopTime)
		{
			dxL = dyL = dxR = dyR = 0;
			return;
		}
		
		int counterFactor = 1;
		if (isCounter)
			counterFactor = -1;
		double angle = -Math.PI/2 + counterFactor * 2 * Math.PI * dt / loopTime;
		dxL = dxR = RADIUS * Math.cos(angle);
		dyL = dyR = RADIUS * Math.sin(angle);
	}
	
}
