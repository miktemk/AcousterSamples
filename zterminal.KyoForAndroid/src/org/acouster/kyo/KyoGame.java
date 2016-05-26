package org.acouster.kyo;

import java.io.IOException;

import org.acouster.*;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.android.MyAndroidResourceContext;
import org.acouster.graphics.ImageRenderable;
import org.acouster.graphics.SimpleWorld2D;
import org.acouster.kyo.xml.Avatar;
import org.acouster.logic.FsmSprite;
import org.acouster.logic.Sprite2D;



public class KyoGame extends Game
{
	// TODO: this is a temporary mess..... oh god it sux
	Sprite2D avatar;
	public KyoGame(GameContext container, String filename, IFunc<String, ContextBitmap> id2image)
	{
		super(container);
		setActionEngine(new KyoActionEngine(this));
		
		Avatar avatarXml;
		try {
			avatarXml = MyAndroidResourceContext.myCurrent().LoadAndCompileAvatar(filename, id2image);
			
			avatar = new FsmSprite(avatarXml.getLogic(), "avatar", 0, 0);
			addSprite(avatar);
			
			Graph<ContextBitmap> images = avatarXml.getVisuals().getLoadedImages();
			images.gotoNode("C");
			//images.printAllPaths();
			ImageRenderable avatarRenderable = new ImageRenderable(avatar, images);
			graphicals.addRenderableToLayer(avatarRenderable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void incrementCharacters(int width, int height) {
//		super.incrementCharacters(width, height);
//	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height)
	{
		super.paintCharacters(g, width, height);
		//BasicGraphics.paintBackground(g, width, height, 0xFFFFFF);
	}
	
	
//	Graph<ContextBitmap> images = new Graph<ContextBitmap>();
//	images.addNode(ResourceContext.current().LoadBitmap("mike_left1.jpg"), 0.1, "L", true);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_left2.jpg"), 0.85);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_center.jpg"), 0.0, "C", false);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_right1.jpg"), 0.1, "R", true);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_right2.jpg"), 0.85);
//	images.linkTo("C");
//	images.addNode(ResourceContext.current().LoadBitmap("mike_gl1.jpg"), 0.1, "LG", true);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_gl2.jpg"), 0.85);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_center2.jpg"), 0.0, "C2", false);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_gr1.jpg"), 0.1, "RG", true);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_gr2.jpg"), 0.85);
//	images.linkTo("C2");
//	images.addNode(ResourceContext.current().LoadBitmap("mike_ngl1.jpg"), 0.85, "LNG", true);
//	images.linkTo("C2");
//	images.addNode(ResourceContext.current().LoadBitmap("mike_ngr1.jpg"), 0.85, "RNG", true);
//	images.linkTo("C2");
//	images.addNode(ResourceContext.current().LoadBitmap("mike_tip1.jpg"), 0.1, "T", true);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_tip2.jpg"), 0.1);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_tip3.jpg"), 0.1);
//	images.addNode(ResourceContext.current().LoadBitmap("mike_tip4.jpg"), 0);
	
}
