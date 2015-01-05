package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class Way {
    public int id;
    public String uno;
    public int lineID;
    public int waitTime;
    public List<Integer> stationIDList;

    public Way (int theID, String str) {
        String[] array = str.split(",", -1);
        id = theID;
        uno = array[0];
        lineID = Integer.parseInt(array[1]);
        waitTime = Integer.parseInt(array[2]);
        stationIDList = new ArrayList<Integer>(array.length - 3);
        for (int i = 3; i < array.length; i++) {
            stationIDList.add(Integer.parseInt(array[i]));
        }
    }
}
