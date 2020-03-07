package com.github.angrycellophane.foobar.l2

import spock.lang.Specification
import spock.lang.Unroll

class Solution2Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert Solution2.solution(input) == expected

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
