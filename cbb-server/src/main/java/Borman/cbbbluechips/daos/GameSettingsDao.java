package Borman.cbbbluechips.daos;

import Borman.cbbbluechips.config.properties.GameRules;
import Borman.cbbbluechips.daos.sql.GameSettingsSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameSettingsDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    GameRules gameRules;

    public String getCurrentRound() {
        return jdbcTemplate.queryForObject(GameSettingsSQL.getCurrentRound, String.class);
    }

    public String selectLastPriceChange() {
        return jdbcTemplate.queryForObject(GameSettingsSQL.SELECT_LAST_PRICE_CHANGE, String.class);
    }


    public void updateCurrentRound(String round) {
        String sql = "UPDATE game_info SET Current_Round = " + round + " WHERE Year = '2019';";
        jdbcTemplate.update(sql);
    }

    public void deleteAllTransactionFromGame() {
        String sql = "DELETE FROM transaction_history WHERE Transaction_ID > 0";
        jdbcTemplate.update(sql);
    }

    public void deleteAllPriceHistoryFromGame() {
        String sql = "DELETE FROM price_history where Price_ID > 0";
        jdbcTemplate.update(sql);
    }

    public void resetAllTeamsBackToStartingPrice() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startingSharePrice", gameRules.getStartingPricePerShare());
        String sql = "UPDATE teams SET `Current_Market_Price` =  :startingSharePrice where Team_ID > 0";
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteAllFromOwnsTable() {
        String sql = "DELETE FROM owns where Owns_ID > 0";
        jdbcTemplate.update(sql);
    }

    public void updateAllUsersCashBackToStartingCash(int startingCash) {
        String sql = "UPDATE user set Cash = '100000' where User_ID > 0";
        jdbcTemplate.update(sql);
    }

    public void resetLockedAndIsOutStatus() {
        String sql = "UPDATE teams SET Is_Out = false, Is_Locked = false";
        jdbcTemplate.update(sql);
    }
}
