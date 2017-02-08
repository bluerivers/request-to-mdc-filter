package kim.gin.web.filter.mdc

import kim.gin.web.filter.mdc.excluder.SimpleWordsExcluder
import spock.lang.Specification

/**
 *
 * @since 2017-02-08
 */
class ExcluderTest extends Specification {

    def "SimpleWordsExcluderTest" () {

        given:
        Excluder excluder = new SimpleWordsExcluder("a", "b", "c")

        when:
        def decision1 = excluder.decide("a")
        def decision2 = excluder.decide("b")
        def decision3 = excluder.decide("cd")

        then:
        decision1
        decision2
        !decision3

    }
}
