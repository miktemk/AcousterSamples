package org.acouster.kyo.xml;

import org.simpleframework.xml.Attribute;

public class SchedulePicture
{
	@Attribute
	private String id;
	@Attribute(required=false)
	private String ttsLine;
	
	public SchedulePicture() {
		this("", "");
	}
	public SchedulePicture(String id, String ttsLine) {
		super();
		this.id = id;
		this.ttsLine = ttsLine;
	}
	public String getId() {
		return id;
	}
	public String getTtsLine() {
		return ttsLine;
	}
	
	
}
