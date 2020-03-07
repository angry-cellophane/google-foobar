package com.github.angrycellophane.foobar.l3


import spock.lang.Specification
import spock.lang.Unroll

class L3Solution2Test extends Specification {

    @Unroll
    void tests() {
        expect:
        assert L3Solution2.solution(m, f) == expected

        where:
        m                | f                || expected
        '1'              | '1'              || '0'
        '2'              | '5'              || '3'
        '2'              | '1'              || '1'
        '4'              | '7'              || '4'
        '2'              | '4'              || 'impossible'
        '4'              | '2'              || 'impossible'
        '3'              | '2'              || '2'
        '2'              | '5'              || '3'
        '4'              | '1'              || '3'
        '1' + ('0' * 50) | '1'              || '9' * 50
        '1'              | '1' + ('0' * 50) || '9' * 50
        '1' + ('0' * 50) | '2'              || 'impossible'
    }
}
