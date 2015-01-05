package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xinlukou.mmcommon.Helper;

public class DM {
    public static List<City> cityList = null;
    public static City city = null;
    public static List<SrcConfig> configList = null;
    public static List<String> holidayList = null;
    public static List<SrcUNO> unoList = null;
    public static List<SrcFare> fareList = null;
    public static List<Station> stationList = null;
    public static List<Line> lineList = null;
    public static List<Way> wayList = null;
    public static List<Link> linkList = null;
    public static List<Transfer> transferFromList = null;
    public static List<Transfer> transferToList = null;
    public static List<Timetable> weekdayList = null;
    public static List<Timetable> weekendList = null;
    
    public static City getCity(Integer index) {
        return cityList.get(index);
    }
    
    public static Station getStation(Integer index) {
        return stationList.get(index);
    }
    
    public static Line getLine(Integer index) {
        return lineList.get(index);
    }
    
    public static Way getWay(Integer index) {
        return wayList.get(index);
    }
    
    public static Link getLink(Integer index) {
        return linkList.get(index);
    }
    
    public static Transfer getTransferFrom(Integer index) {
        return transferFromList.get(index);
    }
    
    public static Transfer getTransferTo(Integer index) {
        return transferToList.get(index);
    }
    
    public static Timetable getWeekday(Integer index) {
        return weekdayList.get(index);
    }
    
    public static Timetable getWeekend(Integer index) {
        return weekendList.get(index);
    }

	public static void loadCityList() {
		String[] rowArray = Helper.GetFile(null, "mmcity.csv");
		cityList = new ArrayList<City>(rowArray.length);
		for (String str : rowArray) {
			if(Helper.isEmpty(str)) continue;
			cityList.add(new City(str));
		}
	}
	
	public static void loadCity(int cityID) {
		city = getCity(cityID);
		loadConfig();
		loadHoliday();
		loadUNO();
		loadFare();
		loadStation();
		loadLine();
		loadWay();
		loadLink();
		loadTransferFrom();
		loadTransferTo();
		loadWeekday();
		loadWeekend();
	}
	
	public static void unloadCity() {
		  city = null;
		  configList = null;
		  holidayList = null;
		  unoList = null;
		  fareList = null;
		  stationList = null;
		  lineList = null;
		  wayList = null;
		  linkList = null;
		  transferFromList = null;
		  transferToList = null;
		  weekdayList = null;
		  weekendList = null;
	}

    public static String[] getCsv(String fileName) {
    	return Helper.GetFile(city.cityKey, fileName + ".csv");
    }

