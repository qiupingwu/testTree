package com.xinlukou.mmengine;

public class City {
    public int cityID;
    public String cityKey;
    public int linkCount;
    public int transferCount;
    public int weekdayCount;
    public int weekendCount;
    public String appID;
    public double latitude;
    public double longitude;

    public City (String str) {
        String[] array = str.split(",", -1);
        cityID = Integer.parseInt(array[0]);
        cityKey = array[1];
        linkCount = Integer.parseInt(array[2]);
        transferCount = Integer.parseInt(array[3]);
        weekdayCount = Integer.parseInt(array[4]);
        weekendCount = Integer.parseInt(array[5]);
        appID = array[6];
        latitude = Double.parseDouble(array[7]);
        longitude = Double.parseDouble(array[8]);
    }
}
