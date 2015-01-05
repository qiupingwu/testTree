package com.xinlukou.mmengine;

public class FixRBTree {
	public int maxCount;
	RBTree rbTree;
	
	public int count() {
		return rbTree.size;
	}
	
	public Object getMax() {
		return rbTree.getMax();
	}
	
	public Object getMin() {
		return rbTree.getMin();
	}
	
	public Object find(Object data) {
		return rbTree.find(data);
	}
	
	public Object removeMax() {
		return rbTree.removeMax();
	}
	
	public Object removeMin() {
		return rbTree.removeMin();
	}
	
	public Object remove(Object key) {
		return rbTree.remove(key);
	}
	
	public Object getInOrderList() {
		return rbTree.getInOrderList();
	}
	
	public boolean insertUnique(Object data) {
		boolean result = rbTree.insertUnique(data);
		if(result && rbTree.size > maxCount) {
			if(rbTree.compareData(data,  rbTree.getMax()) == 0) {
				result = false;
			}
			rbTree.removeMax();
		}
		return result;
	}
	
	public boolean insertUnion(Object data) {
		boolean result = rbTree.insertUnion(data);
		if(result && rbTree.size > maxCount) {
			if(rbTree.compareData(data,  rbTree.getMax()) > 0) {
				result = false;
			}
			rbTree.removeMax();
		}
		return result;
	}
	
	public FixRBTree(int type, int theMaxCount) {
		rbTree = new RBTree(type);
		maxCount = theMaxCount;
	}
	
	public void dealloc() {
		rbTree.dealloc();
		rbTree = null;
	}
}
