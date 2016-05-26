package org.acouster.platinum.xml.menu;

import java.util.List;
import java.util.Vector;

import org.acouster.IFunc;
import org.acouster.util.ListUtils;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="lessons")
public class Lessons
{
	@ElementList(inline=true, entry="category")
	public List<LessonCategory> categories;
	
	public Lessons()
	{
		categories = new Vector<LessonCategory>();
	}

	public LessonCategory getCategory(final String categoryName)
	{
		return ListUtils.firstOrDefault(categories, new IFunc<LessonCategory, Boolean>() {
			@Override
			public Boolean lambda(LessonCategory c) {
				return c.name.equals(categoryName);
			}
		});
	}
}
