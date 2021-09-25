package Borman.cbbbluechips.services;

import Borman.cbbbluechips.models.Matchup;
import Borman.cbbbluechips.models.Team;
import Borman.cbbbluechips.models.responses.MarketResponse;
import Borman.cbbbluechips.utilities.PriceHistoryUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;

@Service
public class MatchupService {

    Logger logger = LoggerFactory.getLogger(MatchupService.class);

    private final TeamService teamService;
    private final PriceHistoryService priceHistoryService;

    public MatchupService(TeamService teamService, PriceHistoryService priceHistoryService) {
        this.teamService = teamService;
        this.priceHistoryService = priceHistoryService;
    }

    public MarketResponse todaysMarket() {
        return todaysMarket(MAX_VALUE);
    }

    public MarketResponse upComingGames() {
        MarketResponse response = todaysMarket(3);
        //Remove games that's that have already been played
        response.setMatchups(response.getMatchups().stream()
                .filter(x -> x.getStartTimeDateTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList()));
        return response;
    }

    MarketResponse todaysMarket(int limitAmount) {

        List<Matchup> teamsPlayingToday = teamService.teamsPlayingToday()
                .stream()
                .filter(Team::isNextGameHome) //filters out only home teams
                .map(this::createMatchup)
                .sorted(Matchup::compareTo)
                .limit(limitAmount)
                .collect(Collectors.toList());

        return new MarketResponse(teamsPlayingToday);
    }


    private Matchup createMatchup(Team team) {
        Matchup matchup = new Matchup();

        //Set price history for homeTeam
        List<String>
                priceHistoryTeam1 = PriceHistoryUtility.apply(priceHistoryService.priceHistoryByTeamId(team.getTeamId()));
        team.setPriceHistory(priceHistoryTeam1);
        matchup.setTeam1(team);

        Team teamPlaying = teamService.getTeamByIdWithSharesOutstanding((team.getNextTeamPlaying()));
        //Set price history for AwayTeam
        List<String> priceHistoryTeam2 = PriceHistoryUtility.apply(priceHistoryService.priceHistoryByTeamId(teamPlaying.getTeamId()));
        teamPlaying.setPriceHistory(priceHistoryTeam2);
        matchup.setTeam2(teamPlaying);

        LocalDateTime startTime = teamService.getStartTimeByTeamId(team.getTeamId());

        matchup.setStartTimeDateTime(startTime);
        matchup.setStartTime(startTime.format(DateTimeFormatter.ofPattern("h:mm a")));
        return matchup;
    }

}