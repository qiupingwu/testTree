package com.xinlukou.mmengine;

public class RouteAdjust {
    public RouteData routeData;
    public RouteBase routeBase;

    public RouteAdjust() {
    	routeData = null;
    	routeBase = null;
    }
    
	public RouteAdjust(RouteData theRouteData, RouteBase theRouteBase) {
		routeData = new RouteData(theRouteData);
		routeBase = new RouteBase(theRouteBase);
	}

	public RouteAdjust(RouteAdjust theRouteAdjust) {
		routeData = new RouteData(theRouteAdjust.routeData);
		routeBase = new RouteBase(theRouteAdjust.routeBase);
	}
}
