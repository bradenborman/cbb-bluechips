package Borman.cbbbluechips.services;

import Borman.cbbbluechips.daos.PriceHistoryDao;
import Borman.cbbbluechips.models.MarketValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceHistoryService {

    private Logger logger = LoggerFactory.getLogger(PriceHistoryService.class);

    PriceHistoryDao priceHistoryDao;

    public PriceHistoryService(PriceHistoryDao priceHistoryDao) {
        this.priceHistoryDao = priceHistoryDao;
    }

    public List<MarketValue> fetchAllPriceHistory() {
        return priceHistoryDao.fetchAllPriceHistory();
    }


    public List<MarketValue> priceHistoryByTeamId(String teamId) {
        return priceHistoryDao.setPriceHistoryByTeamId(teamId);
    }

}