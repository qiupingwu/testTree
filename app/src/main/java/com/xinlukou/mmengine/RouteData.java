package com.xinlukou.mmengine;

import java.util.Date;

public class RouteData {
    public int driveTime = 0;
    public int transTime = 0;
    public int distance = 0;
    public int transCount = 0;
    public Date depDateTime;
    public Date arvDateTime;

    public RouteData () {
    	driveTime = 0;
    	transTime = 0;
    	distance = 0;
    	transCount = 0;
    	depDateTime = Condition.searchDT;
    	arvDateTime = Condition.searchDT;
    }

    public RouteData(RouteData routeData) {
        if (routeData == null) return;
        driveTime = routeData.driveTime;
        transTime = routeData.transTime;
        distance = routeData.distance;
        transCount = routeData.transCount;
        depDateTime = routeData.depDateTime;
        arvDateTime = routeData.arvDateTime;
    }

    private boolean appendDepAverage(int firstLinkID) {
    	Link link = DM.getLink(firstLinkID);
    	Way way = DM.getWay(link.wayID);
    	transTime = way.waitTime;
		driveTime = link.averageTime;
		distance = link.distance;
		return true;
    }

    private boolean appendArrAverage(int firstLinkID) {
    	Link link = DM.getLink(firstLinkID);
    	Way way = DM.getWay(link.wayID);
    	transTime = way.waitTime;
		driveTime = link.averageTime;
		distance = link.distance;
		return true;
    }

    private boolean appendDepAverage(int preTransferID, int linkID) {
    	Link link = DM.getLink(linkID);
    	Transfer trans = DM.getTransferFrom(preTransferID);
    	Way way = DM.getWay(link.wayID);
    	driveTime += link.averageTime;
    	distance += link.distance;
    	if(trans.transType != Define.TransType_None) {
    		transCount++;
    		transTime += trans.transTime + way.waitTime;
    		distance += trans.transDistance;
    	}
		return true;
    }

    private boolean appendArrAverage(int preTransferID, int linkID) {
    	Link link = DM.getLink(linkID);
    	Transfer trans = DM.getTransferTo(preTransferID);
    	Way way = DM.getWay(link.wayID);
    	driveTime += link.averageTime;
    	distance += link.distance;
    	if(trans.transType != Define.TransType_None) {
    		transCount++;
    		transTime += trans.transTime + way.waitTime;
    		distance += trans.transDistance;
    	}
		return true;
    }

    private boolean appendDeparture(int firstLinkID) {
    	boolean result = true;
    	Date[] curDTArray = new Date[1];
    	curDTArray[0] = depDateTime;
    	Timetable bestTimetable = DM.getBestTimetableFrom(-1, firstLinkID, curDTArray);
    	Date curDT = curDTArray[0];
    	if(bestTimetable == null) {
    		result = false;
    	} else {
			transTime = (int)(curDT.getTime() - depDateTime.getTime()) / Define.UNIT_MIN;
			driveTime = bestTimetable.arrTime - bestTimetable.depTime;
			distance = DM.getLink(firstLinkID).distance;
			depDateTime = curDT;
			arvDateTime = new Date(curDT.getTime() + driveTime * Define.UNIT_MIN);
    	}
    	return result;
    }

    private boolean appendDeparture(int preTransferID, int linkID) {
    	boolean result = true;
    	Transfer preTransfer = DM.getTransferFrom(preTransferID);
    	Timetable bestTimetable;
    	Date[] curDTArray = new Date[1];
    	curDTArray[0] = arvDateTime;
    	if(preTransfer.transType == Define.TransType_None) {
        	bestTimetable = DM.getBestTimetableFrom(-1, linkID, curDTArray);
    	} else {
    		bestTimetable = DM.getBestTimetableFrom(preTransferID, linkID, curDTArray);
    	}
    	Date curDT = curDTArray[0];
    	if(bestTimetable == null) {
    		result = false;
    	} else {
			transTime += (curDT.getTime() - arvDateTime.getTime()) / Define.UNIT_MIN;
			driveTime += bestTimetable.arrTime - bestTimetable.depTime;
			distance += DM.getLink(linkID).distance;
			if(preTransfer.transType != Define.TransType_None) {
				transCount++;
				distance += preTransfer.transDistance;
			}
			arvDateTime = new Date(curDT.getTime() + (bestTimetable.arrTime - bestTimetable.depTime) * Define.UNIT_MIN);
    	}
    	return result;
    }

