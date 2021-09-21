package Borman.cbbbluechips.models.responses;

import Borman.cbbbluechips.models.Matchup;

import java.util.List;

public class MarketResponse {

    List<Matchup> matchups;

    public MarketResponse(List<Matchup> matchups) {
        this.matchups = matchups;
    }

    public List<Matchup> getMatchups() {
        return matchups;
    }

    public void setMatchups(List<Matchup> matchups) {
        this.matchups = matchups;
    }

}