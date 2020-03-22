package com.github.angrycellophane.foobar.l5;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Problem:
 * Dodge the Lasers!
 * =================
 *
 * Oh no! You've managed to escape Commander Lambdas collapsing space station in an escape pod with the rescued bunny prisoners - but Commander Lambda isnt about to let you get away that easily. She's sent her elite fighter pilot squadron after you - and they've opened fire!
 *
 * Fortunately, you know something important about the ships trying to shoot you down. Back when you were still Commander Lambdas assistant, she asked you to help program the aiming mechanisms for the starfighters. They undergo rigorous testing procedures, but you were still able to slip in a subtle bug. The software works as a time step simulation: if it is tracking a target that is accelerating away at 45 degrees, the software will consider the targets acceleration to be equal to the square root of 2, adding the calculated result to the targets end velocity at each timestep. However, thanks to your bug, instead of storing the result with proper precision, it will be truncated to an integer before adding the new velocity to your current position.  This means that instead of having your correct position, the targeting software will erringly report your position as sum(i=1..n, floor(i*sqrt(2))) - not far enough off to fail Commander Lambdas testing, but enough that it might just save your life.
 *
 * If you can quickly calculate the target of the starfighters' laser beams to know how far off they'll be, you can trick them into shooting an asteroid, releasing dust, and concealing the rest of your escape.  Write a function solution(str_n) which, given the string representation of an integer n, returns the sum of (floor(1*sqrt(2)) + floor(2*sqrt(2)) + ... + floor(n*sqrt(2))) as a string. That is, for every number i in the range 1 to n, it adds up all of the integer portions of i*sqrt(2).
 *
 * For example, if str_n was "5", the solution would be calculated as
 * floor(1*sqrt(2)) +
 * floor(2*sqrt(2)) +
 * floor(3*sqrt(2)) +
 * floor(4*sqrt(2)) +
 * floor(5*sqrt(2))
 * = 1+2+4+5+7 = 19
 * so the function would return "19".
 *
 * str_n will be a positive integer between 1 and 10^100, inclusive. Since n can be very large (up to 101 digits!), using just sqrt(2) and a loop won't work. Sometimes, it's easier to take a step back and concentrate not on what you have in front of you, but on what you don't.
 *
 * Solution:
 * Sum(floor(i*sqrt(2))) where i=1, ..., n is a Beatty sequence
 * https://en.wikipedia.org/wiki/Beatty_sequence
 * https://oeis.org/A001951
 *
 * https://math.stackexchange.com/questions/2052179/how-to-find-sum-i-1n-left-lfloor-i-sqrt2-right-rfloor-a001951-a-beatty-s
 *
 * Final recurrent formula is:
 * n'= floor((sqrt(2)-1)*n)
 * Sum(n) = n*n' + n*(n+1)/2 - n'*(n'+1)/2 - Sum(n')
 */

public class L5Solution1 {
    // java 8 doesn't have a built-in method to take square root of BigDecimals. It was added in java9.
    // since the precision is limited to 101 digits a simpler solution would be to have the value hardcoded rather than copying implementation from java sdk 9.
    private static final BigDecimal SQRT_2_MINUS_1 = new BigDecimal("0.4142135623730950488016887242096980785696718753769480731766797379907324784621070388503875343276415727");
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static String solution(String number) {
        BigInteger sum = calculateSum(new BigInteger(number));
        return sum.toString();
    }

    private static BigInteger calculateSum(BigInteger n) {
        if (BigInteger.ONE.equals(n)) return BigInteger.ONE;
        if (n.compareTo(BigInteger.ONE) < 0) return BigInteger.ZERO;

        BigInteger n1 = SQRT_2_MINUS_1.multiply(new BigDecimal(n)).toBigInteger();
        BigInteger nSum = n.multiply((n.add(BigInteger.ONE))).divide(TWO);
        BigInteger n1Sum = n1.multiply((n1.add(BigInteger.ONE))).divide(TWO);
        return n.multiply(n1).add(nSum).subtract(n1Sum).subtract(calculateSum(n1));
    }
}
