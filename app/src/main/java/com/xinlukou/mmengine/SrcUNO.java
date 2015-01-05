package com.xinlukou.mmengine;

public class SrcUNO {
    public String uno;
    public String range;
    public String type;
    public String simplified;
    public String traditional;
    public String japanese;
    public String english;
    public String pinyin;
    public String py;
    public String latitude;
    public String longitude;
    public String pointX;
    public String pointY;
    public String wikiZH;
    public String wikiJA;
    public String wikiEN;
    public String color;

    public SrcUNO (String str) {
        String[] array = str.split(",", -1);
        uno = array[0];
        range = array[1];
        type = array[2];
        simplified = array[3];
        traditional = array[4];
        japanese = array[5];
        english = array[6];
        pinyin = array[7];
        py = array[8];
        latitude = array[9];
        longitude = array[10];
        pointX = array[11];
        pointY = array[12];
        wikiZH = array[13];
        wikiJA = array[14];
        wikiEN = array[15];
        if(array.length > 16) color = array[16];
    }
}
