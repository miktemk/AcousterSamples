package org.acouster.kyo.xml;

import org.simpleframework.xml.Element;

import org.simpleframework.xml.Root;

import org.acouster.IFunc;
import org.acouster.context.ContextBitmap;
import org.acouster.data.GraphImage.ImageGraph;
import org.acouster.data.GraphLogic.FsmGraph;
import org.acouster.xml.GraphImage.XmlImageGraph;
import org.acouster.xml.GraphLogic.XmlFsmGraph;


@Root
public class Avatar
{
	@Element
	private XmlFsmGraph logic;	
	@Element
	private XmlImageGraph visuals;
	@Element
	private CameraSchedule schedule;
	
	public Avatar()
	{
		logic = new XmlFsmGraph();
		visuals = new XmlImageGraph();
	}

	// TODO: we really wont be needing this in the future
	// setters for asset loader
	public void setLogic(XmlFsmGraph logic) {
		this.logic = logic;
	}
	public void setVisuals(XmlImageGraph visuals) {
		this.visuals = visuals;
	}
	public void setSchedule(CameraSchedule schedule) {
		this.schedule = schedule;
	}
	
	//================ logic ========================
	
	public void compile(IFunc<String, ContextBitmap> id2image)
	{
		logic.compile();
		visuals.compile(id2image);
		schedule.compile();
	}
	
	public FsmGraph getLogic() {
		return logic;
	}
	public ImageGraph getVisuals() {
		return visuals;
	}
	public CameraSchedule getSchedule() {
		return schedule;
	}
}
