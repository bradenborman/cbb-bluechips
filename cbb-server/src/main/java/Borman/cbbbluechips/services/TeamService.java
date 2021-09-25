package Borman.cbbbluechips.services;

import Borman.cbbbluechips.config.properties.GameRules;
import Borman.cbbbluechips.daos.TeamDao;
import Borman.cbbbluechips.email.EmailService;
import Borman.cbbbluechips.models.MarketValue;
import Borman.cbbbluechips.models.Team;
import Borman.cbbbluechips.models.exceptions.TeamLockedOnTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TeamService {

    Logger logger = LoggerFactory.getLogger(TeamService.class);

    TeamDao teamDao;
    PriceHistoryService priceHistoryService;
    EmailService emailService;
    GameRules gameRules;

    public TeamService(TeamDao teamDao, PriceHistoryService priceHistoryService, EmailService emailService, GameRules gameRules) {
        this.teamDao = teamDao;
        this.priceHistoryService = priceHistoryService;
        this.emailService = emailService;
        this.gameRules = gameRules;
    }

    //TODO - look into pulling apart and then joining with sql
    public List<Team> getAllTeams(boolean onlyTeamsInTournament) {
        List<Team> allTeams = onlyTeamsInTournament ? teamDao.getAllTeamsWithSharesOutstandingDetail() : teamDao.getAllTeams();

//        if(TeamDataValidator.anyTeamsMissingPointSpread(allTeams))
//            emailService.sendSetPointSpreadReminderEmail();

        //TODO decide if team history is needed for this if its just admin using it
        if (onlyTeamsInTournament) {
            List<MarketValue> historicalData = priceHistoryService.fetchAllPriceHistory();
            allTeams.forEach(team -> applyTeamPriceHistory(team, historicalData));
        }

        return allTeams;
    }

    private void applyTeamPriceHistory(Team team, List<MarketValue> historicalData) {
        //Limit down to just team's data
        List<MarketValue> onlySelectedTeam = historicalData.stream()
                .filter(marketValue -> marketValue.getTeamId().equals(team.getTeamId()))
                .collect(Collectors.toList());

        String priceHistoryString = fetchHistoryDetails(team, onlySelectedTeam);

        team.setPriceHistoryString(priceHistoryString);
    }

    private String fetchHistoryDetails(Team team, List<MarketValue> onlySelectedTeam) {
        LinkedHashMap<String, String> priceMap = new LinkedHashMap<>();
        priceMap.put("64", String.valueOf(gameRules.getStartingPricePerShare()));
        List<String> rounds = Arrays.asList("32", "16", "8", "4", "2", "1");
        rounds.forEach(round -> {
            Optional<MarketValue> matchedValueForRound = onlySelectedTeam.stream()
                    .filter(marketValue -> marketValue.getRoundId().equals(round))
                    .findFirst();

            matchedValueForRound.ifPresent(marketValue -> priceMap.put(round, String.valueOf(marketValue.getPrice())));
        });
        return String.join(" ", priceMap.values());
    }


    public Team getTeamByIdWithSharesOutstanding(String teamId) {
        return teamDao.getTeamByIdWithSharesOutstanding(teamId);
    }

    public Team getTeamById(String teamId) {
        return teamDao.getTeamById(teamId);
    }


    public boolean isTeamUnLocked(String teamId) {
        if (teamDao.isTeamLocked(teamId))
            throw new TeamLockedOnTransactionException(teamId);
        return true;
    }

    public List<Team> teamsPlayingToday() {
        //Selects with shares outstanding
        logger.debug("Querying for teams playing today..");
        List<Team> list = new ArrayList<>();
        List<Team> homeTeams = teamDao.getTeamsPlayingTodayHomeTeam();
        List<Team> awayTeams = teamDao.getTeamsPlayingTodayAwayTeam();

        logger.debug("Games today: {}", homeTeams.size());

        if (homeTeams.size() != awayTeams.size())
            logger.warn("Numbers do not match for teams playing today. Not same number as home({}) and away({}) teams", homeTeams.size(), awayTeams.size());

        list.addAll(homeTeams);
        list.addAll(awayTeams);
        return list;
    }

    public LocalDateTime getStartTimeByTeamId(String teamId) {
        Optional<String> startTimeStr = teamDao.getStartTimeByTeamId(teamId);
        logger.debug("Game starting at: {}", startTimeStr);

        return startTimeStr
                .map(s -> {
                    LocalDate localDate = LocalDate.now();
                    String date = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    return LocalDateTime.parse(date + " " + s, DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a"));
                })
                .orElseGet(LocalDateTime::now);
    }
}