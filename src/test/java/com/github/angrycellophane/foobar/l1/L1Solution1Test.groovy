package com.github.angrycellophane.foobar.l1


import spock.lang.Specification
import spock.lang.Unroll

class L1Solution1Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L1Solution1.solution(area) == expected

        where:
        area      | expected
        15324     | [15129, 169, 25, 1] as int[]
        12        | [9, 1, 1, 1] as int[]
        1         | [1] as int[]
        2         | [1, 1] as int[]
        3         | [1, 1, 1] as int[]
        4         | [4] as int[]
        36        | [36] as int[]
        59        | [49, 9, 1] as int[]
        102       | [100, 1, 1] as int[]
        256       | [256] as int[]
        300       | [289, 9, 1, 1] as int[]
        1_000_000 | [1_000_000] as int[]
    }
}
