package Borman.cbbbluechips.daos;

import Borman.cbbbluechips.daos.sql.OwnsSQL;
import Borman.cbbbluechips.daos.sql.TeamSQL;
import Borman.cbbbluechips.mappers.TeamRowDetailedMapper;
import Borman.cbbbluechips.mappers.TeamRowMapper;
import Borman.cbbbluechips.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TeamDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Team> getAllTeams() {
        return jdbcTemplate.query(TeamSQL.getAllTeams, new TeamRowMapper());
    }

    public List<Team> onlyTeamsInTournament() {
        return jdbcTemplate.query(TeamSQL.getTournamentTeams, new TeamRowMapper());
    }

    public Team getTeamByIdWithSharesOutstanding(String teamId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
        return namedParameterJdbcTemplate.queryForObject(TeamSQL.getTeamByIdWithSharesOutstanding, params, new TeamRowDetailedMapper());
    }

    public Team getTeamById(String teamId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
        return namedParameterJdbcTemplate.queryForObject(TeamSQL.getTeamById, params, new TeamRowMapper());
    }

    public Team getTeamBySportsDataId(String sportsDataId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("sportsDataId", sportsDataId);
        return namedParameterJdbcTemplate.queryForObject(TeamSQL.getTeamBySportsDataId, params, new TeamRowMapper());
    }

    public double getCurrentMarketPrice(String teamId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
        return namedParameterJdbcTemplate.queryForObject(OwnsSQL.getCurrentMarketPrice, params, Double.class);
    }


    public String getTeamNameById(String teamId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
        return namedParameterJdbcTemplate.queryForObject(TeamSQL.getTeamName, params, String.class);
    }

    public Team getTeamByName(String teamName) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamName", teamName);
        return namedParameterJdbcTemplate.queryForObject(TeamSQL.getTeamByName, params, new TeamRowMapper());
    }

    public List<Team> getAllTeamsWithSharesOutstandingDetail() {
        return jdbcTemplate.query(TeamSQL.getAllTeamsWithSharesOutstandingDetail, new TeamRowDetailedMapper());
    }

    @Deprecated
    public String getSharesOutstandingForTeam(String teamId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
            String amount = namedParameterJdbcTemplate.queryForObject(TeamSQL.getSharesOutstanding, params, String.class);
            return amount != null ? amount : "0";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "0";
        }
    }


    public void updateNextTeamPlayingByTeamID(String teamToUpdateId, String teamPlayingId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("teamToUpdateId", teamToUpdateId)
                    .addValue("teamPlayingId", teamPlayingId); //My TEAM id not sports data

            namedParameterJdbcTemplate.update(TeamSQL.updateNextTeamPlaying, params);
        } catch (Exception e) {
            System.out.println("Failed to Update Next TeamPlaying");
        }
    }


    public String getTeamPlayingNext(String teamId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
            return namedParameterJdbcTemplate.queryForObject(TeamSQL.getTeamPlayingNext, params, String.class);
        } catch (Exception e) {
            System.out.println("Failed to getTeamPlayingNext");
            return "";
        }
    }

    public void resetNextTeamPlayingForAll() {
        jdbcTemplate.update(TeamSQL.resetNextTeamPlayingForAll);
    }

    public String getNextPointSpread(String teamId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
            String ps = namedParameterJdbcTemplate.queryForObject(TeamSQL.getNextPointSpread, params, String.class);
            return ps != null ? ps : "0";
        } catch (Exception e) {
            System.out.println("Failed to getNextPointSpread");
            return "0";
        }
    }


    public String getNameByShortName(String teamShortName) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamShortName", teamShortName);
            return namedParameterJdbcTemplate.queryForObject(TeamSQL.getNameByShortName, params, String.class);
        } catch (Exception e) {
            return null;
        }
    }


    public boolean isTeamLocked(String teamId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource().addValue("teamId", teamId);
            String result = namedParameterJdbcTemplate.queryForObject(TeamSQL.getLockedStatusByTeam, params, String.class);
            return "1".equals(result);
        } catch (Exception e) {
            return false;
        }
    }

    public List<Team> selectTeamsPlayingToday() {
        return namedParameterJdbcTemplate.query(TeamSQL.SELECT_TEAMS_PLAYING_NEXT_SET, new TeamRowDetailedMapper());
    }

}