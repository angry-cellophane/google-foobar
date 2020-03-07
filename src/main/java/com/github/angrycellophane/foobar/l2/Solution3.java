package com.github.angrycellophane.foobar.l2;

/**
 *  Problem:
 * The LAMBCHOP's engineers have given you lists identifying the placement of groups of pegs along various support beams. You need to place a gear on each peg (otherwise the gears will collide with unoccupied pegs). The engineers have plenty of gears in all different sizes stocked up, so you can choose gears of any size, from a radius of 1 on up. Your goal is to build a system where the last gear rotates at twice the rate (in revolutions per minute, or rpm) of the first gear, no matter the direction. Each gear (except the last) touches and turns the gear on the next peg to the right.
 *
 * Given a list of distinct positive integers named pegs representing the location of each peg along the support beam, write a function solution(pegs) which, if there is a solution, returns a list of two positive integers a and b representing the numerator and denominator of the first gear's radius in its simplest form in order to achieve the goal above, such that radius = a/b. The ratio a/b should be greater than or equal to 1. Not all support configurations will necessarily be capable of creating the proper rotation ratio, so if the task is impossible, the function solution(pegs) should return the list [-1, -1].
 *
 * For example, if the pegs are placed at [4, 30, 50], then the first gear could have a radius of 12, the second gear could have a radius of 14, and the last one a radius of 6. Thus, the last gear would rotate twice as fast as the first one. In this case, pegs would be [4, 30, 50] and solution(pegs) should return [12, 1].
 *
 * The list pegs will be given sorted in ascending order and will contain at least 2 and no more than 20 distinct positive integers, all between 1 and 10000 inclusive.
 *
 *
 * Solution:
 *
 * A simple example:
 *        r0      r1  r1    r2
 *     -------><----|----><----
 * ---P0-----------P1---------P2---
 *    4            30         50
 * P0 = 4
 * P1 = 30
 * P2 = 50
 *
 * main constraint is the last gear rotates at twice the rate of the first gear.
 * This constraint can be rewritten as r0 = (wn/w0) * rn = 2 * rn according to https://en.wikipedia.org/wiki/Gear_train
 *  where wn is angular velocity of the n-th gear and rn is the radius of the n-th gear.
 * r0 = 2*rn
 *
 * then:
 * r0 + r1 = P1 - P0
 * r1 + r2 = P2 - P1
 *
 * r2 = P2 - P1 - r1 = P2 - P1 - (P1 - P0 - r0) = P2 - 2 * P1 + P0 - r0
 * and r0 = 2 * r2, then
 *
 * r0 = 2 * (P2 - 2*P1 +P0 - r0)
 * r0 = -2 * (P2 - 2*P1 + P0)
 *
 * This formula can be generalized the same way for an arbitrary number of pegs - N:
 * if N is even then r0 = 2/3 * (Pn + ... + (-1)^(i) * 2 * Pi + ... - P0)
 * if N is odd then r0 = -2/1 * (Pn + ... + (-1)^(i) * 2 * Pi + ... + P0)
 *                       ^  ^               ^
 *                 factor   denominator     components' sum
 *
 * numerator = factor * components' sum
 * r0 = numerator/denominator
 *
 * denominator is always > 0, then if numerator < 0 or < denominator then it's not possible
 *
 * There are edge cases when r0 can be found but a valid chain of radiuses cannot be built
 * e.g. pegs = [2,6,7]
 * numerator = 6, denominator = 1, r0 = 6
 * 2 + 6 > next peg == 6
 * there is an additional check for such cases.
 */
public class Solution3 {
    public static int[] solution(int[] pegs) {
        if (pegs == null || pegs.length < 2) return notPossible();

        int factor = calculateFactor(pegs);
        int denominator = calculateDenominator(pegs);

        int numerator = factor * calculateComponentsSum(pegs);
        // requirement that numerator/denominator >= 1
        if (numerator < denominator) return notPossible();

        double thisRadius = (double) numerator / denominator;
        int last = pegs.length - 1;
        for (int i=0; i<last; i++) {
            int distance = pegs[i + 1] - pegs[i];
            double thatRadius = distance - thisRadius;
            if (thatRadius < 1.0) return notPossible();

            thisRadius = thatRadius;
        }

        // for cases like sum/denominator = 24/3, it should be simplified to 8/1
        if (denominator != 1 && numerator % denominator == 0) {
            numerator /= denominator;
            denominator = 1;
        }

        return new int[] {numerator, denominator};
    }

    private static int calculateFactor(int[] pegs) {
        return pegs.length % 2 == 0 ? 2 : -2;
    }

    private static int calculateDenominator(int[] pegs) {
        return pegs.length % 2 == 0 ? 3 : 1;
    }

    /*
    calculates Sum = (P(n) - 2*P(n-1) + 2*P(n-2) - ... + 2*P * (-1)^(n) * P1 + (-1)^(n+1) * P0)
     */
    private static int calculateComponentsSum(int[] pegs) {
        int sum = 0;

        sum += pegs[pegs.length - 1];
        boolean negative = true;
        for (int i=pegs.length - 2; i>0; i--) {
            int value = pegs[i] * 2;
            sum += negative ? -value : value;
            negative = !negative;
        }
        sum += negative ? -pegs[0] : pegs[0];

        return sum;
    }

    private static int[] notPossible() {
        return new int[] {-1, -1};
    }
}
