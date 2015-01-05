package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xinlukou.mmcommon.Helper;

public class SrcFare {
    public String fareType;
    public String wayUNOList;
    public String commonFare;
    public String fileFare;
    public String stationUNOList;
    public List<String> wayList;
    public List<String> unoList;
    public List<String> fareStrList;
    public List<List<String>> fareList;

    public SrcFare (String str) {
        String[] array = str.split(",", -1);
        fareType = array[0];
        wayUNOList = array[1];
        commonFare = array[2];
        fileFare = array[3];
        stationUNOList = array[4];
        wayList = Arrays.asList(wayUNOList.split("\\|"));
        unoList = Arrays.asList(stationUNOList.split("\\|"));
        if(!Helper.isEmpty(fileFare)) {
            String fileName = fileFare;
            if (fileName.endsWith(".csv")) {
                fileName = fileName.substring(0, fileName.length() - 4);
            }
            String[] rowArray = DM.getCsv(fileName);
            fareStrList = new ArrayList<String>(rowArray.length);
            fareList = new ArrayList<List<String>>(rowArray.length);
            for (String row : rowArray) {
                if(Helper.isEmpty(row)) continue;
                fareStrList.add(row);
                fareList.add(new ArrayList<String>());
            }
        }
    }
    
    public float getFare(Integer rowIndex, Integer colIndex) {
        List<String> rowList = fareList.get(rowIndex);
        if(rowList.size() == 0) {
            List<String> tempRowList = Arrays.asList(fareStrList.get(rowIndex).split(","));
            rowList.addAll(tempRowList);
        }
        return Float.parseFloat(rowList.get(colIndex));
    }
}
