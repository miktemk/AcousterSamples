package org.acouster.platinum.xml;

import org.simpleframework.xml.Attribute;

public class AudioTime {
	
	public static final double PLATINUM_OOPS_FACTOR = 1.004;
	
	@Attribute(required=false)
	private long in;
	@Attribute(required=false)
	private long out;
	
	public AudioTime() {}

	public long getIn() {
		return (long)(PLATINUM_OOPS_FACTOR * in);
	}
	public long getOut() {
		return (long)(PLATINUM_OOPS_FACTOR * out);
	}
}
