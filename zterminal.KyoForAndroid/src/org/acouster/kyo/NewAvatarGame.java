package org.acouster.kyo;

import java.io.IOException;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.GameEvent;
import org.acouster.Graph;
import org.acouster.GraphTicker;
import org.acouster.IFunc;
import org.acouster.Graph.OnAdvanceListener;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.android.MyAndroidResourceContext;
import org.acouster.kyo.xml.Avatar;
import org.acouster.kyo.xml.CameraSchedule;

import android.util.Log;


public class NewAvatarGame extends Game implements OnAdvanceListener<GameEvent> {

	protected GraphTicker<GameEvent> ticker;
	protected Graph<GameEvent> schedule;
	
	public NewAvatarGame(GameContext gameContext, String filename, IFunc<String, ContextBitmap> id2image) {
		super(gameContext);
		
		Avatar avatar;
		try {
			avatar = MyAndroidResourceContext.myCurrent().LoadAndCompileAvatar(filename, id2image);
			schedule = avatar.getSchedule().getSchedule();
			schedule.setOnAdvanceListener(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TODO: do we have a null ponter check??
		ticker = new GraphTicker<GameEvent>(schedule);
		
		//while (true)
		//	ticker.increment();
	}

	@Override
	public void handleCustomMessage(GameEvent message) {
		if (message.getBody() == CameraSchedule.CUSTOM_MESSAGE_GO)
			schedule.gotoNode(CameraSchedule.CUSTOM_MESSAGE_GO);
	}
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
		ticker.increment();
	}

	@Override
	public void onAdvance(GameEvent message) {
		gameContext.handleMessage(message);
	}

}