    private static void loadConfig() {
        if(configList != null && configList.size() > 0) return;
        String[] rowArray = getCsv("config");
        configList = new ArrayList<SrcConfig>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            configList.add(new SrcConfig(str));
        }
    }

    private static void loadHoliday() {
        if(holidayList != null && holidayList.size() > 0) return;
        String[] rowArray = getCsv("holiday");
        holidayList = new ArrayList<String>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            holidayList.add(str);
        }
    }

    private static void loadUNO() {
        if(unoList != null && unoList.size() > 0) return;
        String[] rowArray = getCsv("uno");
        unoList = new ArrayList<SrcUNO>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            unoList.add(new SrcUNO(str));
        }
    }

    private static void loadFare() {
        if(fareList != null && fareList.size() > 0) return;
        String[] rowArray = getCsv("fare");
        fareList = new ArrayList<SrcFare>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            fareList.add(new SrcFare(str));
        }
    }

    private static void loadStation() {
        if(stationList != null && stationList.size() > 0) return;
        String[] rowArray = getCsv("station");
        Integer curIndex = 0;
        stationList = new ArrayList<Station>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            stationList.add(new Station(curIndex, str));
            curIndex++;
        }
    }

    private static void loadLine() {
        if(lineList != null && lineList.size() > 0) return;
        String[] rowArray = getCsv("line");
        Integer curIndex = 0;
        lineList = new ArrayList<Line>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            lineList.add(new Line(curIndex, str));
            curIndex++;
        }
    }

    private static void loadWay() {
        if(wayList != null && wayList.size() > 0) return;
        String[] rowArray = getCsv("way");
        Integer curIndex = 0;
        wayList = new ArrayList<Way>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            wayList.add(new Way(curIndex, str));
            curIndex++;
        }
    }

    private static void loadLink() {
        if(linkList != null && linkList.size() > 0) return;
        String[] rowArray = getCsv("link");
        Integer curIndex = 0;
        linkList = new ArrayList<Link>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            linkList.add(new Link(curIndex, str));
            curIndex++;
        }
    }
    
    private static void loadTransferFrom() {
        if(transferFromList != null && transferFromList.size() > 0) return;
        String[] rowArray = getCsv("transferfrom");
        Integer curIndex = 0;
        transferFromList = new ArrayList<Transfer>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            transferFromList.add(new Transfer(curIndex, str));
            curIndex++;
        }
    }
    
    private static void loadTransferTo() {
        if(transferToList != null && transferToList.size() > 0) return;
        String[] rowArray = getCsv("transferto");
        Integer curIndex = 0;
        transferToList = new ArrayList<Transfer>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            transferToList.add(new Transfer(curIndex, str));
            curIndex++;
        }
    }
    
    private static void loadWeekday() {
        if(weekdayList != null && weekdayList.size() > 0) return;
        String[] rowArray = getCsv("weekday");
        Integer curIndex = 0;
        weekdayList = new ArrayList<Timetable>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            weekdayList.add(new Timetable(curIndex, str));
            curIndex++;
        }
    }
    
    private static void loadWeekend() {
        if(weekendList != null && weekendList.size() > 0) return;
        String[] rowArray = getCsv("weekend");
        Integer curIndex = 0;
        weekendList = new ArrayList<Timetable>(rowArray.length);
        for (String str : rowArray) {
            if(Helper.isEmpty(str)) continue;
            weekendList.add(new Timetable(curIndex, str));
            curIndex++;
        }
    }

    ////Best Timetable
    public static Timetable getDepWeekday(int time, int startID, int endID) {
    	Timetable result = null;
    	if(startID == endID) return getWeekday(startID);
    	else {
    		int middleID = (startID + endID) / 2;
    		Timetable mTimetable;
    		do {
    			mTimetable = getWeekday(middleID);
    			if(time > mTimetable.depTime) startID = middleID;
    			else if(time < mTimetable.depTime) endID = middleID;
    			else break;
    			middleID = (startID + endID) / 2;
    		} while(endID - startID > 1);
    		mTimetable = getWeekday(middleID);
    		if(time == mTimetable.depTime) result = mTimetable;
    		else result = getWeekday(endID);
    	}
    	return result;
    }
    
    public static Timetable getDepWeekend(int time, int startID, int endID) {
    	Timetable result = null;
    	if(startID == endID) return getWeekend(startID);
    	else {
    		int middleID = (startID + endID) / 2;
    		Timetable mTimetable;
    		do {
    			mTimetable = getWeekend(middleID);
    			if(time > mTimetable.depTime) startID = middleID;
    			else if(time < mTimetable.depTime) endID = middleID;
    			else break;
    			middleID = (startID + endID) / 2;
    		} while(endID - startID > 1);
    		mTimetable = getWeekend(middleID);
    		if(time == mTimetable.depTime) result = mTimetable;
    		else result = getWeekend(endID);
    	}
    	return result;
    }
    
    public static Timetable getArrWeekday(int time, int startID, int endID) {
    	Timetable result = null;
    	if(startID == endID) return getWeekday(startID);
    	else {
    		int middleID = (startID + endID) / 2;
    		Timetable mTimetable;
    		do {
    			mTimetable = getWeekday(middleID);
    			if(time > mTimetable.arrTime) startID = middleID;
    			else if(time < mTimetable.arrTime) endID = middleID;
    			else break;
    			middleID = (startID + endID) / 2;
    		} while(endID - startID > 1);
    		mTimetable = getWeekday(middleID);
    		if(time == mTimetable.arrTime) result = mTimetable;
    		else result = getWeekday(endID);
    	}
    	return result;
    }
    
    public static Timetable getArrWeekend(int time, int startID, int endID) {
    	Timetable result = null;
    	if(startID == endID) return getWeekend(startID);
    	else {
    		int middleID = (startID + endID) / 2;
    		Timetable mTimetable;
    		do {
    			mTimetable = getWeekend(middleID);
    			if(time > mTimetable.arrTime) startID = middleID;
    			else if(time < mTimetable.arrTime) endID = middleID;
    			else break;
    			middleID = (startID + endID) / 2;
    		} while(endID - startID > 1);
    		mTimetable = getWeekend(middleID);
    		if(time == mTimetable.arrTime) result = mTimetable;
    		else result = getWeekend(endID);
    	}
    	return result;
    }
    
    public static Timetable getBestTimetableFrom(int preTransferID, int linkID, Date[] curDT) {
    	Timetable result = null;
    	Link link = getLink(linkID);
    	if(preTransferID != -1) {
    		Transfer trans = getTransferFrom(preTransferID);
    		if(trans.transType != Define.TransType_None) curDT[0] = new Date(curDT[0].getTime() + trans.transTime * Define.UNIT_MIN); 
    	}
    	if(curDT[0].getTime() - Condition.searchDT.getTime() > Define.UNIT_MIN * 60 *24) return null;
    	int curTime = (int)(curDT[0].getTime() - Condition.date2015.getTime()) / Define.UNIT_MIN;
    	curTime = curTime % 1440;
    	if(curTime < 120) curTime = curTime + 1440;
    	if(link.startWeekdayID == -1 && link.endWeekdayID == -1 && link.startWeekendID == -1 && link.endWeekendID == -1) {
    		// Walk Link don't have timetable
    		return new Timetable(-1, linkID, curTime, curTime + link.averageTime);
    	}
    	if(link.startWeekdayID == -1 || link.endWeekdayID == -1 || link.startWeekendID == -1 || link.endWeekendID == -1) {
    		if(!Condition.isHoliday(curDT[0]) && link.startWeekdayID == -1) return null;
    		if(Condition.isHoliday(curDT[0]) && link.startWeekendID == -1) return null;
    	}
    	if(!Condition.isHoliday(curDT[0])) {
    		if(curTime < getWeekday(link.startWeekdayID).depTime) {
    			result = getWeekday(link.startWeekdayID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.depTime - curTime) * Define.UNIT_MIN);
    		} else if(curTime <= getWeekday(link.endWeekdayID).depTime) {
    			result = getDepWeekday(curTime, link.startWeekdayID, link.endWeekdayID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.depTime - curTime) * Define.UNIT_MIN);
    		} else {
    			if(!Condition.isHoliday(new Date(curDT[0].getTime() + Define.UNIT_MIN * 60 *24))) result = getWeekday(link.startWeekdayID);
    			else {
    				if(link.startWeekendID == -1) return null;
    				result = getWeekend(link.startWeekendID);
    			}
    			curDT[0] = new Date(curDT[0].getTime() + (result.depTime + 1440 - curTime) * Define.UNIT_MIN);
    		}
    		if(curDT[0].getTime() - Condition.searchDT.getTime() > Define.UNIT_MIN * 60 *24) return null;
    	} else {
    		if(curTime < getWeekend(link.startWeekendID).depTime) {
    			result = getWeekend(link.startWeekendID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.depTime - curTime) * Define.UNIT_MIN);
    		} else if(curTime <= getWeekend(link.endWeekendID).depTime) {
    			result = getDepWeekend(curTime, link.startWeekendID, link.endWeekendID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.depTime - curTime) * Define.UNIT_MIN);
    		} else {
    			if(!Condition.isHoliday(new Date(curDT[0].getTime() + Define.UNIT_MIN * 60 *24))) {
    				if(link.startWeekdayID == -1) return null;
    				result = getWeekday(link.startWeekdayID);
    			} else result = getWeekend(link.startWeekendID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.depTime + 1440 - curTime) * Define.UNIT_MIN);
    		}
    		if(curDT[0].getTime() - Condition.searchDT.getTime() > Define.UNIT_MIN * 60 *24) return null;
    	}
        return result;
    }

    public static Timetable getBestTimetableTo(int preTransferID, int linkID, Date[] curDT) {
    	Timetable result = null;
    	Link link = getLink(linkID);
    	if(preTransferID != -1) {
    		Transfer trans = getTransferTo(preTransferID);
    		if(trans.transType != Define.TransType_None) curDT[0] = new Date(curDT[0].getTime() + trans.transTime * Define.UNIT_MIN * -1); 
    	}
    	if(Condition.searchDT.getTime() - curDT[0].getTime() > Define.UNIT_MIN * 60 *24) return null;
    	int curTime = (int)(curDT[0].getTime() - Condition.date2015.getTime()) / Define.UNIT_MIN;
    	curTime = curTime % 1440;
    	if(curTime < 120) curTime = curTime + 1440;
    	if(link.startWeekdayID == -1 && link.endWeekdayID == -1 && link.startWeekendID == -1 && link.endWeekendID == -1) {
    		// Walk Link don't have timetable
    		return new Timetable(-1, linkID, curTime - link.averageTime, curTime);
    	}
    	if(link.startWeekdayID == -1 || link.endWeekdayID == -1 || link.startWeekendID == -1 || link.endWeekendID == -1) {
    		if(!Condition.isHoliday(curDT[0]) && link.startWeekdayID == -1) return null;
    		if(Condition.isHoliday(curDT[0]) && link.startWeekendID == -1) return null;
    	}
    	if(!Condition.isHoliday(curDT[0])) {
    		if(curTime >= getWeekday(link.endWeekdayID).arrTime) {
    			result = getWeekday(link.endWeekdayID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.arrTime - curTime) * Define.UNIT_MIN);
    		} else if(curTime >= getWeekday(link.startWeekdayID).arrTime) {
    			result = getArrWeekday(curTime, link.startWeekdayID, link.endWeekdayID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.arrTime - curTime) * Define.UNIT_MIN);
    		} else {
    			if(!Condition.isHoliday(new Date(curDT[0].getTime() - Define.UNIT_MIN * 60 *24))) result = getWeekday(link.endWeekdayID);
    			else {
    				if(link.endWeekendID == -1) return null;
    				result = getWeekend(link.endWeekendID);
    			}
    			curDT[0] = new Date(curDT[0].getTime() + (result.arrTime - 1440 - curTime) * Define.UNIT_MIN);
    		}
    		if(Condition.searchDT.getTime() - curDT[0].getTime() > Define.UNIT_MIN * 60 *24) return null;
    	} else {
    		if(curTime >= getWeekend(link.endWeekendID).arrTime) {
    			result = getWeekend(link.endWeekendID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.arrTime - curTime) * Define.UNIT_MIN);
    		} else if(curTime >= getWeekend(link.startWeekendID).arrTime) {
    			result = getArrWeekend(curTime, link.startWeekendID, link.endWeekendID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.arrTime - curTime) * Define.UNIT_MIN);
    		} else {
    			if(!Condition.isHoliday(new Date(curDT[0].getTime() - Define.UNIT_MIN * 60 *24))) {
    				if(link.endWeekdayID == -1) return null;
    				result = getWeekday(link.endWeekdayID);
    			} else result = getWeekend(link.endWeekendID);
    			curDT[0] = new Date(curDT[0].getTime() + (result.arrTime - 1440 - curTime) * Define.UNIT_MIN);
    		}
    		if(Condition.searchDT.getTime() - curDT[0].getTime() > Define.UNIT_MIN * 60 *24) return null;
    	}
        return result;
    }
    
    ////Helper
    public static List<TransferLink> getDepTransferLinks(int stationID) {
    	List<TransferLink> result = new ArrayList<TransferLink>();
    	for(Link link : linkList) {
    		if(link.fromStationID == stationID) {
    			result.add(new TransferLink(-1, link.id));
    		}
    	}
    	return result;
    }
    
    public static List<TransferLink> getArrTransferLinks(int stationID) {
    	List<TransferLink> result = new ArrayList<TransferLink>();
    	for(Link link : linkList) {
    		if(link.toStationID == stationID) {
    			result.add(new TransferLink(-1, link.id));
    		}
    	}
    	return result;
    }
    
    public static List<TransferLink> getConnectFromTransferLinks(int linkID) {
    	List<TransferLink> result = new ArrayList<TransferLink>();
    	Link curLink = getLink(linkID);
    	if(curLink.startTransferFromID != -1 && curLink.endTransferFromID != -1) {
    		for(int i = curLink.startTransferFromID; i <= curLink.endTransferFromID; i++) {
    			Transfer nextTrans = getTransferFrom(i);
    			Link nextLink = getLink(nextTrans.toLinkID);
    			if(curLink.fromStationID == nextLink.toStationID) continue;
    			else result.add(new TransferLink(nextTrans.id, nextLink.id));
    		}
    	}
    	return result;
    }
    
    public static List<TransferLink> getConnectToTransferLinks(int linkID) {
    	List<TransferLink> result = new ArrayList<TransferLink>();
    	Link curLink = getLink(linkID);
    	if(curLink.startTransferToID != -1 && curLink.endTransferToID != -1) {
    		for(int i = curLink.startTransferToID; i <= curLink.endTransferToID; i++) {
    			Transfer nextTrans = getTransferTo(i);
    			Link nextLink = getLink(nextTrans.fromLinkID);
    			if(curLink.toStationID == nextLink.fromStationID) continue;
    			else result.add(new TransferLink(nextTrans.id, nextLink.id));
    		}
    	}
    	return result;
    }
    
    public static int getFirstTime(Link link, boolean isHoliday) {
    	int result = -1;
    	if(getWay(link.wayID).waitTime == 0) {
    		List<TransferLink> transferLinkList = getConnectFromTransferLinks(link.id);
    		for(int i = 0; i < transferLinkList.size(); i++) {
    			TransferLink transLink = transferLinkList.get(i);
    			Link curLink = getLink(transLink.linkID);
    			Transfer curTransfer = getTransferFrom(transLink.preTransferID);
    			int tempTime = -1;
    			Timetable tempTimetable = null;
    			if(isHoliday) tempTimetable = getWeekend(curLink.startWeekendID);
    			else tempTimetable = getWeekday(curLink.startWeekdayID);
    			tempTime = tempTimetable.depTime - curTransfer.transTime - link.averageTime;
    			if(result == -1 || result > tempTime) result = tempTime;
    		}
    	} else {
    		if(isHoliday) {
    			if(link.startWeekendID != -1) {
    				result = getWeekend(link.startWeekendID).depTime;
    			}
    		} else {
    			if(link.startWeekdayID != -1) {
    				result = getWeekday(link.startWeekdayID).depTime;
    			}
    		}
    	}
    	return result;
    }
    
    public static int getLastTime(Link link, boolean isHoliday) {
    	int result = -1;
    	if(getWay(link.wayID).waitTime == 0) {
    		List<TransferLink> transferLinkList = getConnectToTransferLinks(link.id);
    		for(int i = 0; i < transferLinkList.size(); i++) {
    			TransferLink transLink = transferLinkList.get(i);
    			Link curLink = getLink(transLink.linkID);
    			Transfer curTransfer = getTransferTo(transLink.preTransferID);
    			int tempTime = -1;
    			Timetable tempTimetable = null;
    			if(isHoliday) tempTimetable = getWeekend(curLink.endWeekendID);
    			else tempTimetable = getWeekday(curLink.endWeekdayID);
    			tempTime = tempTimetable.arrTime + curTransfer.transTime + link.averageTime;
    			if(result == -1 || result > tempTime) result = tempTime;
    		}
    	} else {
    		if(isHoliday) {
    			if(link.endWeekendID != -1) {
    				result = getWeekend(link.endWeekendID).arrTime;
    			}
    		} else {
    			if(link.endWeekdayID != -1) {
    				result = getWeekday(link.endWeekdayID).arrTime;
    			}
    		}
    	}
    	return result;
    }
    
    public static int getFirstDepTime(int stationID, String date) {
    	int result = -1;
    	boolean holidayFlag = holidayList.contains(date);
    	for(Link link : linkList) {
    		if(link.fromStationID == stationID) {
    			int time = getFirstTime(link, holidayFlag);
    			if(time == -1) continue;
    			if(result == -1 || result > time) result = time;
    		}
    	}
    	return result;
    }
    
    public static int getLastArrTime(int stationID, String date) {
    	int result = -1;
    	boolean holidayFlag = holidayList.contains(date);
    	for(Link link : linkList) {
    		if(link.toStationID == stationID) {
    			int time = getLastTime(link, holidayFlag);
    			if(time == -1) continue;
    			if(result == -1 || result < time) result = time;
    		}
    	}
    	return result;
    }
}
