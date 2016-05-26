package org.acouster.lingorepeater.xml;

import java.util.List;

import org.acouster.xml.common.XmlValueString;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="lesson")
public class PhraseList
{
	@ElementList(inline=true, entry="phrase")
	public List<XmlValueString> phrases;
}
