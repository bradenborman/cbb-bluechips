package Borman.cbbbluechips.utilities;

import Borman.cbbbluechips.models.MarketValue;

import java.util.ArrayList;
import java.util.List;

public class PriceHistoryUtility {


    public static List<String> apply(List<MarketValue> teamPriceHistory) {
        String[] rounds = new String[]{"64", "32", "16", "8", "4", "2", "1"};
        List<String> priceHistory = new ArrayList<>();

        for (int i = 0, teamPriceHistorySize = teamPriceHistory.size(); i < teamPriceHistorySize; i++) {
            priceHistory.add(rounds[i] + ":" + teamPriceHistory.get(i).getPrice());
        }

        return priceHistory;

    }

}