package org.acouster.platinum.xml;

import java.util.List;
import java.util.Vector;

import org.simpleframework.xml.*;

@Root
public class Lesson {

	@Element(name="header")
	private LessonHeader header;
	@ElementList(name="lines", entry="line")
	private List<LessonLine> lines;
	
	public Lesson()
	{
		lines = new Vector<LessonLine>();
	}
	
	public LessonHeader getHeader() {
		return header;
	}
	public List<LessonLine> getLines() {
		return lines;
	}
}