package org.acouster.platinum.xml.menu;

import java.util.List;
import java.util.Vector;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="languages")
public class PlatMain
{
	@ElementList(inline=true, entry="language")
	public List<PlatMainLanguage> languages;

	public PlatMain()
	{
		languages = new Vector<PlatMainLanguage>();
	}
	
	public PlatMainLanguage[] languages_toArray() {
		return languages.toArray(new PlatMainLanguage[languages.size()]);
	}
}
