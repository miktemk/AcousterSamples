package org.acouster.eyefitness;

import java.util.HashMap;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.Graph;
import org.acouster.GraphTicker;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ContextMouseEvent;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.AnimatedStroke;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.logic.HtmlPositioner;
import org.acouster.logic.Sprite2DHtmlPositioned;
import org.acouster.util.GameUtils;
import org.acouster.util.StringUtils;


public class EyeFitnessGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_EYES = 2;
	private static final int LAYER_LEVEL_STROKES = 3;
	protected static final String OPTION_TEST = "test";
	protected static final String OPTION_TEST2 = "test2";
	protected static final String EYE_LEFT = "left";
	protected static final String EYE_RIGHT = "right";
	
	protected WorldLayer layerBg, layerEyes, layerStrokes;
	protected UIActionEngine uiActions;
//	private EyeSprite spriteEyeL, spriteEyeR;
//	private EyeVisual visualEyeL, visualEyeR;
	private FullScreenImage bgImage;
	private HashMap<String, AnimatedStroke> strokeMap;
	private GraphTicker<EyeInstruction> tickerOfInstruction;
	private EyeInstruction curInstruction = null;
	private ZorroMask zorroMask;
	private Sprite2DHtmlPositioned zorroMaskPositioner;
	
	public EyeFitnessGame(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerEyes = new WorldLayer(LAYER_LEVEL_EYES));
		addLayer(layerStrokes = new WorldLayer(LAYER_LEVEL_STROKES));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		// background
		layerBg.addRenderable(bgImage = new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource(Constants.Filenames.bg1), false, false)));
		
		// eyes (old!!!!!! delete this shit)
//		addSprite(spriteEyeL = new EyeSprite());
//		addSprite(spriteEyeR = new EyeSprite());
//		layerEyes.addRenderable(visualEyeL = new EyeVisualImage(spriteEyeL, 50, 25, bmpPupil));
//		layerEyes.addRenderable(visualEyeR = new EyeVisualImage(spriteEyeR, 50, 25, bmpPupil));
//		layerEyes.addRenderable(zorroMask = new ZorroMask(spriteEyeL, spriteEyeR,
//				ImageManager.instance().loadFromResource(Constants.Filenames.zorro1),
//				ImageManager.instance().loadFromResource(Constants.Filenames.zorro1_closed)));
		
		// eyes
		// TODO: skin
		layerEyes.addRenderable(zorroMask = new ZorroMask(addSprite(zorroMaskPositioner = GameUtils.makeCenteredSprite())));
		
		// strokes
		strokeMap = new HashMap<String, AnimatedStroke>();
		addStroke(EyeDoctor.CMD_L, UtilsStrokes.hardcodedPen_left_xml());
		addStroke(EyeDoctor.CMD_R, UtilsStrokes.hardcodedPen_right_xml());
		addStroke(EyeDoctor.CMD_U, UtilsStrokes.hardcodedPen_up_xml());
		addStroke(EyeDoctor.CMD_D, UtilsStrokes.hardcodedPen_down_xml());
		addStroke(EyeDoctor.CMD_ROUNDCL, UtilsStrokes.hardcodedPen_round_clock_xml());
		addStroke(EyeDoctor.CMD_ROUNDCC, UtilsStrokes.hardcodedPen_round_counter_clock_xml());
	}
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
		if (tickerOfInstruction != null)
			tickerOfInstruction.increment();
		if (curInstruction != null)
		{
			curInstruction.increment();
			zorroMask.setInstructions(curInstruction);
//			spriteEyeL.setState(curInstruction.getState());
//			spriteEyeR.setState(curInstruction.getState());
//			spriteEyeL.setEyeVector(curInstruction.getDx(), curInstruction.getDy());
//			spriteEyeR.setEyeVector(curInstruction.getDx(), curInstruction.getDy());
		}
		if (isContextDimensionsChanged() && isContextDimensionsValid())
		{
			zorroMask.setWidthKeepAspectRatio(GameDimensions.getZorroWidth(getContextDiagonal()));
		}
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height) {
		super.paintCharacters(g, width, height);
	}
	
	@Override
	public void mouseMoved(ContextMouseEvent e)
	{
		// DEBUG SHIT
//		spriteEyeL.setEyeVector(
//				e.getX() - spriteEyeL.getTransform().getX(),
//				e.getY() - spriteEyeL.getTransform().getY()
//			);
//		spriteEyeR.setEyeVector(
//				e.getX() - spriteEyeR.getTransform().getX(),
//				e.getY() - spriteEyeR.getTransform().getY()
//			);
	}
	
	// ---------- interaction (game control) ---------------
	/** called after constructor with w 1 & 10 */
	public void constructExerciseGraph(int intensity)
	{
		// graph
		Graph<EyeInstruction> gInstruction = EyeDoctor.generateExercise(intensity);
		gInstruction.setOnAdvanceListener(new Graph.OnAdvanceListener<EyeInstruction>() {
			@Override
			public void onAdvance(EyeInstruction inst) {
				if (inst == null)
					return;
				if (!StringUtils.isNullOrEmpty(inst.snd))
					sendEventToContext(DIRECTIVE_SOUND, inst.snd);
				if (!StringUtils.isNullOrEmpty(inst.stroke))
					activateStroke(inst.stroke);
				inst.reset();
				curInstruction = inst;
			}
		});
		gInstruction.gotoFirstNode();
		
		tickerOfInstruction = new GraphTicker<EyeInstruction>(gInstruction);
		//tInstruction.gotoFirstNode();
	}
	public void setArrowLayerVisible(boolean flag)
	{
		layerStrokes.setVisible(flag);
	}
	
	// ---------- privates ---------------
	private void addStroke(String key, AnimatedStroke stroke)
	{
		strokeMap.put(key, stroke);
		stroke.setActive(false);
		stroke.setTimeWaitAfter(500);
		layerStrokes.addRenderable(stroke);
	}
	private void activateStroke(String key)
	{
		AnimatedStroke stroke = strokeMap.get(key);
		if (stroke != null)
			stroke.playFromNow();
	}
	
	
}
