package org.acouster.platinum.xml;

import org.simpleframework.xml.*;

public class LessonLine {
	@Attribute(required=false)
	private String lang1;
	@Attribute(required=false)
	private String lang2;
	@Attribute(required=false)
	private String image;
	@Attribute(required=false)
	private String someNumber;
	@Element
	private AudioTime audioTime;
	
	public LessonLine(){}

	public String getLang1() {
		return lang1;
	}
	public String getLang2() {
		return lang2;
	}
	public String getImage() {
		return image;
	}
	public String getSomeNumber() {
		return someNumber;
	}
	public AudioTime getAudioTime() {
		return audioTime;
	}
	
}
