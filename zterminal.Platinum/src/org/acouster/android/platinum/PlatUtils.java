package org.acouster.android.platinum;

import java.io.File;

import org.acouster.platinum.xml.Lesson;
import org.acouster.platinum.xml.LessonLine;

import android.os.Environment;
import android.text.Html;
import android.text.Spanned;

public class PlatUtils
{
	//TODO: maybe put this into config... maybe string resources
	//public static final String PREFIX_FOLDER = "Platinum/Deutsch";

	public static final String FOLDER_LOCAL_NAME_IMG = "img";
	//public static final String FOLDER_LOCAL_NAME_WAV = "wav";
	public static final String FOLDER_LOCAL_NAME_MP3 = "mp3";
	public static final int WHICH_LANGUAGE_1 = 0;
	public static final int WHICH_LANGUAGE_2 = 1;
	
	protected String externalStoragePath;
	
	public PlatUtils(String externalStoragePath)
	{
		this.externalStoragePath = externalStoragePath;
	}
	
	// ================== SINGLETON ==============================================
	protected static PlatUtils instance;
	public static PlatUtils instance()
	{
		return instance;
	}
	public void makeInstance()
	{
		instance = this;
	}

	// ================== FILE SYSTEM METHODS ==============================================
	public String getPlatinumExternalPath(String filename)
	{
		File fff = new File(Environment.getExternalStorageDirectory(), externalStoragePath + "/" + filename);
		return fff.getPath();
		//return externalStoragePath + "/" + filename;
	}
	public String getPlatinumImagePath(LessonLine line)
	{
		String filename = line.getImage();
		//filename = filename.replace("dg\\", "");
		filename = filename.replace("\\", "/");
		return externalStoragePath + "/" + FOLDER_LOCAL_NAME_IMG + "/" + filename;
	}
	public String getPlatinumAudioPath(Lesson lesson)
	{
		String filename = lesson.getHeader().getWav();
		//return getPlatinumExternalPath(FOLDER_LOCAL_NAME_WAV + "/" + filename);
		filename = filename.replace(".wav", ".mp3");
		return getPlatinumExternalPath(FOLDER_LOCAL_NAME_MP3 + "/" + filename);
	}
	
	// ================== TEXT METHODS =====================================================
	public Spanned makeLessonCurrentTripleLine(Lesson lesson, int curIndex, int whichLanguage)
	{
		LessonLine line1 = getLineSafe(lesson, curIndex-1);
		LessonLine line2 = getLineSafe(lesson, curIndex);
		LessonLine line3 = getLineSafe(lesson, curIndex+1);
		
		String text1 = null;
		String text2 = null;
		String text3 = null;
		
		if (whichLanguage == WHICH_LANGUAGE_1)
		{
			if (line1 != null)
				text1 = fixLessonLineText(line1.getLang1());
			if (line2 != null)
				text2 = fixLessonLineText(line2.getLang1());
			if (line3 != null)
				text3 = fixLessonLineText(line3.getLang1());
		}
		else if (whichLanguage == WHICH_LANGUAGE_2)
		{
			if (line1 != null)
				text1 = fixLessonLineText(line1.getLang2());
			if (line2 != null)
				text2 = fixLessonLineText(line2.getLang2());
			if (line3 != null)
				text3 = fixLessonLineText(line3.getLang2());
		}
		
		return makeStyledHtmlString(text1, text2, text3);
	}
	public LessonLine getLineSafe(Lesson lesson, int index)
	{
		if (index >= 0 && index < lesson.getLines().size())
			return lesson.getLines().get(index);
		return null;
	}
	private String fixLessonLineText(String text)
	{
		text = text.replace("#", "");
		//text = text.replace("- ", "");
		//if (!text.startsWith("- "))
		//	text = "   " + text;
		return text;
	}
	private Spanned makeStyledHtmlString(String line1, String line2, String line3)
	{
		StringBuilder sb = new StringBuilder();
		//if (line1 != null)
		//	sb.append("<font color='#aaaaaa'>" + line1 + "</font><br>");
		if (line2 != null)
			sb.append("<font color='black'>" + line2 + "</font><br>");
		if (line3 != null)
			sb.append("<font color='#aaaaaa'>" + line3 + "</font><br>");
		return Html.fromHtml(sb.toString());
	}
}
