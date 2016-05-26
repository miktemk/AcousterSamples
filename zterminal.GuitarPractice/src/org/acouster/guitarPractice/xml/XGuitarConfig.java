package org.acouster.guitarPractice.xml;

import java.io.IOException;
import java.util.List;

import org.acouster.simplexml.ObjectFactory;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="config")
public class XGuitarConfig {
	
	@ElementList(inline=true, entry="song")
	public List<XGuitarSong> songs;
	
	
	
	
	// desktop tests
//	public static void main(String[] args) throws IOException {
//		
//		XGuitarConfig obj = ObjectFactory.loadXml(XGuitarConfig.class, "C:\\Users\\miktemk\\Desktop\\GuitarTime\\config.xml");
//		System.out.println("obj.len" + obj.songs.size());
//	}
}

