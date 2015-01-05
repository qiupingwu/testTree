package com.xinlukou.mmengine;

public class RouteTraverseInfo {
    public RouteTraverse routeTraverse;
    public int theIndex;

    public RouteTraverseInfo() {
    	routeTraverse = new RouteTraverse();
    	theIndex = -1;
    }
    
    public RouteTraverseInfo(RouteTraverse theRouteTraverse, int index) {
        routeTraverse = new RouteTraverse(theRouteTraverse);
        theIndex = index;
    }

    public RouteTraverseInfo (RouteTraverseInfo routeTraverseInfo) {
    	routeTraverse = new RouteTraverse(routeTraverseInfo.routeTraverse);
    	theIndex = routeTraverseInfo.theIndex;
    }
}
