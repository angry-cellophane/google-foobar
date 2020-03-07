package com.github.angrycellophane.foobar.l3


import spock.lang.Specification
import spock.lang.Unroll

class L3Solution3Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L3Solution3.solution(input) == expected

        where:
        input | expected
        "3"   | 2
        "15"  | 5
        "1"   | 0
        "2"   | 1
        "4"   | 2
        "14"  | 5
        "21"  | 6
        "2"   | 1
        "13"  | 5
        "19"  | 6
        "29"  | 7
        "31"  | 6
        "300" | 11
        "768" | 10
        "1024" | 10
        "1025" | 11
        "1026" | 11
    }
}
