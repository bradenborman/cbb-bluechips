package Borman.cbbbluechips.services;

import Borman.cbbbluechips.daos.GameSettingsDao;
import Borman.cbbbluechips.daos.TeamDao;
import Borman.cbbbluechips.models.responses.GameSettingsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
public class GameSettingsService {

    private Logger logger = LoggerFactory.getLogger(GameSettingsService.class);

    private GameSettingsDao settingsDao;
    private TeamDao teamDao;
    private int startingCash;

    public GameSettingsService(GameSettingsDao settingsDao, TeamDao teamDao, @Qualifier("startingCash") int startingCash) {
        this.settingsDao = settingsDao;
        this.teamDao = teamDao;
        this.startingCash = startingCash;
    }


    public GameSettingsResponse gameSettings() {
        GameSettingsResponse response = new GameSettingsResponse();
        response.setCurrentRound(getCurrentRound());
        response.setSignUpAllowed(true);
        return response;
    }

    public String getCurrentRound() {
        return settingsDao.getCurrentRound();
    }

    public void updateRound(String round) {
        Stream<String> validRounds = Stream.of("64", "32", "16", "8", "4", "2", "1");
        if (validRounds.anyMatch(x -> x.equals(round)))
            settingsDao.updateCurrentRound(round);
    }

    @Transactional
    public void resetGame() {
        logger.info("~~ REQUEST TO RESET GAME ~~");
        settingsDao.deleteAllTransactionFromGame();
        settingsDao.deleteAllPriceHistoryFromGame();
        settingsDao.resetAllTeamsBackToStartingPrice();
        settingsDao.deleteAllFromOwnsTable();
        settingsDao.updateAllUsersCashBackToStartingCash(startingCash);
        settingsDao.resetLockedAndIsOutStatus();
        updateRound("64");
        logger.info("~~ REQUEST TO RESET GAME COMPLETED ~~");
    }

}
