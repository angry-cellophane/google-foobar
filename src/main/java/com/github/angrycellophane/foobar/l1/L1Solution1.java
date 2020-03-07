package com.github.angrycellophane.foobar.l1;

import java.util.ArrayList;
import java.util.List;

public class L1Solution1 {
    public static int[] solution(int area) {
        List<Integer> result = new ArrayList<>();

        int leftArea = area;
        while (leftArea > 0) {
            int root = (int) Math.floor(Math.sqrt(leftArea));
            int closestSquare = root * root;

            leftArea -= closestSquare;
            result.add(closestSquare);
        }
        return result.stream().mapToInt(i -> i).toArray();
    }
}
