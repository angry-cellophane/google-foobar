package com.github.angrycellophane.foobar.l3;

import java.math.BigInteger;

/**
 * Problem:
 *
 * Bomb, Baby!
 * ===========
 *
 * You're so close to destroying the LAMBCHOP doomsday device you can taste it! But in order to do so, you need to deploy special self-replicating bombs designed for you by the brightest scientists on Bunny Planet. There are two types: Mach bombs (M) and Facula bombs (F). The bombs, once released into the LAMBCHOP's inner workings, will automatically deploy to all the strategic points you've identified and destroy them at the same time.
 *
 * But there's a few catches. First, the bombs self-replicate via one of two distinct processes:
 * Every Mach bomb retrieves a sync unit from a Facula bomb; for every Mach bomb, a Facula bomb is created;
 * Every Facula bomb spontaneously creates a Mach bomb.
 *
 * For example, if you had 3 Mach bombs and 2 Facula bombs, they could either produce 3 Mach bombs and 5 Facula bombs, or 5 Mach bombs and 2 Facula bombs. The replication process can be changed each cycle.
 *
 * Second, you need to ensure that you have exactly the right number of Mach and Facula bombs to destroy the LAMBCHOP device. Too few, and the device might survive. Too many, and you might overload the mass capacitors and create a singularity at the heart of the space station - not good!
 *
 * And finally, you were only able to smuggle one of each type of bomb - one Mach, one Facula - aboard the ship when you arrived, so that's all you have to start with. (Thus it may be impossible to deploy the bombs to destroy the LAMBCHOP, but that's not going to stop you from trying!)
 *
 * You need to know how many replication cycles (generations) it will take to generate the correct amount of bombs to destroy the LAMBCHOP. Write a function solution(M, F) where M and F are the number of Mach and Facula bombs needed. Return the fewest number of generations (as a string) that need to pass before you'll have the exact number of bombs necessary to destroy the LAMBCHOP, or the string "impossible" if this can't be done! M and F will be string representations of positive integers no larger than 10^50. For example, if M = "2" and F = "1", one generation would need to pass, so the solution would be "1". However, if M = "2" and F = "4", it would not be possible.
 *
 *
 * Solution:
 *
 * Initially there is a tuple (1,1) that can produce either (2,1) or (1,2). These would be generation 1.
 * Generation one can produce (3,1), (2,3), (3,2), (1,3).
 *
 * It is possible to present this data as a binary tree
 *                          (1,1)
 *            (2,1)                       (1,2)
 *    (3,1)         (2,3)          (3,2)        (1,3)
 * (4,1) (3,4)   (5,3) (2,5)    (5,2) (3,5)   (4,3) (1,4)
 *
 * The goal is check if the given (m,f) tuple exists in the tree and return the minimum layer.
 *
 * Walking through the tree is quite slow, O(2^n). M and F can be up to 10^50.
 * Another approach is to walk from the given tuple.
 * If (m,f) exists then its parent is either (m-f, f) or (m, f-m). if m-f<=0 or f-m<= then that branch is not possible.
 * Also if (m,f) exists in the tree then (f,m) exists as well,
 * Hence, (m,f) always can be transformed to a form where the first element >= second element and it would correspond the left branch.
 *     (3,1)
 *  (4,1)  (3,4)
 *
 * It will help to decrease the number of nodes to check.
 * Still in a case with a big difference between m and f it will take O(m) operations to find the result.
 * Another optimization is to use mod operator to "skip" tree levels.
 * Then, this problem is similar to finding greatest common divisor and can be solved in O(log(min(m,f))) time.
 *
 */

public class L3Solution2 {

    private static final String IMPOSSIBLE = "impossible";

    public static String solution(String machs, String faculas) {
        BigInteger generations = BigInteger.ZERO;
        BigInteger m = new BigInteger(machs);
        BigInteger f = new BigInteger(faculas);

        while (f.compareTo(BigInteger.ONE) > 0) {
            generations = generations.add(m.divide(f));
            BigInteger tmp = f;
            f = m.mod(f);
            m = tmp;
        }
        // (m, 0) will never get (1,1)
        if (f.equals(BigInteger.ZERO)) return IMPOSSIBLE;

        // otherwise f == 1, then +(m-1) additional generations.
        return generations.add(m.subtract(BigInteger.ONE)).toString();
    }
}

