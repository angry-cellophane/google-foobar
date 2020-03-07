package com.github.angrycellophane.foobar.l3


import spock.lang.Specification
import spock.lang.Unroll

class L3Solution1Test extends Specification {

    @Unroll
    void 'verify fractions addition'() {
        expect:
        op1.add(op2) == result


        where:
        op1 | op2 | result
        new L3Solution1.Fraction(1, 3)  | new L3Solution1.Fraction(1, 3) | new L3Solution1.Fraction(2, 3)
        new L3Solution1.Fraction(2, 3)  | new L3Solution1.Fraction(1, 3) | new L3Solution1.Fraction(1, 1)
        new L3Solution1.Fraction(4, 3)  | new L3Solution1.Fraction(1, 3) | new L3Solution1.Fraction(5, 3)
        new L3Solution1.Fraction(4, 3)  | new L3Solution1.Fraction(2, 3) | new L3Solution1.Fraction(2, 1)
        new L3Solution1.Fraction(4, 3)  | new L3Solution1.Fraction(2, 5) | new L3Solution1.Fraction(26, 15)
        new L3Solution1.Fraction(-4, 2) | new L3Solution1.Fraction(1, 3) | new L3Solution1.Fraction(-5, 3)
    }

    @Unroll
    void 'verify fractions subtraction'() {
        expect:
        op1.subtract(op2) == result


        where:
        op1                        | op2                           | result
        new L3Solution1.Fraction(1, 3)         | new L3Solution1.Fraction(1, 3)            | new L3Solution1.Fraction(0, 1)
        new L3Solution1.Fraction(2, 3)         | new L3Solution1.Fraction(1, 3)            | new L3Solution1.Fraction(1, 3)
        new L3Solution1.Fraction(4, 3)         | new L3Solution1.Fraction(1, 3)            | new L3Solution1.Fraction(1, 1)
        new L3Solution1.Fraction(4, 3)         | new L3Solution1.Fraction(2, 3)            | new L3Solution1.Fraction(2, 3)
        new L3Solution1.Fraction(-4, 3)        | new L3Solution1.Fraction(2, 3)            | new L3Solution1.Fraction(-2, 1)
        new L3Solution1.Fraction(3, 5)         | new L3Solution1.Fraction(2, 7)            | new L3Solution1.Fraction(11, 35)
        new L3Solution1.Fraction(19, 3)        | new L3Solution1.Fraction(1, 3)            | new L3Solution1.Fraction(6, 1)
        new L3Solution1.Fraction(32747, 44160) | new L3Solution1.Fraction(106027, 1810560) | new L3Solution1.Fraction(10305, 15088)
    }

    @Unroll
    void 'verify fractions multiplication'() {
        expect:
        op1.multiply(op2) == result


        where:
        op1                     | op2                     | result
        new L3Solution1.Fraction(1, 3)      | new L3Solution1.Fraction(1, 3)      | new L3Solution1.Fraction(1, 9)
        new L3Solution1.Fraction(0, 3)      | new L3Solution1.Fraction(1, 3)      | new L3Solution1.Fraction(0, 1)
        new L3Solution1.Fraction(2, 3)      | new L3Solution1.Fraction(3, 4)      | new L3Solution1.Fraction(1, 2)
        new L3Solution1.Fraction(-2, 3)     | new L3Solution1.Fraction(3, 4)      | new L3Solution1.Fraction(-1, 2)
        new L3Solution1.Fraction(-2, 3)     | new L3Solution1.Fraction(-3, 4)     | new L3Solution1.Fraction(1, 2)
        new L3Solution1.Fraction(687, 1312) | new L3Solution1.Fraction(463, 4140) | new L3Solution1.Fraction(106027, 1810560)
    }

    @Unroll
    void 'verify fractions division'() {
        expect:
        op1.divide(op2) == result


        where:
        op1                 | op2                 | result
        new L3Solution1.Fraction(1, 3)  | new L3Solution1.Fraction(1, 1)  | new L3Solution1.Fraction(1, 3)
        new L3Solution1.Fraction(0, 3)  | new L3Solution1.Fraction(1, 1)  | new L3Solution1.Fraction(0, 1)
        new L3Solution1.Fraction(9, 1)  | new L3Solution1.Fraction(3, 1)  | new L3Solution1.Fraction(3, 1)
        new L3Solution1.Fraction(-9, 1) | new L3Solution1.Fraction(3, 1)  | new L3Solution1.Fraction(-3, 1)
        new L3Solution1.Fraction(-9, 1) | new L3Solution1.Fraction(-3, 1) | new L3Solution1.Fraction(3, 1)
        new L3Solution1.Fraction(2, 3)  | new L3Solution1.Fraction(3, 4)  | new L3Solution1.Fraction(8, 9)
        new L3Solution1.Fraction(2, 3)  | new L3Solution1.Fraction(2, 3)  | new L3Solution1.Fraction(1, 1)
        new L3Solution1.Fraction(6, 3)  | new L3Solution1.Fraction(8, 3)  | new L3Solution1.Fraction(3, 4)
    }

