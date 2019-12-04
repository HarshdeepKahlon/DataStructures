// Created by Harshdeep Kahlon
// 12/01/2019
// PrimeThreads.java

import java.util.*;

public class PrimeSerial {

    public static void serialPrimeNums(int start, int end) {
        System.out.println("SERIAL IMPLEMENTATION:");
        int num = start;
        int i = 0;
        while (num <= end) {
            if (checkPrime(num)) {
                if (i < 10) {
                    System.out.println("Element " + i + ": " + num);
                }
                i++;
            }
            num++;
        }
        System.out.println("Total Number of Primes: " + i);
    }

    public static boolean checkPrime(int n) {
        if (n == 2 || n == 3 || n == 5) return true;
        if (n <= 1 || (n & 1) == 0) return false;

        for (int i = 3; Math.pow(i, 2) <= n; i += 2)
            if (n % i == 0) return false;

        return true;
    }

    public static void main(String[] args) {
        PrimeSerial.serialPrimeNums(1000000, 2000000);
    }

}