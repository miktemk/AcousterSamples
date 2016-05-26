package org.acouster.platinum.xml.menu;

import org.simpleframework.xml.Attribute;

public class LessonFile
{
	@Attribute(required=false)
	private String filename;
	@Attribute(required=false)
	private String uiTitle;
	
	public LessonFile() {
		super();
	}

	public LessonFile(String filename, String uiTitle) {
		super();
		this.filename = filename;
		this.uiTitle = uiTitle;
	}

	public String getFilename() {
		return filename;
	}

	public String getUiTitle() {
		return uiTitle;
	}
	
	@Override
	public String toString()
	{
		return uiTitle;	
	}
}
