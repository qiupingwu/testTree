package com.xinlukou.mmengine;

public class Station {
    public int id;
    public String uno;
    public int multiLine;
    public int multiWay;

    public Station (int theID, String str) {
        String[] array = str.split(",", -1);
        id = theID;
        uno = array[0];
        multiLine = Integer.parseInt(array[1]);
        multiWay = Integer.parseInt(array[2]);
    }
}
