package com.xinlukou.mmcommon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Helper {
	
	public static Boolean isEmpty(String str) {
		if(str == null || str.length() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String ROOT_FOLDER = "C:\\Users\\10047258\\Desktop\\masaru_hori\\_work\\temp\\data\\";
	
	public static String[] GetFile(String folderName, String filename) {
		List<String> result = new ArrayList<String>();
		try {
			String fullName = isEmpty(folderName) ? (ROOT_FOLDER + filename) : (ROOT_FOLDER + folderName + "\\" + filename);
			File f = new File(fullName);
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			BufferedReader br = new BufferedReader(isr);
			String str;
			while((str = br.readLine()) != null) {
				result.add(str);
			}
			br.close();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return (String[])result.toArray(new String[0]);
	}
}
