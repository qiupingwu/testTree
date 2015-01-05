package com.xinlukou.mmengine;

import java.util.List;

public class RoutePart {
    public int routeID;
    public int startPos;
    public int endPos;
    public int connectNodeID;
    public RoutePatternData routePatternData;

    public RoutePart() {
    	routeID = -1;
    	startPos = -1;
    	endPos = -1;
    	connectNodeID = -1;
    	routePatternData = null;
    }
    
    public RoutePart(int theRouteID, int theStartPos, int theEndPos, int theConnectNodeID, List<PairInt> theRoutePattern, RouteData theRouteData) {
		routeID = theRouteID;
		startPos = theStartPos;
		endPos = theEndPos;
		connectNodeID = theConnectNodeID;
		routePatternData = new RoutePatternData(theRoutePattern, theRouteData);
	}
}
