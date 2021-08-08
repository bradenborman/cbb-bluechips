package Borman.cbbbluechips.mappers;

import Borman.cbbbluechips.models.Matchup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchupRowMapper implements RowMapper<Matchup> {

    @Override
    public Matchup mapRow(ResultSet rs, int rowNum) throws SQLException {
        Matchup matchup = new Matchup();
//        matchup.setMatchupId(rs.getString("Matchup_ID"));
//        matchup.setTeamOneId(rs.getString("TEAM1_ID"));
//        matchup.setTeamTwoId(rs.getString("TEAM2_ID"));
//        matchup.setMatchupDate(rs.getString("Matchup_Date"));
//        matchup.setMatchupStartTime(rs.getString("Matchup_StartTime"));
        return matchup;
    }

}