package Borman.cbbbluechips.services;

import Borman.cbbbluechips.daos.TransactionDao;
import Borman.cbbbluechips.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCacheService {

    Logger logger = LoggerFactory.getLogger(TransactionCacheService.class);

    private TransactionDao transactionDao;

    public TransactionCacheService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    /*

    Transactions cant change, or edit.
    So might as well cache them to save time when I know nothing has changed

     */


    @Cacheable(value = "recentTransactions")
    public List<Transaction> allTransactions(Integer transactionCount) {
        logger.warn("Not cached for {}", transactionCount);
        return transactionDao.getAllTransactions();
    }

}