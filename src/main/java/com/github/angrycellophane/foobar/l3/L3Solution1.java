package com.github.angrycellophane.foobar.l3;

import java.util.Arrays;
import java.util.Objects;

/**
 * Problem:
 *
 * Doomsday Fuel
 * =============
 *
 * Making fuel for the LAMBCHOP's reactor core is a tricky process because of the exotic matter involved. It starts as raw ore, then during processing, begins randomly changing between forms, eventually reaching a stable form. There may be multiple stable forms that a sample could ultimately reach, not all of which are useful as fuel.
 *
 * Commander Lambda has tasked you to help the scientists increase fuel creation efficiency by predicting the end state of a given ore sample. You have carefully studied the different structures that the ore can take and which transitions it undergoes. It appears that, while random, the probability of each structure transforming is fixed. That is, each time the ore is in 1 state, it has the same probabilities of entering the next state (which might be the same state).  You have recorded the observed transitions in a matrix. The others in the lab have hypothesized more exotic forms that the ore can become, but you haven't seen all of them.
 *
 * Write a function solution(m) that takes an array of array of nonnegative ints representing how many times that state has gone to the next state and return an array of ints for each terminal state giving the exact probabilities of each terminal state, represented as the numerator for each state, then the denominator for all of them at the end and in simplest form. The matrix is at most 10 by 10. It is guaranteed that no matter which state the ore is in, there is a path from that state to a terminal state. That is, the processing will always eventually end in a stable state. The ore starts in state 0. The denominator will fit within a signed 32-bit integer during the calculation, as long as the fraction is simplified regularly.
 *
 * For example, consider the matrix m:
 * [
 *   [0,1,0,0,0,1],  # s0, the initial state, goes to s1 and s5 with equal probability
 *   [4,0,0,3,2,0],  # s1 can become s0, s3, or s4, but with different probabilities
 *   [0,0,0,0,0,0],  # s2 is terminal, and unreachable (never observed in practice)
 *   [0,0,0,0,0,0],  # s3 is terminal
 *   [0,0,0,0,0,0],  # s4 is terminal
 *   [0,0,0,0,0,0],  # s5 is terminal
 * ]
 * So, we can consider different paths to terminal states, such as:
 * s0 -> s1 -> s3
 * s0 -> s1 -> s0 -> s1 -> s0 -> s1 -> s4
 * s0 -> s1 -> s0 -> s5
 * Tracing the probabilities of each, we find that
 * s2 has probability 0
 * s3 has probability 3/14
 * s4 has probability 1/7
 * s5 has probability 9/14
 * So, putting that together, and making a common denominator, gives an answer in the form of
 * [s2.numerator, s3.numerator, s4.numerator, s5.numerator, denominator] which is
 * [0, 3, 2, 9, 14].
 *
 *
 *
 * Solution:
 * Since the problem presents a set of states and transitions between them with some probability
 * it can be expressed as Markov chain.
 * It is guaranteed that there is a path from any state to a terminal state. Hence, the problem can be expressed as absorbing Markov chain
 * where the task is to find the final probability vector of reaching every absorbing state from the given initial state.
 * https://en.wikipedia.org/wiki/Absorbing_Markov_chain
 * and good videos with practical examples - https://www.youtube.com/watch?v=uvYTGEZQTEs&list=PLANMHOrJaFxPMQCMYcYqwOCYlreFswAKP&index=1
 *
 * To solve this problem:
 * 1. take observations and turn them into probabilities by calculating the sum of all entries per row
 *    and diving each value in a row by the sum.
 * 2. Rearrange the matrix in such way that all terminal or absorbing states (rows with sum == 0) go before non-terminal.
 *    After that first rows and columns represent absorbing (terminal) states and the rest are non-terminal
 *    The matrix will for the first example look like this after:
 * [
 *   [0,0  ,0  ,0  ,0  ,0],  # s2 is terminal, and unreachable (never observed in practice)
 *   [0,0  ,0  ,0  ,0  ,0],  # s3 is terminal
 *   [0,0  ,0  ,0  ,0  ,0],  # s4 is terminal
 *   [0,0  ,0  ,0  ,0  ,0],  # s5 is terminal
 *   [0,0  ,0  ,1/2,0  ,1/2],  # s0, the initial state, goes to s1 and s5 with equal probability
 *   [0,3/9,2/9,0  ,4/9,0],  # s1 can become s0, s3, or s4, but with different probabilities
 * ]
 * 3. Let's name this matrix transition matrix and mark as P
 * 4. After that P can be split into 4 submatrices:
 *        | I 0 |
 *    P = | R Q |
 *
 *
 *
 *    where:
 *    - let's N be the number of absorbing states.
 *    - I is an identity matrix of size N for all absorbing states. It can be created by replacing 0 with 1 on the main diagonal in N first rows.
 *    - 0 is a submatrix consisting of zeros
 *    - R and Q are submatrices consisting of non-absorbing states.
 *
 *    For the example above:
 * [
 *   [1 0   0   0  | 0  0  ]  # s2 is terminal, and unreachable (never observed in practice)
 *   [0 1   0   0  | 0  0  ]  # s3 is terminal
 *   [0 0   1   0  | 0  0  ]  # s4 is terminal
 *   [0 0   0   1  | 0  0  ]  # s5 is terminal
 *   -----------0--|------
 *   [0 0   0   1/2|0   1/2]  # s0, the initial state, goes to s1 and s5 with equal probability
 *   [0 3/9 2/9 0  |4/9 0  ]  # s1 can become s0, s3, or s4, but with different probabilities
 * ]
 *   N = 4
 *   R = [0 0   0   1/2]
 *       [0 3/9 2/9 0  ]
 *
 *   Q = [0   1/2]
 *       [4/9 0  ]
 *
 * 5. Then, the Fundamental matrix F can be calculated.
 *    F is defined as F = (I - Q) ^ (-1), where I is an identity matrix.
 *
 * 6. After that final probabilities for non-absorbing states will be defined as F * R
 *    Each row in the F * R matrix represents probabilities for each state.
 *    Since we're interesting in the first (initial, S0) state only it should be the first row if order between non-absorbing states is preserved.
 *
 * 7. Last step is to go through the first row and find the dominator for all of them in the simplest form as required.
 */