    private boolean appendArrival(int firstLinkID) {
    	boolean result = true;
    	Date[] curDTArray = new Date[1];
    	curDTArray[0] = arvDateTime;
    	Timetable bestTimetable = DM.getBestTimetableTo(-1, firstLinkID, curDTArray);
    	Date curDT = curDTArray[0];
    	if(bestTimetable == null) {
    		result = false;
    	} else {
			transTime = (int)(arvDateTime.getTime() - curDT.getTime()) / Define.UNIT_MIN;
			driveTime = bestTimetable.arrTime - bestTimetable.depTime;
			distance = DM.getLink(firstLinkID).distance;
			depDateTime = new Date(curDT.getTime() + driveTime * Define.UNIT_MIN * -1);
			arvDateTime = curDT;
    	}
    	return result;
    }

    private boolean appendArrival(int preTransferID, int linkID) {
    	boolean result = true;
    	Transfer preTransfer = DM.getTransferTo(preTransferID);
    	Timetable bestTimetable;
    	Date[] curDTArray = new Date[1];
    	curDTArray[0] = depDateTime;
    	if(preTransfer.transType == Define.TransType_None) {
        	bestTimetable = DM.getBestTimetableTo(-1, linkID, curDTArray);
    	} else {
    		bestTimetable = DM.getBestTimetableTo(preTransferID, linkID, curDTArray);
    	}
    	Date curDT = curDTArray[0];
    	if(bestTimetable == null) {
    		result = false;
    	} else {
			transTime += (depDateTime.getTime() - curDT.getTime()) / Define.UNIT_MIN;
			driveTime += bestTimetable.arrTime - bestTimetable.depTime;
			distance += DM.getLink(linkID).distance;
			if(preTransfer.transType != Define.TransType_None) {
				transCount++;
				distance += preTransfer.transDistance;
			}
			depDateTime = new Date(curDT.getTime() + (bestTimetable.arrTime - bestTimetable.depTime) * Define.UNIT_MIN * -1);
    	}
    	return result;
    }

    public boolean removeDepAverage(int linkID, int nextTransferID) {
    	Link link = DM.getLink(linkID);
    	Way way = DM.getWay(link.wayID);
    	Transfer nextTrans = DM.getTransferFrom(nextTransferID);
    	driveTime -= link.averageTime;
    	distance -= link.distance;
    	if(nextTrans.transType != Define.TransType_None) {
    		transCount -= 1;
    		transTime -= (nextTrans.transTime + way.waitTime);
    		distance -= nextTrans.transDistance;
    	}
		return true;
    }

    private boolean removeArrAverage(int linkID, int nextTransferID) {
    	Link link = DM.getLink(linkID);
    	Way way = DM.getWay(link.wayID);
    	Transfer nextTrans = DM.getTransferTo(nextTransferID);
    	driveTime -= link.averageTime;
    	distance -= link.distance;
    	if(nextTrans.transType != Define.TransType_None) {
    		transCount -= 1;
    		transTime -= (nextTrans.transTime + way.waitTime);
    		distance -= nextTrans.transDistance;
    	}
		return true;
    }

    private boolean removeDeparture(int linkID, int nextTransferID) {
    	boolean result = true;
    	Transfer nextTrans = DM.getTransferFrom(nextTransferID);
    	Date[] curDTArray = new Date[1];
    	curDTArray[0] = depDateTime;
    	Timetable bestTimetable = DM.getBestTimetableFrom(-1, linkID, curDTArray);
    	Date curDT = curDTArray[0];
    	if(bestTimetable == null) {
    		result = false;
    	} else {
			driveTime -= (bestTimetable.arrTime - bestTimetable.depTime);
			distance -= DM.getLink(linkID).distance;
			transTime -= (curDT.getTime() - depDateTime.getTime()) / Define.UNIT_MIN;
			if(nextTrans.transType == Define.TransType_None) {
				depDateTime = new Date(curDT.getTime() + (bestTimetable.arrTime - bestTimetable.depTime) * Define.UNIT_MIN);
			} else {
				transCount--;
				transTime -= nextTrans.transTime;
				distance -= nextTrans.transDistance;
				depDateTime = new Date(curDT.getTime() + (bestTimetable.arrTime - bestTimetable.depTime + nextTrans.transTime) * Define.UNIT_MIN);
			}
    	}
    	return result;
    }

