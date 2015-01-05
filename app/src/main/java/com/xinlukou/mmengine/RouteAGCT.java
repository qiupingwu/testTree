package com.xinlukou.mmengine;

public class RouteAGCT {
    public RouteBase routeBase;
    public RoutePatternData routePatternData;

	public RouteAGCT(RouteBase theRouteBase) {
		routeBase = new RouteBase(theRouteBase);
		routePatternData = new RoutePatternData(theRouteBase);
	}

	public RouteAGCT(RouteAGCT theRouteAGCT) {
		routeBase = new RouteBase(theRouteAGCT.routeBase);
		routePatternData = new RoutePatternData(theRouteAGCT.routePatternData);
	}

	public boolean append(int firstLinkID) {
		boolean result = routePatternData.routeData.append(firstLinkID);
		if(result) {
			Link firstLink = DM.getLink(firstLinkID);
			routeBase.firstLinkID = firstLinkID;
			if(Condition.flag) {
				routePatternData.addPattern(firstLink.wayID, firstLink.fromStationID);
			} else {
				routePatternData.addPattern(firstLink.wayID, firstLink.toStationID);
			}
		}
		return result;
	}

	public boolean append(int preBindID, int linkID) {
		boolean result = routePatternData.routeData.append(preBindID, linkID);
		if(result) {
			Link link = DM.getLink(linkID);
			routeBase.addTransferID(preBindID);
			if(Condition.flag) {
				if(Condition.searchType != Define.SearchType_Time) {
					if(routePatternData.getPatternValue1(routePatternData.routePattern.size() - 1) != link.wayID) {
						routePatternData.addPattern(link.wayID, link.fromStationID);
					}
				} else {
					routePatternData.addPattern(link.wayID, link.fromStationID);
				}
			} else {
				if(Condition.searchType != Define.SearchType_Time) {
					if(routePatternData.getPatternValue1(routePatternData.routePattern.size() - 1) != link.wayID) {
						routePatternData.addPattern(link.wayID, link.toStationID);
					}
				} else {
					routePatternData.addPattern(link.wayID, link.toStationID);
				}
			}
		}
		return result;
	}
}
