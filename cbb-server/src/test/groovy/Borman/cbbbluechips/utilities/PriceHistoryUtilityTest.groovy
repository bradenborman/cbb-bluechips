package Borman.cbbbluechips.utilities

import Borman.cbbbluechips.models.MarketValue
import spock.lang.Specification
import spock.lang.Unroll

class PriceHistoryUtilityTest extends Specification {


    @Unroll
    def "PriceHistoryUtility.apply() Test"() {
        when:
        List<String> results = PriceHistoryUtility.apply(marketValueResults)

        then:
        results.contains("64:5000")
        results.contains("32:5500.0")
        results.contains("16:6000.0")
        results.get(1).contains("32:5500.0")
        results.get(2).contains("16:6000.0")

        if (results.size() > 3)
            results.get(3).contains("8:7500.0")

        where:
        marketValueResults << [
                Arrays.asList(new MarketValue(roundId: "32", price: 5500), new MarketValue(roundId: "16", price: 6000)),
                Arrays.asList(new MarketValue(roundId: "16", price: 6000), new MarketValue(roundId: "32", price: 5500)),
                Arrays.asList(new MarketValue(roundId: "16", price: 6000), new MarketValue(roundId: "8", price: 7500), new MarketValue(roundId: "32", price: 5500))
        ]

    }


}