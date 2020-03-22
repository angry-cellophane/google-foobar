package com.github.angrycellophane.foobar.l5


import spock.lang.Specification
import spock.lang.Unroll

class L5Solution1Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L5Solution1.solution(input) == expected

        where:
        input | expected
        '5'   | '19'
        '77'  | '4208'
    }
}
