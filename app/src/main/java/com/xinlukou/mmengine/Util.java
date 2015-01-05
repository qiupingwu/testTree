package com.xinlukou.mmengine;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static SimpleDateFormat formatYMDHM = new SimpleDateFormat("yyyyMMddHHmm");
	public static SimpleDateFormat formatYMD = new SimpleDateFormat("yyyyMMdd");

    public static Date convertYYYYMMDDHHMMToDate(String str) {
    	try {
    		return formatYMDHM.parse(str);
    	} catch(Exception ex) {
    		return null;
    	}
	}

	public static Date convertYYYYMMDDToDate(String str) {
		try {
    		return formatYMD.parse(str);
    	} catch(Exception ex) {
    		return null;
    	}
	}

	public static String convertDateToYYYYMMDDHHMM(Date date) {
		return formatYMDHM.format(date);
	}

	public static String convertDateToYYYYMMDD(Date date) {
		return formatYMD.format(date);
	}
}
