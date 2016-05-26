package org.acouster.guitarPractice.xml;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class XGuitarSong
{
	@Attribute
	public String name, mp3;
	@ElementList(inline=true, entry="image")
	public List<XGuitarImage> images;
}
