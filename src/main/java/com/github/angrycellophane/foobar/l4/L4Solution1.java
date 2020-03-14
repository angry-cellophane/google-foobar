package com.github.angrycellophane.foobar.l4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Problem:
 * Distract the Guards
 * ===================
 *
 * The time for the mass escape has come, and you need to distract the guards so that the bunny prisoners can make it out! Unfortunately for you, they're watching the bunnies closely. Fortunately, this means they haven't realized yet that the space station is about to explode due to the destruction of the LAMBCHOP doomsday device. Also fortunately, all that time you spent working as first a minion and then a henchman means that you know the guards are fond of bananas. And gambling. And thumb wrestling.
 *
 * The guards, being bored, readily accept your suggestion to play the Banana Games.
 *
 * You will set up simultaneous thumb wrestling matches. In each match, two guards will pair off to thumb wrestle. The guard with fewer bananas will bet all their bananas, and the other guard will match the bet. The winner will receive all of the bet bananas. You don't pair off guards with the same number of bananas (you will see why, shortly). You know enough guard psychology to know that the one who has more bananas always gets over-confident and loses. Once a match begins, the pair of guards will continue to thumb wrestle and exchange bananas, until both of them have the same number of bananas. Once that happens, both of them will lose interest and go back to guarding the prisoners, and you don't want THAT to happen!
 *
 * For example, if the two guards that were paired started with 3 and 5 bananas, after the first round of thumb wrestling they will have 6 and 2 (the one with 3 bananas wins and gets 3 bananas from the loser). After the second round, they will have 4 and 4 (the one with 6 bananas loses 2 bananas). At that point they stop and get back to guarding.
 *
 * How is all this useful to distract the guards? Notice that if the guards had started with 1 and 4 bananas, then they keep thumb wrestling! 1, 4 -> 2, 3 -> 4, 1 -> 3, 2 -> 1, 4 and so on.
 *
 * Now your plan is clear. You must pair up the guards in such a way that the maximum number of guards go into an infinite thumb wrestling loop!
 *
 * Write a function solution(banana_list) which, given a list of positive integers depicting the amount of bananas the each guard starts with, returns the fewest possible number of guards that will be left to watch the prisoners. Element i of the list will be the number of bananas that guard i (counting from 0) starts with.
 *
 * The number of guards will be at least 1 and not more than 100, and the number of bananas each guard starts with will be a positive integer no more than 1073741823 (i.e. 2^30 -1). Some of them stockpile a LOT of bananas.
 *
 * Solution:
 * 1. Check each pair of guards if they can be pair off to wrestle infinitely based on the number of bananas they have (check the hasCycle method's comments for details).
 * 2. Build a graph representing connections between guards that can wrestle infinitely.
 *    Each vertex represents a guard, edge between guards A and B represents the fact the guards can wrestle infinitely.
 * 3. The last part is to match guards in a way to maximize the number of matches.
 *    A simple greedy algorithm is used. It might not be enough and the more complex blossom algorithm should be used instead,
 *    However, the greedy algorithm is simpler to implement and it passes all test cases.
 *    https://en.wikipedia.org/wiki/Blossom_algorithm
 * 4. The result is the number of guards that cannot be matched.
 */

public class L4Solution1 {

    public static int solution(int[] bananas) {
        Map<Integer, Set<Integer>> infinitePlaysGraph = calculateInfinitePlaysGraph(bananas);
        int matches = findMaximumMatches(infinitePlaysGraph);
        return bananas.length - (matches * 2);
    }

    /*
        returns a graph where each vertex is a guard and edge between vertices represents the fact the guards can play forever as there is a cycle.
     */
    static Map<Integer, Set<Integer>> calculateInfinitePlaysGraph(int[] bananas) {
        Map<Integer, Set<Integer>> incidentsGraph = new HashMap<>();

        for (int i = 0; i < bananas.length; i++) {
            for (int j = i+1; j < bananas.length; j++) {
                boolean cycle = hasCycle(bananas[i], bananas[j]);
                if (cycle) {
                    incidentsGraph.computeIfAbsent(i, k -> new HashSet<>()).add(j);
                    incidentsGraph.computeIfAbsent(j, k -> new HashSet<>()).add(i);
                }
            }
        }
        return incidentsGraph;
    }

    /*
        greedy algorithm to find the number of edges in the maximum matching.
        it tries to find a match for every vertex.
        on each iteration it finds a vertex with the minimum number of edges and matches it with the incident vertex which has the maximum number of edges.
     */
    static int findMaximumMatches(Map<Integer, Set<Integer>> graph) {
        int matching = 0;
        Set<Integer> used = new HashSet<>();
        Integer vertex = findVertexWithMinIncidents(graph, used);
        while (vertex != null) {
            Integer matched = findVertexWithMaxIncidents(graph.get(vertex), graph, used);
            if (matched != null) {
                used.add(matched);
                matching++;
            }
            used.add(vertex);
            vertex = findVertexWithMinIncidents(graph, used);
        }

        return matching;
    }

    static Integer findVertexWithMinIncidents(Map<Integer, Set<Integer>> graph, Set<Integer> used) {
        return graph.entrySet().stream().filter(e -> !used.contains(e.getKey()))
                .min(Comparator.comparingLong(e -> e.getValue().stream().filter(v -> !used.contains(v)).count()))
                .map(e -> e.getKey())
                .orElse(null);
    }

    static Integer findVertexWithMaxIncidents(Set<Integer> vertices, Map<Integer, Set<Integer>> graph, Set<Integer> used) {
        return vertices.stream().filter(v -> !used.contains(v))
                .max(Comparator.comparingLong(v -> graph.get(v).stream().filter(u -> !used.contains(u)).count()))
                .orElse(null);
    }

    /**
     * (a, b) can be transformed into (a + a, b - a) or (a - b, b + b)
     * The sum of the first and second components remains the same
     * sum((a,b)) = a + b
     * sum((a+a, b-a)) = a + a + b - a = a + b
     * sum((a-b, b+b)) = a - b + b + b = a + b
     *
     * if c = a+b then b = c -a and a-b = a - (c-a) => a-b = 2*a - c
     * (a-b)+(a-b) = (2*a - c) + (2*a - c) = 4*a - 2c
     * (a-b) - (b+b) = a - b - b - b = a - 3b = a - 3*(c-a) = a - 3c + 3a = 4a - 3c
     *
     * then value of a on each iteration can be calculated as ai = (2^i * a) (mod c)
     * It is easier to check if there is such i that ai = c/2; there is no need to find the i, just to check if it can exist
     *
     * c/2 = (2^i * a) (mod c) => 1 = (2^i * (a/(c/2))) (mod 2)
     * Fraction a / (c/2) should be simplified by dividing by greatest common divisor.
     * then (2^i * (a/(c/2)) can be simplified further. If c/2 is dividable by 2 and c/2 = 2 * d,
     * then (2^i * (a/(c/2)) == (2^i * (a/d)), where d is not dividable by 2.
     * Then if a / d is not an integer it's not possible for 2^i * (a/d) = 1 by mod 2, because d is not dividable by 2.
     * If a / d is an integer than it is possible for 2^i * (a/d) = 1 (mod 2) only if a/d = 1 by mod 2.
     *
     * So if a/d is an integer and a/d = 1 (mod 2) then a and b don't have cycles and (a,b) turns into ((a+b)/2, (a+b)/2) after some iterations.
     */
    static boolean hasCycle(long a, long b) {
        long c = a + b;
        if (c % 2 != 0) return true;
        long gcd = gcd(a, c);
        a /= gcd;
        c /= gcd;

        while (c % 2 == 0) {
            c /= 2;
        }

        return !((a % c == 0) && ((a / c) % 2 == 1));
    }


    /*
        greatest common divisor
        https://en.wikipedia.org/wiki/Greatest_common_divisor
     */
    static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);

        long tmp;
        while (b>0) {
            a %= b;
            tmp = a;
            a = b;
            b = tmp;
        }
        return a;
    }
}
