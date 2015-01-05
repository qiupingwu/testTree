package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class RouteBase {
    public int firstLinkID = -1;
    public List<Integer> transferIDList = new ArrayList<Integer>();;

    public RouteBase () {
    }

    public RouteBase(RouteBase routeBase) {
    	firstLinkID = routeBase.firstLinkID;
    	transferIDList.addAll(routeBase.transferIDList);
    }

    public void addTransferID(int transferID) {
    	transferIDList.add(transferID);
    }

    public int getTransferIDAt(int index) {
    	return transferIDList.get(index);
    }
}
