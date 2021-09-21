package Borman.cbbbluechips.services;

import Borman.cbbbluechips.daos.MatchupDao;
import Borman.cbbbluechips.models.Matchup;
import Borman.cbbbluechips.models.Team;
import Borman.cbbbluechips.models.responses.MarketResponse;
import Borman.cbbbluechips.utilities.PriceHistoryUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchupService {

    Logger logger = LoggerFactory.getLogger(MatchupService.class);

    MatchupDao matchupDao;
    TeamService teamService;
    PriceHistoryService priceHistoryService;

    public MatchupService(MatchupDao matchupDao, TeamService teamService, PriceHistoryService priceHistoryService) {
        this.matchupDao = matchupDao;
        this.teamService = teamService;
        this.priceHistoryService = priceHistoryService;
    }

    public MarketResponse todaysMarket() {
        List<Matchup> teamsPlayingToday = teamService.teamsPlayingToday(LocalDate.now())
                .stream()
                .filter(Team::isNextGameHome) //filters out only home teams
                .map(this::createMatchup)
                .sorted(Matchup::compareTo)
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