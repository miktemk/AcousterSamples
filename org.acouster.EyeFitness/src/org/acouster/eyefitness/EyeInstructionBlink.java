package org.acouster.eyefitness;

public class EyeInstructionBlink extends EyeInstruction
{
	public static final long BLINK_EVERY_MS = 250;
	public static final long BLINK_OUT_OF_THAT_TIME_CLOSED = 150;
	
	protected long blinkTime;
	protected long now;
	
	public EyeInstructionBlink(int blinkTimeSeconds, String snd, String stk)
	{
		super(0, 0, snd, stk);
		this.blinkTime = blinkTimeSeconds * 1000;
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
		if (dt > blinkTime)
		{
			state = STATE_OPEN;
			return;
		}
		if (dt % BLINK_EVERY_MS < BLINK_OUT_OF_THAT_TIME_CLOSED)
			state = STATE_CLOSED;
		else
			state = STATE_OPEN;
	}
}
