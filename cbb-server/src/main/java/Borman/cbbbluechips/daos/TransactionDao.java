package Borman.cbbbluechips.daos;

import Borman.cbbbluechips.daos.sql.TransactionSQL;
import Borman.cbbbluechips.mappers.TransactionRowMapper;
import Borman.cbbbluechips.models.SearchTag;
import Borman.cbbbluechips.models.TradeRequest;
import Borman.cbbbluechips.models.Transaction;
import Borman.cbbbluechips.utilities.FilteredSearchUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionDao {

    Logger logger = LoggerFactory.getLogger(TransactionDao.class);


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public TransactionDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transaction> getAllTransactionByUser(String userName) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("userName", userName);
        return namedParameterJdbcTemplate.query(TransactionSQL.getByUserId, params, new TransactionRowMapper());
    }

    public List<Transaction> getAllTransactionByTeam(String teamName) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamName", teamName);
        return namedParameterJdbcTemplate.query(TransactionSQL.getByTeamName, params, new TransactionRowMapper());
    }

    public void sellShares(TradeRequest tradeRequest) {
        try {
            SqlParameterSource params = new BeanPropertySqlParameterSource(tradeRequest);
            namedParameterJdbcTemplate.update(TransactionSQL.sellShares, params);
        } catch (Exception e) {
            System.out.println("Error updating sell" + e);
        }
    }

    public void buyShares(TradeRequest tradeRequest) {
        try {
            SqlParameterSource params = new BeanPropertySqlParameterSource(tradeRequest);
            namedParameterJdbcTemplate.update(TransactionSQL.buyShares, params);
        } catch (Exception e) {
            System.out.println("Error updating buy" + e);
        }
    }

    public void buySharesAgain(TradeRequest tradeRequest) {
        try {
            SqlParameterSource params = new BeanPropertySqlParameterSource(tradeRequest);
            namedParameterJdbcTemplate.update(TransactionSQL.buySharesAgain, params);
        } catch (Exception e) {
            System.out.println("Error updating buy" + e);
        }
    }


    public void recordTransaction(Transaction transaction) {
        try {
            SqlParameterSource params = new BeanPropertySqlParameterSource(transaction);
            namedParameterJdbcTemplate.update(TransactionSQL.insertIntoTransactionHistory, params);
        } catch (Exception e) {
            System.out.println("ERROR " + e);
        }
    }

    public Integer getTransactionCountTotal() {
        return jdbcTemplate.queryForObject(TransactionSQL.getCountTotalTransactions, Integer.class);
    }


    public List<Transaction> getAllTransactions() {
        return namedParameterJdbcTemplate.query(TransactionSQL.getAllTransactions, new TransactionRowMapper());
    }

    public List<Transaction> getFilteredTransactions(List<SearchTag> tags) {
        MapSqlParameterSource inQueryParams = new MapSqlParameterSource();
        inQueryParams.addValue("tags", tags.stream().map(SearchTag::getSearchValue).collect(Collectors.toList()));
        return namedParameterJdbcTemplate.query(FilteredSearchUtility.buildSQL(), inQueryParams, new TransactionRowMapper());
    }

    public void deleteUsersTransactions(String fullName) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("fullName", fullName);
            namedParameterJdbcTemplate.update(TransactionSQL.deleteUsersTransactions, params);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Transaction> getLatest50Transactions() {
        return namedParameterJdbcTemplate.query(TransactionSQL.getLatest50Transactions, new TransactionRowMapper());
    }

    public List<Transaction> getTransactionsAfter50() {
        return namedParameterJdbcTemplate.query(TransactionSQL.getTransactions, new TransactionRowMapper());
    }
}
