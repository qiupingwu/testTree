package com.xinlukou.mmengine;

public class Entry {
	public Entry left;
    public Entry right;
    public Entry parent;
    public Object data;
    public Byte color;

    public Entry (Object theData, Entry theParent) {
        left = null;
        right = null;
        parent = theParent;
        data = theData;
    }
}
