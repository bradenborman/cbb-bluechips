package Borman.cbbbluechips.utilities;

import Borman.cbbbluechips.models.MarketValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PriceHistoryUtility {


    public static List<String> apply(List<MarketValue> teamPriceHistory) {
        List<String> priceHistory = new ArrayList<>();
        priceHistory.add("64:5000");

        teamPriceHistory.sort(Comparator.comparingDouble(MarketValue::getPrice));
        priceHistory.addAll(
                teamPriceHistory.stream().map(x -> x.getRoundId() + ":" + x.getPrice()).collect(Collectors.toList())
        );

        return priceHistory;

    }

}