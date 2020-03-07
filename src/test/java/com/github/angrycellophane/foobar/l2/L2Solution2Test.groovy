package com.github.angrycellophane.foobar.l2

import spock.lang.Specification
import spock.lang.Unroll

class L2Solution2Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L2Solution2.solution(input) == expected

        where:
        input                | expected
        [4, 17, 50] as int[] | [-1, -1] as int[]
        [4, 30, 50] as int[] | [12, 1] as int[]
        [2, 14] as int[]     | [8, 1] as int[]
        [2, 13] as int[]     | [22, 3] as int[]
        [2, 6, 7] as int[]   | [-1, -1] as int[]
        [2, 3] as int[]      | [-1, -1] as int[]
    }
}
