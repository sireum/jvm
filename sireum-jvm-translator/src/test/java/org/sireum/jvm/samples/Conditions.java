package org.sireum.jvm.samples;

public class Conditions {
	public static void main(String[] args) {
		int i, j, k;

		i = j = k = 0;

		if (i >= j) {
			if (j < k) {
				System.out.println(k);
			}
		} else {
			cmp(i, j);
		}
	}

	public static int cmp(int i, int j) {
		if (i >= j) {
			return i;
		} else {
			return j;
		}
	}
	
}