public class L3Solution1 {
    /**
     * 1. observations => transition matrix (P) with probabilities and absorbing states go first. Order between non-absorbing states preserved.
     * 2. Get R matrix from P
     * 3. Get Q matrix from P
     * 4. Calculate fundamental matrix F = (I - Q) ^ (-1)
     * 5. Calculate steady transition matrix for non-absorbing states SteadyP = F * R
     * 6. Take the first row of SteadyP, format and add common dominator.
     */
    public static int[] solution(int[][] observations) {
        if (observations == null || observations.length == 0) return new int[0];
        if (observations.length == 1) return new int[] {1,1};

        TransitionMatrix p = calculatePMatrix(observations);
        Fraction[][] r = getRSubmatrixFrom(p);
        Fraction[][] q = getQSubmatrixFrom(p);
        Fraction[][] f = calculateFMatrix(q);
        Fraction[][] steadyP = multiplyMatrices(f,r);

        // steadyP[0] contains probability vector of S0
        int lcm = gcd(steadyP[0]);
        int[] result = new int[steadyP[0].length + 1];
        for (int i = 0; i < steadyP[0].length; i++) {
            result[i] = (int)(((long)steadyP[0][i].numerator * lcm) / steadyP[0][i].denominator);
        }
        result[result.length-1] = lcm;

        return result;
    }

    static TransitionMatrix calculatePMatrix(int[][] observations) {
        int[] sumByRow = Arrays.stream(observations).mapToInt(row -> Arrays.stream(row).sum()).toArray();

        int absorbingCount = (int) Arrays.stream(sumByRow).filter(value -> value == 0).count();
        Fraction[][] rearranged = rearrange(observations, sumByRow);
        return new TransitionMatrix(rearranged, absorbingCount);
    }

