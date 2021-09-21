package Borman.cbbbluechips.config.cron;

import Borman.cbbbluechips.services.LeaderboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class CacheCallers {

    Logger logger = LoggerFactory.getLogger(CacheCallers.class);

    private final LeaderboardService leaderboardService;

    public CacheCallers(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
        logger.info("Cache Caller active");
    }

    @Scheduled(cron = "0 30 * ? * *") //Every 30 minutes
    public void updateNextTeamPlayingAndOdds() {
        leaderboardService.getLeaders();
    }

}