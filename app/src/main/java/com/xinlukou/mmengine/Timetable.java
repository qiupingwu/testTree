package com.xinlukou.mmengine;

public class Timetable {
    public int id;
    public int linkID;
    public int depTime;
    public int arrTime;

    public Timetable (int theID, String[] array) {
        id = theID;
        linkID = -1;
        depTime = Integer.parseInt(array[0]);
        arrTime = Integer.parseInt(array[1]);
    }
    
    public Timetable (int theID, String str) {
    	String[] array = str.split(",", -1);
        id = theID;
        linkID = -1;
        depTime = Integer.parseInt(array[0]);
        arrTime = Integer.parseInt(array[1]);
    }
    
    public Timetable (int theID, int theLinkID, int theDepTime, int theArvTime) {
    	id = theID;
        linkID = theLinkID;
        depTime = theDepTime;
        arrTime = theArvTime;
    }

}
