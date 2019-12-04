// Created by Harshdeep Kahlon
// 12/01/2019
// PrimeThreads.java

import java.util.*;

public class PrimeThreads {
    static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    static ArrayList<Integer> primes = new ArrayList<Integer>();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new PrimeRun(i, 1000000, 2000000));
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) threads[i].join();

        System.out.println("PARALLEL IMPLEMENTATION:");
        for (int i = 0; i < 10; i++) {
          if (i <= primes.size()) {
            System.out.println("Element " + i + ": " + primes.get(i));
          }
        }

        System.out.println("Total Number of Primes: " + primes.size());
    }

    static boolean isPrime(int n) {
        if (n == 2 || n == 3 || n == 5) return true;
        if (n <= 1 || (n & 1) == 0) return false;

        for (int i = 3; Math.pow(i, 2) <= n; i += 2)
            if (n % i == 0) return false;

        return true;
    }

    synchronized static void addPrime(int n) {
        primes.add(n);
    }
}

class PrimeRun implements Runnable {
    final int threadID;
    final int min;
    final int max;

    PrimeRun(int threadID, int min, int max) {
        this.threadID = threadID;
        this.min = min;
        this.max = max;
    }

    public void run() {
        for (int i = min; i <= max; i++) {
            if (i % PrimeThreads.NUM_THREADS == threadID)
                if (PrimeThreads.isPrime(i)) PrimeThreads.addPrime(i);
        }
    }
}


