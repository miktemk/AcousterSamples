package org.acouster.eyefitness;

import org.acouster.Graph;
import org.acouster.util.MathUtils;

/** generates exercises... i.e. EyeInstruction Graphs */
public class EyeDoctor
{
	public static final String ROOT_KEY_GO = "GO!";
	
	public static final String CMD_BEGIN = "begin";
	public static final String CMD_L = "left";
	public static final String CMD_R = "right";
	public static final String CMD_U = "up";
	public static final String CMD_D = "down";
	public static final String CMD_ROUNDCL = "round_clock";
	public static final String CMD_ROUNDCC = "round_counter_clock";
	public static final String CMD_AGAIN = "again";
	public static final String CMD_CLOSE = "close_tight";
	public static final String CMD_OPEN = "open";
	public static final String CMD_BLINK = "blink";
	public static final String CMD_STOP = "stop";
	
	private static final double DDD = 1.0;
	
	/**
	 * @param intensity - # b/w 1 & 10
	 */
	public static Graph<EyeInstruction> generateExercise(int intensity)
	{
		// TODO: more choices
		int randChoice = MathUtils.random(1);
		switch (randChoice)
		{
		case 0:
			return generateExercise_0(intensity);
		case 1:
			return generateExercise_1(intensity);
		default:
			// should never happen but who the fuck knows...
			return generateExercise_0(intensity);
		}
	}
	public static Graph<EyeInstruction> generateExercise_0(int intensity)
	{
		final int count2b = intensity + 4;
		final int count2bSmall = intensity/2 + 1;
		
		Graph<EyeInstruction> g = new Graph<EyeInstruction>();
		g.addNode(new EyeInstruction(0, 0, CMD_BEGIN, null), 2, ROOT_KEY_GO);
		
		//----------left-right
		for (int i = 0; i < count2b; i++)
		{
			g.addNode(new EyeInstruction(-DDD, 0, CMD_L, CMD_L), 2);
			g.addNode(new EyeInstruction(DDD, 0, CMD_R, CMD_R), 2);
			g.addNode(new EyeInstruction(0, -DDD, CMD_U, CMD_U), 2);
			g.addNode(new EyeInstruction(0, DDD, CMD_D, CMD_D), 2);
		}
		//----------round
		g.addNode(new EyeInstructionRound(0, false, 5, CMD_ROUNDCL, CMD_ROUNDCL), 6);
		for (int i = 0; i < count2bSmall; i++)
		{
			g.addNode(new EyeInstructionRound(0, false, 5, CMD_AGAIN, CMD_AGAIN), 6);
		}
		g.addNode(new EyeInstructionRound(0, true, 5, CMD_ROUNDCC, CMD_ROUNDCC), 6);
		for (int i = 0; i < count2bSmall; i++)
		{
			g.addNode(new EyeInstructionRound(0, true, 5, CMD_AGAIN, CMD_AGAIN), 6);
		}
		//----------shut
		g.addNode(new EyeInstruction(EyeInstruction.STATE_SHUT_TIGHT, CMD_CLOSE, null), 3);
		g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_OPEN, null), 2);
		for (int i = 0; i < count2bSmall; i++)
		{
			g.addNode(new EyeInstruction(EyeInstruction.STATE_SHUT_TIGHT, CMD_AGAIN, null), 3);
			g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_OPEN, null), 2);
		}
		//----------blink
		int blinkTime = count2b * 2;
		g.addNode(new EyeInstructionBlink(blinkTime, CMD_BLINK, null), blinkTime);
		g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_STOP, null), 2);
		
		return g;
	}
	
	public static Graph<EyeInstruction> generateExercise_1(int intensity)
	{
		final int count2b = intensity + 4;
		final int count2bSmall = intensity/2 + 1;
		
		Graph<EyeInstruction> g = new Graph<EyeInstruction>();
		g.addNode(new EyeInstruction(0, 0, CMD_BEGIN, null), 2, ROOT_KEY_GO);
		
		//----------shut
		g.addNode(new EyeInstruction(EyeInstruction.STATE_SHUT_TIGHT, CMD_CLOSE, null), 3);
		g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_OPEN, null), 2);
		for (int i = 0; i < count2bSmall; i++)
		{
			g.addNode(new EyeInstruction(EyeInstruction.STATE_SHUT_TIGHT, CMD_AGAIN, null), 3);
			g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_OPEN, null), 2);
		}
		//----------blink
		int blinkTime = count2b * 2;
		g.addNode(new EyeInstructionBlink(blinkTime, CMD_BLINK, null), blinkTime);
		g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_STOP, null), 2);
		//----------left-right
		for (int i = 0; i < count2b; i++)
		{
			g.addNode(new EyeInstruction(0, -DDD, CMD_U, CMD_U), 2);
			g.addNode(new EyeInstruction(0, DDD, CMD_D, CMD_D), 2);
		}
		for (int i = 0; i < count2b; i++)
		{
			g.addNode(new EyeInstruction(-DDD, 0, CMD_L, CMD_L), 2);
			g.addNode(new EyeInstruction(DDD, 0, CMD_R, CMD_R), 2);
		}
		//----------round
		g.addNode(new EyeInstructionRound(0, false, 5, CMD_ROUNDCL, CMD_ROUNDCL), 6);
		for (int i = 0; i < count2bSmall; i++)
		{
			g.addNode(new EyeInstructionRound(0, false, 5, CMD_AGAIN, CMD_AGAIN), 6);
		}
		g.addNode(new EyeInstructionRound(0, true, 5, CMD_ROUNDCC, CMD_ROUNDCC), 6);
		for (int i = 0; i < count2bSmall; i++)
		{
			g.addNode(new EyeInstructionRound(0, true, 5, CMD_AGAIN, CMD_AGAIN), 6);
		}
		//----------blink (again)
		g.addNode(new EyeInstructionBlink(blinkTime, CMD_BLINK, null), blinkTime);
		g.addNode(new EyeInstruction(EyeInstruction.STATE_OPEN, CMD_STOP, null), 2);
		
		return g;
	}
}
