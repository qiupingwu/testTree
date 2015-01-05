package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class RouteTraverseDetail {
    public RouteTraverse routeTraverse;
    public List<TransferLink> transLinkList;

    public RouteTraverseDetail() {
    	routeTraverse = null;
    	transLinkList = null;
    }
    
    public RouteTraverseDetail (RouteTraverse theRouteTraverse, List<TransferLink> theTransLinkList) {
        routeTraverse = new RouteTraverse(theRouteTraverse);
        transLinkList = new ArrayList<TransferLink>(theTransLinkList.size());
        for(TransferLink obj : theTransLinkList) {
        	transLinkList.add(new TransferLink(obj));
        }
    }

    public TransferLink getTransLinkAt(int index) {
    	return transLinkList.get(index);
    }
}
