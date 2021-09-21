package Borman.cbbbluechips.mappers;

import Borman.cbbbluechips.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRowDetailedMapper implements RowMapper<Team> {

    //Sets shares outstanding with SQL rather than calling in loop


    @Override
    public Team mapRow(ResultSet rs, int rownumber) throws SQLException {
        Team team =new Team();
        team.setTeamId(rs.getString("Team_ID"));
        team.setTeamName(rs.getString("name"));
        team.setSeed(rs.getString("seed"));
        team.setLocked(rs.getBoolean("Is_Locked"));
        team.setEliminated(rs.getBoolean("is_out"));
        team.setLogoULR(rs.getString("Logo_URL"));
        team.setCurrentMarketPrice(rs.getDouble("Current_Market_Price"));
        team.setNextTeamPlaying(rs.getString("Next_Team_Playing"));
        team.setPointSpread(rs.getString("Point_Spread"));

        team.setNextGameHome(rs.getBoolean("Next_Game_Home_Game"));

        String sharesOutstanding = rs.getString("Amount_Owned");
        team.setSharesOutstanding(sharesOutstanding != null ? sharesOutstanding : "0");

        return team;
    }
}
