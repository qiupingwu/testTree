package com.xinlukou.mmengine;

public class SrcConfig {
    public String versionNO;
    public Integer searchResultType;
    public Integer searchResultFormat;
    public Integer requestTerminal;
    public Integer resultRouteCount;
    public Integer searchType;
    public String dataID;
    public Integer supportFlag;
    public Integer dtType;

    public SrcConfig (String str) {
        String[] array = str.split(",", -1);
        versionNO = array[0];
        searchResultType = Integer.parseInt(array[1]);
        searchResultFormat = Integer.parseInt(array[2]);
        requestTerminal = Integer.parseInt(array[3]);
        resultRouteCount = Integer.parseInt(array[4]);
        searchType = Integer.parseInt(array[5]);
        dataID = array[6];
        supportFlag = Integer.parseInt(array[7]);
        dtType = Integer.parseInt(array[8]);
    }
}
