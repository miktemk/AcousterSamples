package org.acouster.platinum.xml.menu;

import java.util.List;
import java.util.Vector;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class LessonCategory
{
	@Attribute
	public String name;
	@ElementList(inline=true, entry="lesson")
	public List<LessonFile> lessons;
	
	public LessonCategory()
	{
		lessons = new Vector<LessonFile>();
	}
	
	@Override
	public String toString()
	{
		return name;	
	}
}
