package org.acouster.android.kyo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KyoUtils {
	
	public static final String PREFIX_FOLDER = "DCIM_Kyo";

	public static String makeCurrentTimestampedFolderName()
	{
		Date maintenant = new Date();
		SimpleDateFormat formatDateJour = new SimpleDateFormat("yyyy-MM-dd_kk-mm-ss");
		String dateFormatee = formatDateJour.format(maintenant); 
		return PREFIX_FOLDER + "/." + dateFormatee.replace("-", "");
	}

}
