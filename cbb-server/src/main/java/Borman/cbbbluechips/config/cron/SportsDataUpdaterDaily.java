package Borman.cbbbluechips.config.cron;

import Borman.cbbbluechips.services.SportsDataApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sport-data-api", name = "update", havingValue = "daily")
public class SportsDataUpdaterDaily {

    private final Logger logger = LoggerFactory.getLogger(SportsDataUpdaterDaily.class);

    SportsDataApiService sportsDataApiService;
    boolean shouldMakeApiCall;

    public SportsDataUpdaterDaily(SportsDataApiService sportsDataApiService, @Qualifier("make_api_call") boolean shouldMakeApiCall) {
        this.sportsDataApiService = sportsDataApiService;
        this.shouldMakeApiCall = shouldMakeApiCall;
        logger.info("Making call at 5am daily to update Teams playing today.");
    }

    @Scheduled(cron = "0 0 5 * * ?") //Every day at 5am
    public void updateNextTeamPlayingAndOdds() {
        logger.info("Scheduled task hit: updateTeamsPlayingToday.");
        if(shouldMakeApiCall) sportsDataApiService.updateTeamsPlayingToday();
        else logger.info("ENV VAR Make_Api_Call set to false");
    }

}