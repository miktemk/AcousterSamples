package org.acouster.platinum.xml.menu;

import org.simpleframework.xml.Attribute;

public class PlatMainLanguage
{
	@Attribute(required=false)
	public String name;
	@Attribute(required=false)
	public String xmlPath;
	@Attribute(required=false)
	public String xmlReplacement;
	@Attribute(required=false)
	public String externalPath;
	@Attribute(required=false)
	public String imgFilename;
}
