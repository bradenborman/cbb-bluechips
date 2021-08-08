package Borman.cbbbluechips.daos;

import Borman.cbbbluechips.daos.sql.PriceHistorySQL;
import Borman.cbbbluechips.mappers.PriceHistoryRowMapper;
import Borman.cbbbluechips.models.MarketValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PriceHistoryDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<MarketValue> fetchAllPriceHistory() {
        try {
            return namedParameterJdbcTemplate.query(PriceHistorySQL.SELECT_ALL_PRICE_HISTORY, new PriceHistoryRowMapper());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<MarketValue> setPriceHistoryByTeamId(String teamId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
            return namedParameterJdbcTemplate.query(PriceHistorySQL.SELECT_PRICE_HISTORY_BY_TEAM, params, new PriceHistoryRowMapper());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
