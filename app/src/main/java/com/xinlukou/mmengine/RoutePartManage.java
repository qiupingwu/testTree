package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class RoutePartManage {
    public List<Integer> connectNodeList;
    public List<RBTree> frontPartTreeList;
    public List<RBTree> behindPartTreeList;

    public RoutePartManage() {
    	connectNodeList = new ArrayList<Integer>();
    	frontPartTreeList = new ArrayList<RBTree>();
    	behindPartTreeList = new ArrayList<RBTree>();
    }
    
	public int getConnectNodeAt(int index) {
		return connectNodeList.get(index);
	}

	public RBTree getFrontAt(int index) {
		return frontPartTreeList.get(index);
	}

	public RBTree getBehindAt(int index) {
		return behindPartTreeList.get(index);
	}

	public void insert(int connectNode, RoutePart frontPart, RoutePart behindPart) {
		int index = connectNodeList.indexOf(connectNode);
		if(index == -1) {
			RBTree frontPartTree = new RBTree(12);
			RBTree behindPartTree = new RBTree(12);
			frontPartTree.insertUnique(frontPart);
			behindPartTree.insertUnique(behindPart);
			connectNodeList.add(connectNode);
			frontPartTreeList.add(frontPartTree);
			behindPartTreeList.add(behindPartTree);
		} else {
			getFrontAt(index).insertUnique(frontPart);
			getBehindAt(index).insertUnique(behindPart);
		}
	}

	public boolean insertFront(int connectNode, RoutePart frontPart) {
		boolean result = true;
		int index = connectNodeList.indexOf(connectNode);
		if(index == -1) {
			RBTree frontPartTree = new RBTree(12);
			RBTree behindPartTree = new RBTree(12);
			frontPartTree.insertUnique(frontPart);
			connectNodeList.add(connectNode);
			frontPartTreeList.add(frontPartTree);
			behindPartTreeList.add(behindPartTree);
		} else {
			result = getFrontAt(index).insertUnique(frontPart);
		}
		return result;
	}

	public boolean insertBehind(int connectNode, RoutePart behindPart) {
		boolean result = true;
		int index = connectNodeList.indexOf(connectNode);
		if(index == -1) {
			RBTree frontPartTree = new RBTree(12);
			RBTree behindPartTree = new RBTree(12);
			behindPartTree.insertUnique(behindPart);
			connectNodeList.add(connectNode);
			frontPartTreeList.add(frontPartTree);
			behindPartTreeList.add(behindPartTree);
		} else {
			result = getBehindAt(index).insertUnique(behindPart);
		}
		return result;
	}
}
