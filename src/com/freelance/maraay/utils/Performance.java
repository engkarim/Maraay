package com.freelance.maraay.utils;

public class Performance {
	public static void releaseMemory() {
		System.gc();
		Runtime r = Runtime.getRuntime();
		r.gc();
	}

}
