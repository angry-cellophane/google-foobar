package com.github.angrycellophane.foobar.l3;

import java.math.BigInteger;

/**
 * Problem:
 Fuel Injection Perfection
 =========================

 Commander Lambda has asked for your help to refine the automatic quantum antimatter fuel injection system for her LAMBCHOP doomsday device. It's a great chance for you to get a closer look at the LAMBCHOP - and maybe sneak in a bit of sabotage while you're at it - so you took the job gladly.

 Quantum antimatter fuel comes in small pellets, which is convenient since the many moving parts of the LAMBCHOP each need to be fed fuel one pellet at a time. However, minions dump pellets in bulk into the fuel intake. You need to figure out the most efficient way to sort and shift the pellets down to a single pellet at a time.

 The fuel control mechanisms have three operations:

 1) Add one fuel pellet
 2) Remove one fuel pellet
 3) Divide the entire group of fuel pellets by 2 (due to the destructive energy released when a quantum antimatter pellet is cut in half, the safety controls will only allow this to happen if there is an even number of pellets)

 Write a function called solution(n) which takes a positive integer as a string and returns the minimum number of operations needed to transform the number of pellets to 1. The fuel intake control panel can only display a number up to 309 digits long, so there won't ever be more pellets than you can express in that many digits.*

 Solution:
 Just to try to apply operations in iterations until the number of pellets is 0.
 if the number of pellets in the current iteration is even than the best strategy is to divide it by 0.
 if the number of pellets is odd then there are two options: either to add 1 pellet or subtract 1.
 A better option is the one that produces an even number.
 Since (pellets + 1) / 2 - (pellets - 1) / 2 == 1 one of the options should produce a even number.

 The only exception from this rule is number 3 as 3 -> 2 -> 1 is shorter than 3 -> 4 -> 2 -> 1.
 */

public class L3Solution3 {

    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);

    public static int solution(String pellets) {
        BigInteger number = new BigInteger(pellets);
        if (number.compareTo(ONE) < 1) return 0;

        int transformations = 0;
        while (!number.equals(ONE)) {
            if (number.mod(TWO).equals(ONE)) {
                boolean chooseNext = !number.equals(THREE) && number.add(ONE).divide(TWO).mod(TWO).equals(ZERO);
                if (chooseNext) {
                    number = number.add(ONE);
                } else {
                    number = number.subtract(ONE);
                }
                transformations++;
            }

            number = number.divide(TWO);
            transformations++;
        }

        return transformations;
    }
}