    @Unroll
    void tests() {
        expect:
        assert L3Solution1.solution(input) == expected

        where:
        input                                                                                                 | expected
        [array(0, 2, 1, 0, 0), array(0, 0, 0, 3, 4), zeros(5), zeros(5), zeros(5)] as int[][]                 | array(7, 6, 8, 21)
        [array(0, 1, 0, 0, 0, 1), array(4, 0, 0, 3, 2, 0), zeros(6), zeros(6), zeros(6), zeros(6)] as int[][] | array(0, 3, 2, 9, 14)
        tc3()                                                                                                 | array(1, 2, 3)
        tc4()                                                                                                 | array(1, 2, 3, 4, 5, 15)
        tc5()                                                                                                 | array(4, 5, 5, 4, 2, 20)
        tc6()                                                                                                 | array(1, 1, 1, 1, 1, 5)
        tc7()                                                                                                 | array(2, 1, 1, 1, 1, 6)
        tc8()                                                                                                 | array(6, 44, 4, 11, 22, 13, 100)
        tc9()                                                                                                 | array(1, 1, 1, 2, 5)
    }


    static int[][] tc3() {
        [
                array(1, 2, 3, 0, 0, 0),
                array(4, 5, 6, 0, 0, 0),
                array(7, 8, 9, 1, 0, 0),
                array(0, 0, 0, 0, 1, 2),
                zeros(6),
                zeros(6)
        ] as int[][]
    }

    static int[][] tc4() {
        [
                array(0, 0, 12, 0, 15, 0, 0, 0, 1, 8),
                array(0, 0, 60, 0, 0, 7, 13, 0, 0, 0),
                array(0, 15, 0, 8, 7, 0, 0, 1, 9, 0),
                array(23, 0, 0, 0, 0, 1, 0, 0, 0, 0),
                array(37, 35, 0, 0, 0, 0, 3, 21, 0, 0),
                zeros(10),
                zeros(10),
                zeros(10),
                zeros(10),
                zeros(10),
        ] as int[][]
    }

    static int[][] tc5() {
        [
                array(0, 7, 0, 17, 0, 1, 0, 5, 0, 2),
                array(0, 0, 29, 0, 28, 0, 3, 0, 16, 0),
                array(0, 3, 0, 0, 0, 1, 0, 0, 0, 0),
                array(48, 0, 3, 0, 0, 0, 17, 0, 0, 0),
                array(0, 6, 0, 0, 0, 1, 0, 0, 0, 0),
                zeros(10),
                zeros(10),
                zeros(10),
                zeros(10),
                zeros(10)
        ] as int[][]
    }

    static int[][] tc6() {
        [
                array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        ] as int[][]
    }

    static int[][] tc7() {
        [
                array(1, 1, 1, 0, 1, 0, 1, 0, 1, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 0, 1, 1, 1, 0, 1, 0, 1, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 0, 1, 0, 1, 1, 1, 0, 1, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 0, 1, 0, 1, 0, 1, 1, 1, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(1, 0, 1, 0, 1, 0, 1, 0, 1, 1),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        ] as int[][]
    }

    static int[][] tc8() {
        [
                array(0, 86, 61, 189, 0, 18, 12, 33, 66, 39),
                array(0, 0, 2, 0, 0, 1, 0, 0, 0, 0),
                array(15, 187, 0, 0, 18, 23, 0, 0, 0, 0),
                array(1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        ] as int[][]
    }

    static int[][] tc9() {
        [
                array(0, 0, 0, 0, 3, 5, 0, 0, 0, 2),
                array(0, 0, 4, 0, 0, 0, 1, 0, 0, 0),
                array(0, 0, 0, 4, 4, 0, 0, 0, 1, 1),
                array(13, 0, 0, 0, 0, 0, 2, 0, 0, 0),
                array(0, 1, 8, 7, 0, 0, 0, 1, 3, 0),
                array(1, 7, 0, 0, 0, 0, 0, 2, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        ] as int[][]
    }

    static int[] array(int ... nums) {
        return nums;
    }

    static int[] zeros(int count) {
        return new int[count];
    }
}
