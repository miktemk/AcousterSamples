package org.acouster.kyo.xml;

import java.util.List;
import java.util.Vector;

import org.acouster.GameEvent;
import org.acouster.Graph;


import org.simpleframework.xml.ElementList;

public class CameraSchedule
{
	public static final String CUSTOM_MESSAGE_GO = "GO";
	public static final String CUSTOM_MESSAGE_TARGET_TTS = "tts";
	public static final String CUSTOM_MESSAGE_TARGET_CAM = "camera";
	
	@ElementList
	private List<SchedulePicture> pictures;
	
	public CameraSchedule()
	{
		pictures = new Vector<SchedulePicture>();
	}

	public List<SchedulePicture> getPictures() {
		return pictures;
	}

	//================ logic ========================
	
	private Graph<GameEvent> schedule;
	
	public void addNode(SchedulePicture imageNode) {
		pictures.add(imageNode);
	}
	public void compile()
	{
		schedule = new Graph<GameEvent>();
		boolean isFirst = true;
		for (SchedulePicture pic : pictures)
		{
			if (isFirst)
				schedule.addNode(new GameEvent(CUSTOM_MESSAGE_TARGET_TTS, pic.getTtsLine()), 3, CUSTOM_MESSAGE_GO, true);
			else
				schedule.addNode(new GameEvent(CUSTOM_MESSAGE_TARGET_TTS, pic.getTtsLine()), 3);
			schedule.addNode(new GameEvent(CUSTOM_MESSAGE_TARGET_CAM, pic.getId()), 1);
			isFirst = false;
		}
	}
	public Graph<GameEvent> getSchedule() {
		return schedule;
	}
	
}
