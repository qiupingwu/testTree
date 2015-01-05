package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.List;

public class Line {
    public int id;
    public String uno;
    public List<Integer> stationIDList;

    public Line (int theID, String str) {
        String[] array = str.split(",", -1);
        id = theID;
        uno = array[0];
        stationIDList = new ArrayList<Integer>(array.length - 1);
        for (int i = 1; i < array.length; i++) {
            stationIDList.add(Integer.parseInt(array[i]));
        }
    }
}
