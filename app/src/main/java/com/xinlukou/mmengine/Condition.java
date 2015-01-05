package com.xinlukou.mmengine;

import java.util.Date;

public class Condition {
	public static String paramStr = null;
	public static String city = null;
	public static String searchDate = null;
	public static String searchTime = null;
	public static Date searchDT = null;
	public static int depID = -1;
	public static int arvID = -1;
	public static String versionNO = null;
	public static int searchResultType = -1;
	public static int searchResultFormat = -1;
	public static int requestTerminal = -1;
	public static int resultRouteCount = -1;
	public static int searchType = -1;
	public static int dtType = -1;
	public static boolean flag = false;

	private static Date tempDateTime = null;
	private static int tempSearchType = -1;
	
	private static Date prevPointDate = null;
	private static Date nextPointDate = null;
	private static boolean prevHolidayFlag = false;
	private static boolean curHolidayFlag = false;
	private static boolean nextHolidayFlag = false;
	
	public static Date date2015 = null;

	public static void commitSearchDT(Date theSearchDT) {
		searchDT = theSearchDT;
	}

	public static void commitSearchType(int theSearchType) {
		searchType = theSearchType;
	}

	public static void revertSearchDT() {
		searchDT = tempDateTime;
	}

	public static void revertSearchType() {
		searchType = tempSearchType;
	}

	public static int getStationIDByUNO(String uno) {
		int result = -1;
		for(Station sta : DM.stationList) {
			if(sta.uno.equals(uno)) {
				result = sta.id;
				break;
			}
		}
		return result;
	}

	public static void setSearchID(String pSID) {
		paramStr = pSID.trim().toUpperCase();
		String theCity = paramStr.substring(0, 2);
		String theDate = paramStr.substring(2, 7);
		String theTime = paramStr.substring(7, 11);
		String theDepUNO = theCity + paramStr.substring(11, 16);
		String theArvUNO = theCity + paramStr.substring(16, 21);
		String theVersionNO = paramStr.substring(21, 25);
		for(SrcConfig config : DM.configList) {
			if(config.versionNO.equals(theVersionNO)) {
				city = theCity;
				searchDate = "201" + theDate;
				searchTime = theTime;
				searchDT = Util.convertYYYYMMDDHHMMToDate(searchDate + searchTime);
				depID = getStationIDByUNO(theDepUNO);
				arvID = getStationIDByUNO(theArvUNO);
				versionNO = config.versionNO;
				searchResultType = config.searchResultType;
				searchResultFormat = config.searchResultFormat;
				requestTerminal = config.requestTerminal;
				resultRouteCount = config.resultRouteCount;
				searchType = config.searchType;
				dtType = config.dtType;
				// reset datetime for first-search and last-search
				if (dtType == Define.DTType_First) {
					searchDT = Util.convertYYYYMMDDHHMMToDate(searchDate + "0000");
					int tempTime = DM.getFirstDepTime(depID, searchDate);
					searchDT = new Date(searchDT.getTime() + tempTime * Define.UNIT_MIN);
					String tempSearchDT = Util.convertDateToYYYYMMDDHHMM(searchDT);
					searchDate = tempSearchDT.substring(0, 8);
					searchTime = tempSearchDT.substring(8, 12);
				} else if (dtType == Define.DTType_Last) {
					searchDT = Util.convertYYYYMMDDHHMMToDate(searchDate + "0000");
					int tempTime = DM.getLastArrTime(arvID, searchDate);
					searchDT = new Date(searchDT.getTime() + tempTime * Define.UNIT_MIN);
					String tempSearchDT = Util.convertDateToYYYYMMDDHHMM(searchDT);
					searchDate = tempSearchDT.substring(0, 8);
					searchTime = tempSearchDT.substring(8, 12);
				}
				tempDateTime = searchDT;
				tempSearchType = searchType;
				// cache for isHoliday method
				String prevDateStr;
				String curDateStr;
				String nextDateStr;
				date2015 = Util.convertYYYYMMDDHHMMToDate("201501010000");
				int minutes = (int)(searchDT.getTime() - date2015.getTime()) / Define.UNIT_MIN;
				minutes = minutes % 1440;
				if(minutes < 120) {
					prevDateStr = Util.convertDateToYYYYMMDD(new Date(searchDT.getTime() + (-60 * 60 * 24 * 2 * Define.UNIT_MIN)));
					curDateStr = Util.convertDateToYYYYMMDD(new Date(searchDT.getTime() + (-60 * 60 * 24 * Define.UNIT_MIN)));
					nextDateStr = searchDate;
				} else {
					prevDateStr = Util.convertDateToYYYYMMDD(new Date(searchDT.getTime() + (-60 * 60 * 2 * Define.UNIT_MIN)));
					curDateStr = searchDate;
					nextDateStr = Util.convertDateToYYYYMMDD(new Date(searchDT.getTime() + (60 * 60 * 24 * Define.UNIT_MIN)));
				}
				prevPointDate = Util.convertYYYYMMDDHHMMToDate(curDateStr + "0200");
				nextPointDate = Util.convertYYYYMMDDHHMMToDate(nextDateStr + "0200");
				prevHolidayFlag = DM.holidayList.contains(prevDateStr);
				curHolidayFlag = DM.holidayList.contains(curDateStr);
				nextHolidayFlag = DM.holidayList.contains(nextDateStr);
				break;
			}
		}
		if(dtType == Define.DTType_Dep || dtType == Define.DTType_First) flag = true;
		else if(dtType == Define.DTType_Arr || dtType == Define.DTType_Last) flag = false;
	}

	public static boolean isHoliday(Date dt) {
		if(dt.getTime() > nextPointDate.getTime()) return nextHolidayFlag;
		else if(dt.getTime() < prevPointDate.getTime()) return prevHolidayFlag;
		else return curHolidayFlag;
	}
}
