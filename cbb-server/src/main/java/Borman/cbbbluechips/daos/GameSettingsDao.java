package Borman.cbbbluechips.daos;

import Borman.cbbbluechips.config.GameRules;
import Borman.cbbbluechips.daos.sql.GameSettingsSQL;
import Borman.cbbbluechips.mappers.TeamRowMapper;
import Borman.cbbbluechips.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void updateCurrentRound(String round) {
        String sql = "UPDATE game_info SET Current_Round = " + round + " WHERE Year = '2019';";
        jdbcTemplate.update(sql);
    }


    public List<Team> getTeamsPlayingTodayHomeTeam() {
        String sql = "SELECT * FROM teams WHERE Next_Team_Playing is not null AND seed > 0;";
        return jdbcTemplate.query(sql, new TeamRowMapper());
    }

    public List<Team> getTeamsPlayingTodayAwayTeam() {
        List<String> awayIds = jdbcTemplate.queryForList("SELECT Next_Team_Playing FROM teams WHERE Next_Team_Playing is not null AND seed > 0;", String.class);

        String sql = "SELECT * FROM teams WHERE Team_ID in (" + String.join(", ",awayIds) + ") AND seed > 0;";
        return jdbcTemplate.query(sql, new TeamRowMapper());
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
