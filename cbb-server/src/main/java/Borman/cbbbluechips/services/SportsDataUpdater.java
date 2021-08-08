package Borman.cbbbluechips.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SportsDataUpdater {

    private final Logger logger = LoggerFactory.getLogger(SportsDataUpdater.class);

    SportsDataApiService sportsDataApiService;
    boolean shouldMakeApiCall;

    public SportsDataUpdater(SportsDataApiService sportsDataApiService, @Qualifier("make_api_call") boolean shouldMakeApiCall) {
        this.sportsDataApiService = sportsDataApiService;
        this.shouldMakeApiCall = shouldMakeApiCall;
    }

//    @Scheduled(cron = "0/30 * * * * ?")
//    public void updateNextTeamPlayingAndOddsQuick() {
//        logger.info("Scheduled task hit: updateTeamsPlayingToday.");
//        sportsDataApiService.updateTeamsPlayingToday();
//    }

//    @Scheduled(cron = "0/30 * * * * ?")
//    public void callTeamsFromSportsDataApi() {
//        logger.info("Scheduled task hit: callTeamsFromSportsDataApi.");
//        List<SportsDataTeam> teams = sportsDataApiService.callTeamsFromSportsDataApi();
//        teams.forEach(team -> {logger.info("School Display Name: {}", team.getShortDisplayName());});
//    }


//    @Scheduled(cron = "0/30 * * * * ?")
    @Scheduled(cron = "0 0 5 * * ?") //Every day at 5am
    public void updateNextTeamPlayingAndOdds() {
        logger.info("Scheduled task hit: updateTeamsPlayingToday.");
        if(shouldMakeApiCall) {
            sportsDataApiService.updateTeamsPlayingToday();
//            sportsDataApiService.createMatchUps();
        }
        else
            logger.info("ENV VAR Make_Api_Call set to false");
    }


}