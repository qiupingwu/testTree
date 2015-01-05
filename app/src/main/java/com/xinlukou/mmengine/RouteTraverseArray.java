package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class RouteTraverseArray {
    public int topIndex;
    public List<RouteTraverseInfo> routeTraverseInfoList;
    public List<Integer> disabledList;
    
    public RouteTraverseArray() {
    	topIndex = -1;
    	routeTraverseInfoList = new ArrayList<RouteTraverseInfo>();
    	disabledList = new ArrayList<Integer>();
    }

	private int compareObj2Top(RouteTraverse traverse) {
		RouteTraverse topObj = getTop().routeTraverse;
		return Compare.compareRouteTraverse(traverse, topObj);
	}

	private int compareIndex2Top(int index) {
		RouteTraverseInfo topObj = getTop();
		RouteTraverseInfo indexObj = getAt(index);
		return Compare.compareRouteTraverseInfo(indexObj, topObj);
	}

	public RouteTraverseInfo getTop() {
		return getAt(topIndex);
	}

	public RouteTraverseInfo getAt(int index) {
		return routeTraverseInfoList.get(index);
	}

	public boolean isEnable(int index) {
		return !disabledList.contains(index);
	}

	public void enable(int index) {
		if(disabledList.contains(index)) {
			disabledList.remove(index);
			if(topIndex == -1 || compareIndex2Top(index) == -1) {
				topIndex = index;
			}
		}
	}

	public void disable(int index) {
		if(disabledList.contains(index)) return;
		disabledList.add(index);
		if(routeTraverseInfoList.size() == disabledList.size()) {
			topIndex = -1;
		} else if(index == topIndex) {
			topIndex = -1;
			for(int i = 0; i < routeTraverseInfoList.size(); i++) {
				if(!disabledList.contains(i)) {
					if(topIndex == -1 || compareIndex2Top(i) == -1) {
						topIndex = i;
					}
				}
			}
		}
	}

	public int add(RouteTraverse routeTraverse, int preIndex) {
		RouteTraverseInfo tempObj = new RouteTraverseInfo(routeTraverse, preIndex);
		routeTraverseInfoList.add(tempObj);
		if(topIndex == -1 || compareObj2Top(routeTraverse) == -1) {
			topIndex = routeTraverseInfoList.size() - 1;
		}
		return routeTraverseInfoList.size() - 1;
	}
}
