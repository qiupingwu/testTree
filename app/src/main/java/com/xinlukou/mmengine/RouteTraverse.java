package com.xinlukou.mmengine;

public class RouteTraverse {
    public RouteData routeData;
    public TransferLink transLink;

    public RouteTraverse() {
    	routeData = new RouteData();
    	transLink = null;
    }

    public RouteTraverse(RouteTraverse routeTraverse) {
    	routeData = new RouteData(routeTraverse.routeData);
    	transLink = new TransferLink(routeTraverse.transLink);
    }

    public boolean append(TransferLink theTransLink) {
    	transLink = new TransferLink(theTransLink);
    	if(theTransLink.preTransferID == -1) {
    		return routeData.append(theTransLink.linkID);
    	} else {
			return routeData.append(theTransLink.preTransferID, theTransLink.linkID);
    	}
    }
}
