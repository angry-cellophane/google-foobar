package com.github.angrycellophane.foobar.l4


import spock.lang.Specification
import spock.lang.Unroll

class L4Solution1Test extends Specification {

    @Unroll
    void 'check cycles'() {
        expect:
        assert L4Solution1.hasCycle(a, b) == expected

        where:
        a  | b  || expected
        6  | 2  || false
        1  | 7  || false
        1  | 3  || false
        7  | 1  || false
        3  | 1  || false
        1  | 1  || false
        7  | 7  || false
        3  | 13 || false
        21 | 1  || true
        13 | 19 || false
        4  | 1  || true
        21 | 13 || true
        21 | 19 || true
        2  | 3  || true
        2  | 5  || true
        2  | 7  || true
        8  | 2  || true
        10 | 2  || true
        8  | 3  || true
        5  | 3  || false
        5  | 7  || true
        13 | 7  || true
    }

    @Unroll
    void tests() {
        expect:
        assert L4Solution1.solution(input) == expected

        where:
        input                          | expected
        [1, 1] as int[]                | 2
        [4, 1] as int[]                | 0
        [1, 7, 3, 21, 13, 19] as int[] | 0
        [1, 4] as int[]                | 0
        [3, 1, 19, 4] as int[]         | 0
        [3, 1, 19, 4, 5] as int[]      | 1
        [3, 19, 21, 7] as int[]        | 0
    }
}
