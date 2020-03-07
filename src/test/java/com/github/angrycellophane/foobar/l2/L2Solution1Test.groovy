package com.github.angrycellophane.foobar.l2

import spock.lang.Specification
import spock.lang.Unroll

class L2Solution1Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L2Solution1.solution(input) == expected

        where:
        input            | expected
        ">><"            | 4
        "<>>"            | 0
        "<<>><"          | 4
        ">----<"         | 2
        "--->-><-><-->-" | 10
        "----"           | 0
        ">>>>>"          | 0
        "<<<<<"          | 0
        "<"              | 0
        ">>><<<"         | 18
        "<<<>>>"         | 0
        ">>>>><"         | 10
    }
}