    static Fraction[][] getRSubmatrixFrom(TransitionMatrix p) {
        int size = p.matrix.length - p.absorbingStatesCount;
        Fraction[][] r = new Fraction[size][];
        for (int i=0; i<size; i++) {
            r[i] = Arrays.copyOfRange(p.matrix[i + p.absorbingStatesCount], 0, p.absorbingStatesCount);
        }
        return r;
    }

    static Fraction[][] getQSubmatrixFrom(TransitionMatrix p) {
        int size = p.matrix.length - p.absorbingStatesCount;
        Fraction[][] q = new Fraction[size][];
        for (int i=0; i<size; i++) {
            q[i] = Arrays.copyOfRange(p.matrix[i + p.absorbingStatesCount], p.absorbingStatesCount, p.matrix.length);
        }
        return q;
    }

    static Fraction[][] calculateFMatrix(Fraction[][] q) {
        // I - Q, where I - identity matrix
        for (int i=0; i<q.length; i++) {
            for (int j = 0; j < q[i].length; j++) {
                Fraction n = i == j ? Fraction.ONE : Fraction.ZERO;
                q[i][j] = n.subtract(q[i][j]);
            }
        }
        return inverse(q);
    }

    /*
        Gaussian elimination to inverse matrix
        https://en.wikipedia.org/wiki/Gaussian_elimination#Finding_the_inverse_of_a_matrix
     */
    static Fraction[][] inverse(Fraction[][] matrix) {
        int size = matrix.length;
        Fraction[][] inversed = new Fraction[size][size];
        // create identity matrix of size matrix.length
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                inversed[i][j] = i == j ? Fraction.ONE : Fraction.ZERO;
            }
        }

        /*
        to eliminate lower triangle
        1 x x | y y y
        0 1 x | y y y
        0 0 1 | y y y
         */
        for (int i=0; i<size;i++) {
            Fraction divider = matrix[i][i];
            for (int j=0; j<i; j++) {
                inversed[i][j] = inversed[i][j].divide(divider);
            }
            for (int j=i; j<size; j++) {
                matrix[i][j] = matrix[i][j].divide(divider);
                inversed[i][j] = inversed[i][j].divide(divider);
            }
            for (int j=i+1; j<size; j++) {
                if (matrix[j][i].numerator == 0) continue;

                Fraction factor = matrix[j][i];
                for (int k=0; k<i; k++) {
                    inversed[j][k] = inversed[j][k].subtract(inversed[i][k].multiply(factor));
                }
                for (int k=i; k<size; k++) {
                    matrix[j][k] = matrix[j][k].subtract(matrix[i][k].multiply(factor));
                    inversed[j][k] = inversed[j][k].subtract(inversed[i][k].multiply(factor));
                }
            }
        }

        /*
        to eliminate upper triangle
        1 0 0 | y y y
        0 1 0 | y y y
        0 0 1 | y y y
         */
        for (int i=size-1; i>0; i--) {
            for (int j=i-1; j>=0; j--) {
                if (matrix[j][i].numerator == 0) continue;

                Fraction factor = matrix[j][i];
                matrix[j][i] = matrix[j][i].subtract(matrix[i][i].multiply(factor));

                for (int k=0; k<size; k++) {
                    inversed[j][k] = inversed[j][k].subtract(inversed[i][k].multiply(factor));
                }
            }
        }

        return inversed;
    }

    static Fraction[][] multiplyMatrices(Fraction[][] f, Fraction[][] r) {
        int rows = f.length;
        int colums = r[0].length;

        Fraction[][] result = new Fraction[rows][colums];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                result[i][j] = Fraction.ZERO;
                for (int k = 0; k < f[i].length; k++) {
                    result[i][j] = result[i][j].add(f[i][k].multiply(r[k][j]));
                }
            }
        }

        return result;
    }


    /*
        Rearrange rows and columns in such way that absorbing states go before non-absorbing.
        Preserve order between non-absorbing states, e.g. if row[i] and row[j] as non-absorbing
        and i < j, then it guarantees that i' < j' (new corresponding row numbers after rearrangement)
        it's important because the first non-absorbing row will represent the probability vector of S0 that we're looking for.
     */
    static Fraction[][] rearrange(int[][] observations, int[] sumByRow) {
        int[] positions = findNewPositions(sumByRow);

        Fraction[][] newMatrix = new Fraction[observations.length][];
        int size = observations.length;
        for (int i=0; i<size; i++) {
            newMatrix[i] = new Fraction[size];
            for (int j=0; j<size; j++) {
                int numerator = observations[positions[i]][positions[j]];
                newMatrix[i][j] = new Fraction(numerator, sumByRow[positions[i]]);
            }
        }

        return newMatrix;
    }

    static int[] findNewPositions(int[] sumByRow) {
        int[] positions = new int[sumByRow.length];
        int position = 0;
        for (int i=0; i<sumByRow.length; i++) {
            if (sumByRow[i] == 0) {
                positions[position] = i;
                position++;
            }
        }
        for (int i=0; i<sumByRow.length; i++) {
            if (sumByRow[i] != 0) {
                positions[position] = i;
                position++;
            }
        }
        return positions;
    }

    static int gcd(Fraction[] row) {
        int lcm = 1;
        for (Fraction f: row) {
            lcm = lcm(lcm, f.denominator);
        }
        return lcm;
    }

    /*
        greatest common divisor
        https://en.wikipedia.org/wiki/Greatest_common_divisor
     */
    static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        int tmp;
        while (b>0) {
            a %= b;
            tmp = a;
            a = b;
            b = tmp;
        }
        return a;
    }

    /*
        least common multiple
        https://en.wikipedia.org/wiki/Least_common_multiple
     */
    static int lcm(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        // a * b can overflow int
        return (int)(((long)a * b) / gcd(a, b));
    }

    static class Fraction {
        static final Fraction ONE = new Fraction(1, 1);
        static final Fraction ZERO = new Fraction(0, 1);

        final int numerator;
        final int denominator;

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction subtract(Fraction that) {
            int denominator = lcm(this.denominator, that.denominator);
            int numerator = (this.numerator * (denominator / this.denominator)) - (that.numerator * (denominator / that.denominator));

            return simplify(numerator, denominator);
        }

        public Fraction add(Fraction that) {
            int denominator = lcm(this.denominator, that.denominator);
            int numerator = (this.numerator * (denominator / this.denominator)) + (that.numerator * (denominator / that.denominator));

            return simplify(numerator, denominator);
        }

        public Fraction multiply(Fraction that) {
            int denominator = this.denominator * that.denominator;
            int numerator = this.numerator * that.numerator;

            return simplify(numerator, denominator);
        }

        public Fraction divide(Fraction that) {
            int numerator = this.numerator * that.denominator;
            int denominator = this.denominator * that.numerator;

            return simplify(numerator, denominator);
        }

        private static Fraction simplify(int numerator, int denominator) {
            if (numerator < 0 && denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            }
            int gcd = gcd(numerator, denominator);
            if (gcd != 1) {
                numerator /= gcd;
                denominator /= gcd;
            }

            return new Fraction(numerator, denominator);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fraction fraction = (Fraction) o;
            return numerator == fraction.numerator &&
                    denominator == fraction.denominator;
        }

        @Override
        public int hashCode() {
            return Objects.hash(numerator, denominator);
        }
    }

    static class TransitionMatrix {
        final Fraction[][] matrix;
        final int absorbingStatesCount;

        TransitionMatrix(Fraction[][] matrix, int absorbingStatesCount) {
            this.matrix = matrix;
            this.absorbingStatesCount = absorbingStatesCount;
        }
    }
}
