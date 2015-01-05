package com.xinlukou.mmengine;

public class PairInt {
    public int value1;
    public int value2;

    public PairInt (int theValue1, int theValue2) {
        value1 = theValue1;
        value2 = theValue2;
    }
    
    public PairInt (PairInt copy) {
    	value1 = copy.value1;
    	value2 = copy.value2;
    }
}
