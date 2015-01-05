package com.xinlukou.mmengine;

public class Transfer {
    public int id;
    public int fromLinkID;
    public int toLinkID;
    public int transTime;
    public int transDistance;
    public int transType;

    public Transfer (int theID, String str) {
    	String[] array = str.split(",", -1);
    	id = theID;
    	fromLinkID = Integer.parseInt(array[0]);
        toLinkID = Integer.parseInt(array[1]);
        transTime = Integer.parseInt(array[2]);
        transDistance = Integer.parseInt(array[3]);
        transType = Integer.parseInt(array[4]);
    }
}
