package org.acouster.lingorepeater;

import java.io.File;

import android.os.Environment;

public class SomeUtils
{
	private static final String ExternalStoragePath = "Acouster/LingoRepeater";

	public File getMyExternalFile(String filename)
	{
		File fff = new File(Environment.getExternalStorageDirectory(), ExternalStoragePath + "/" + filename);
		return fff;
	}
}
