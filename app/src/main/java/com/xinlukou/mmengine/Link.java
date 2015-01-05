package com.xinlukou.mmengine;

public class Link {
    public int id;
    public int wayID;
    public int fromStationID;
    public int toStationID;
    public int averageTime;
    public int distance;
    public int startTransferFromID;
    public int endTransferFromID;
    public int startTransferToID;
    public int endTransferToID;
    public int startWeekdayID;
    public int endWeekdayID;
    public int startWeekendID;
    public int endWeekendID;

    public Link (int theID, String str) {
    	String[] array = str.split(",", -1);
    	id = theID;
        wayID = Integer.parseInt(array[0]);
        fromStationID = Integer.parseInt(array[1]);
        toStationID = Integer.parseInt(array[2]);
        averageTime = Integer.parseInt(array[3]);
        distance = Integer.parseInt(array[4]);
        startTransferFromID = Integer.parseInt(array[5]);
        endTransferFromID = Integer.parseInt(array[6]);
        startTransferToID = Integer.parseInt(array[7]);
        endTransferToID = Integer.parseInt(array[8]);
        startWeekdayID = Integer.parseInt(array[9]);
        endWeekdayID = Integer.parseInt(array[10]);
        startWeekendID = Integer.parseInt(array[11]);
        endWeekendID = Integer.parseInt(array[12]);
    }
}
