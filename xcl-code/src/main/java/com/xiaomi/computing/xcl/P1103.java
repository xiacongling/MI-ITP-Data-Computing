package com.xiaomi.computing.xcl;

public class P1103 {
    public int[] distributeCandies(int c, int n) {
        int round = (int) ((Math.sqrt(2 * c + 0.25) - 0.5) / n);
        int remain = c - n * n * round * (round - 1) / 2 - round * n * (n + 1) / 2;

        int[] result = new int[n];
        int start = round * (round - 1) / 2 * n + round;
        for (int i = 0; i < n; i++) {
            int fullRounds = start + i * round;
            int lastRound = Math.min((n * round) + 1 + i, remain);
            remain -= lastRound;
            result[i] = fullRounds + lastRound;
        }
        return result;
    }
}
