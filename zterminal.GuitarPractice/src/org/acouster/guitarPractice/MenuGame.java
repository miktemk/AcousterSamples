package org.acouster.guitarPractice;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.InnerScreenFromWorldLayerAndAction;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.BubblePopVisual;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.ui.UIBubblyButton;
import org.acouster.graphics.ui.UIButton;
import org.acouster.graphics.ui.UILayoutManagerCentered2ColumnLandscape;
import org.acouster.guitarPractice.bubbles.BubblePopVisual_btnGo;
import org.acouster.guitarPractice.bubbles.BubblePopVisual_btnOpt;


public class MenuGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_BUTTONS = 3;
	private static final int SCREEN_MAIN = 3;
	private static final int SCREEN_DIFFICULTY = 4;
	
	public static final String OPTION_GO = "goooo";
	public static final String OPTION_OPT = "opttt";
	public static final String EXTRA_DIFFICULTY = "difficulty";
	
	protected WorldLayer layerBg, layerButtonsMain;
	protected UIActionEngine uiActions, uiActionsDifficulty;
	private UILayoutManagerCentered2ColumnLandscape layoutMain;
	
	public MenuGame(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerButtonsMain = new WorldLayer(LAYER_LEVEL_BUTTONS));
		
		layerBg.addRenderable(new FullScreenImage(new BitmapWithTransform(loadImage(Constants.Filenames.bg1), false, false), BitmapWithTransform.ROTATION_4_WAY_90));
		
		BubblePopVisual btnGoBub, btnOptBub;
		// TODO: make UIElementBase not extend RenderableObject, instead add a method to it getVisual() which we then can add to WorldLayer
		layerButtonsMain.addRenderable(btnGoBub = new BubblePopVisual_btnGo(addNewSprite2D()));
		layerButtonsMain.addRenderable(btnOptBub = new BubblePopVisual_btnOpt(addNewSprite2D()));
		
		// action engines
		final UIBubblyButton btnGo, btnOptions;
		addActionEngine(uiActions = new UIActionEngine(this));
		addActionEngine(uiActionsDifficulty = new UIActionEngine(this));
		
		IFuncVoid<UIButton> lambdaDisableUiActions = new IFuncVoid<UIButton>() {
			@Override
			public void lambda(UIButton value) {
				uiActions.setActive(false);
			}
		};
		uiActions
			.addUI(btnGo = new UIBubblyButton(btnGoBub)
			.setLambda(new IFuncVoid<UIButton>() {
				@Override
				public void lambda(UIButton value) {
					uiActions.setActive(true);
					sendEventToContext(OPTION_GO);
					//gotoInnerScreen(SCREEN_SOMESCREEN);
				}
			}, lambdaDisableUiActions ));
		uiActions
			.addUI(btnOptions = new UIBubblyButton(btnOptBub)
			.setLambda(new IFuncVoid<UIButton>() {
				@Override
				public void lambda(UIButton value) {
					uiActions.setActive(true);
					sendEventToContext(OPTION_OPT);
				}
			}, lambdaDisableUiActions));

		// layout
		layoutMain = new UILayoutManagerCentered2ColumnLandscape(0.15f, 0.05f, 0.15f);
		layoutMain.addUI(btnGo);
		layoutMain.addUI(btnOptions);
		
		// register screens here
		registerInnerScreen(SCREEN_MAIN, new InnerScreenFromWorldLayerAndAction(layerButtonsMain, uiActions).setActiveLambda(new IFuncVoid<InnerScreenFromWorldLayerAndAction>() {
			@Override
			public void lambda(InnerScreenFromWorldLayerAndAction value) {
				// do something when this screen is activated
			}
		}));
		
		// always pick a screen after you have registered them
		gotoInnerScreen(SCREEN_MAIN);
	}

	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
		if (isContextDimensionsChanged())
		{
			// realign the buttons
			layoutMain.rearrange(width, height);
		}
	}
	
//	@Override
//	public void paintCharacters(ContextGraphics g, int width, int height) {
//		super.paintCharacters(g, width, height);
//	}


}