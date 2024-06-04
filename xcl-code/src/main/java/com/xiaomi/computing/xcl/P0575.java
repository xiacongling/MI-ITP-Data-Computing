package com.xiaomi.computing.xcl;

import java.util.Arrays;
import java.util.stream.Collectors;

public class P0575 {
    public int distributeCandies(int[] candyType) {
        return Math.min(
                candyType.length / 2,
                Arrays.stream(candyType).boxed().collect(Collectors.toSet()).size());
    }
}
