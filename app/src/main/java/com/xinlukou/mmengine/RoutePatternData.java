package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class RoutePatternData {
    public List<PairInt> routePattern;
    public RouteData routeData;
    
	public RoutePatternData(List<PairInt> theRoutePattern, RouteData theRouteData) {
		routePattern = new ArrayList<PairInt>(theRoutePattern.size());
		for(PairInt obj : theRoutePattern) {
			routePattern.add(new PairInt(obj));
		}
		routeData = new RouteData(theRouteData);
	}

	public RoutePatternData(RouteBase base) {
		routeData = new RouteData();
		routePattern = new ArrayList<PairInt>();
		Link tempLink = DM.getLink(base.firstLinkID);
		if(!routeData.append(base.firstLinkID)) return;
		int preWayID = tempLink.wayID;
		Transfer tempTrans;
		if(Condition.flag) {
			if(Condition.searchType != Define.SearchType_Time) {
				addPattern(tempLink.wayID, tempLink.fromStationID);
				for(int transNum : base.transferIDList) {
					tempTrans = DM.getTransferFrom(transNum);
					tempLink = DM.getLink(tempTrans.toLinkID);
					if(!routeData.append(tempTrans.id, tempLink.id)) {
						routePattern.clear();
						break;
					}
					if(preWayID != tempLink.wayID) {
						addPattern(tempLink.wayID, tempLink.fromStationID);
						preWayID = tempLink.wayID;
					}
				}
			} else {
				addPattern(tempLink.wayID, tempLink.fromStationID);
				for(int transNum : base.transferIDList) {
					tempTrans = DM.getTransferFrom(transNum);
					tempLink = DM.getLink(tempTrans.toLinkID);
					if(!routeData.append(tempTrans.id, tempLink.id)) {
						routePattern.clear();
						break;
					}
					addPattern(tempLink.wayID, tempLink.fromStationID);
				}
			}
		} else {
			if(Condition.searchType != Define.SearchType_Time) {
				addPattern(tempLink.wayID, tempLink.toStationID);
				for(int transNum : base.transferIDList) {
					tempTrans = DM.getTransferTo(transNum);
					tempLink = DM.getLink(tempTrans.fromLinkID);
					if(!routeData.append(tempTrans.id, tempLink.id)) {
						routePattern.clear();
						break;
					}
					if(preWayID != tempLink.wayID) {
						addPattern(tempLink.wayID, tempLink.toStationID);
						preWayID = tempLink.wayID;
					}
				}
			} else {
				addPattern(tempLink.wayID, tempLink.toStationID);
				for(int transNum : base.transferIDList) {
					tempTrans = DM.getTransferTo(transNum);
					tempLink = DM.getLink(tempTrans.fromLinkID);
					if(!routeData.append(tempTrans.id, tempLink.id)) {
						routePattern.clear();
						break;
					}
					addPattern(tempLink.wayID, tempLink.toStationID);
				}
			}
		}
	}

	public RoutePatternData(RoutePatternData copy) {
		routePattern = new ArrayList<PairInt>(copy.routePattern.size());
		for(PairInt obj : copy.routePattern) {
			routePattern.add(new PairInt(obj));
		}
		routeData = new RouteData(copy.routeData);
	}

	public void addPattern(int value1, int value2) {
		PairInt tempObj = new PairInt(value1, value2);
		routePattern.add(tempObj);
	}

	public PairInt getPatternAt(int index) {
		return routePattern.get(index);
	}

	public int getPatternValue1(int index) {
		return getPatternAt(index).value1;
	}

	public int getPatternValue2(int index) {
		return getPatternAt(index).value2;
	}
}
