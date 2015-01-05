package com.xinlukou.mmcommon;

public class Stopwatch {
	private long start;

	public Stopwatch() {
	}

	public void reset() {
		start = System.currentTimeMillis();
	}

	public double diff() {
		long now = System.currentTimeMillis();
		return (now - start) / 1000.0;
	}
	
	public void out(String str) {
		System.out.println(String.format("%f : %s", diff() , str));
	}
}
