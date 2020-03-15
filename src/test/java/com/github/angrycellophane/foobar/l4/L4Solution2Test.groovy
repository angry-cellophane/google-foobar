package com.github.angrycellophane.foobar.l4


import spock.lang.Specification
import spock.lang.Unroll

class L4Solution2Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L4Solution2.solution(entrances, exists, path) == expected

        where:
        entrances   | exists      | path    | expected
        array(0, 1) | array(4, 5) | path1() | 16
        array(0)    | array(3)    | path2() | 6
        array(0)    | array(3)    | path3() | 9
        array(0)    | array(3)    | path4() | 6
        array(0)    | array(1)    | path5() | 2
        array(0, 2) | array(4, 5) | path6() | 2
        array(0, 2) | array(4, 5) | path7() | 15
        array(0)    | array(3, 4) | path8() | 8
    }

    static int[][] path1() {
        arrays(
                array(0, 0, 4, 6, 0, 0),
                array(0, 0, 5, 2, 0, 0),
                array(0, 0, 0, 0, 4, 4),
                array(0, 0, 0, 0, 6, 6),
                array(0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0),
        )
    }

    static int[][] path2() {
        arrays(
                array(0, 7, 0, 0),
                array(0, 0, 6, 0),
                array(0, 0, 0, 8),
                array(9, 0, 0, 0),
        )
    }

    static int[][] path3() {
        arrays(
                array(0, 5, 5, 0),
                array(0, 0, 0, 6),
                array(0, 0, 0, 4),
                array(0, 0, 0, 0),
        )
    }

    // loop to the first node
    static int[][] path4() {
        arrays(
                array(0, 7, 0, 0),
                array(0, 0, 6, 0),
                array(2, 0, 0, 8),
                array(0, 0, 0, 0),
        )
    }

    // two nods with a loop
    static int[][] path5() {
        arrays(
                array(0, 2),
                array(3, 0)
        )
    }

    // node 1 is a bottleneck with capacity = 2
    static int[][] path6() {
        arrays(
                array(0, 6, 0, 0, 0, 0),
                array(0, 0, 0, 2, 0, 0),
                array(0, 3, 0, 0, 0, 0),
                array(0, 0, 0, 0, 5, 10),
                array(0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0),
        )
    }

    static int[][] path7() {
        arrays(
                array(0, 6, 0, 0, 3, 0),
                array(0, 0, 0, 8, 0, 0),
                array(0, 3, 0, 0, 0, 4),
                array(0, 0, 0, 0, 5, 10),
                array(0, 0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0, 0),
        )
    }

    static int[][] path8() {
        arrays(
                array(0, 3, 5, 0, 0),
                array(0, 0, 0, 4, 4),
                array(0, 5, 0, 0, 0),
                array(0, 0, 0, 0, 0),
                array(0, 0, 0, 0, 0),
        )
    }

    static int[] array(int ... elements) {
        return elements
    }

    static int[][] arrays(int[] ... elements) {
        return elements
    }
}