    private boolean removeArrvial(int linkID, int nextTransferID) {
    	boolean result = true;
    	Transfer nextTrans = DM.getTransferTo(nextTransferID);
    	Date[] curDTArray = new Date[1];
    	curDTArray[0] = arvDateTime;
    	Timetable bestTimetable = DM.getBestTimetableTo(-1, linkID, curDTArray);
    	Date curDT = curDTArray[0];
    	if(bestTimetable == null) {
    		result = false;
    	} else {
			driveTime -= (bestTimetable.arrTime - bestTimetable.depTime);
			distance -= DM.getLink(linkID).distance;
			transTime -= (arvDateTime.getTime() - curDT.getTime()) / Define.UNIT_MIN;
			if(nextTrans.transType == Define.TransType_None) {
				arvDateTime = new Date(curDT.getTime() + (bestTimetable.arrTime - bestTimetable.depTime) * Define.UNIT_MIN * -1);
			} else {
				transCount--;
				transTime -= nextTrans.transTime;
				distance -= nextTrans.transDistance;
				arvDateTime = new Date(curDT.getTime() + (bestTimetable.arrTime - bestTimetable.depTime + nextTrans.transTime) * Define.UNIT_MIN * -1);
			}
    	}
    	return result;
    }

    public void plus(RouteData routeData) {
    	driveTime += routeData.driveTime;
    	transTime += routeData.transTime;
    	transCount += routeData.transCount;
    	distance += routeData.distance;
    	if(Condition.flag) {
    		if(Condition.searchType == Define.SearchType_Time) {
    			arvDateTime = new Date(arvDateTime.getTime() + (routeData.driveTime + routeData.transTime) * Define.UNIT_MIN);
    		}
    	} else {
    		if(Condition.searchType == Define.SearchType_Time) {
    			depDateTime = new Date(depDateTime.getTime() + (routeData.driveTime + routeData.transTime) * Define.UNIT_MIN * -1);
    		}
    	}
    }

    public void minus(RouteData routeData) {
    	driveTime -= routeData.driveTime;
    	transTime -= routeData.transTime;
    	transCount -= routeData.transCount;
    	distance -= routeData.distance;
    	if(Condition.flag) {
    		if(Condition.searchType == Define.SearchType_Time) {
    			arvDateTime = new Date(arvDateTime.getTime() + (routeData.driveTime + routeData.transTime) * Define.UNIT_MIN * -1);
    		}
    	} else {
    		if(Condition.searchType == Define.SearchType_Time) {
    			depDateTime = new Date(depDateTime.getTime() + (routeData.driveTime + routeData.transTime) * Define.UNIT_MIN);
    		}
    	}
    }

    public boolean append(int firstLinkID) {
    	boolean result = false;
    	if(firstLinkID != -1) {
    		if(Condition.flag) {
    			if(Condition.searchType == Define.SearchType_Time) {
    				result = appendDeparture(firstLinkID);
    			} else {
    				result = appendDepAverage(firstLinkID);
    			}
    		} else {
    			if(Condition.searchType == Define.SearchType_Time) {
    				result = appendArrival(firstLinkID);
    			} else {
    				result = appendArrAverage(firstLinkID);
    			}
    		}
    	}
    	return result;
    }

    public boolean append(int preTransferID, int linkID) {
    	boolean result = false;
    	if(preTransferID != -1 && linkID != -1) {
    		if(Condition.flag) {
    			if(Condition.searchType == Define.SearchType_Time) {
    				result = appendDeparture(preTransferID, linkID);
    			} else {
    				result = appendDepAverage(preTransferID, linkID);
    			}
    		} else {
    			if(Condition.searchType == Define.SearchType_Time) {
    				result = appendArrival(preTransferID, linkID);
    			} else {
    				result = appendArrAverage(preTransferID, linkID);
    			}
    		}
    	}
    	return result;
    }

    public boolean remove(int linkID, int nextTransferID) {
    	boolean result = false;
    	if(linkID != -1 && nextTransferID != -1) {
    		if(Condition.flag) {
    			if(Condition.searchType == Define.SearchType_Time) {
    				result = removeDeparture(linkID, nextTransferID);
    			} else {
    				result = removeDepAverage(linkID, nextTransferID);
    			}
    		} else {
    			if(Condition.searchType == Define.SearchType_Time) {
    				result = removeArrvial(linkID, nextTransferID);
    			} else {
    				result = removeArrAverage(linkID, nextTransferID);
    			}
    		}
    	}
    	return result;
    }
}
