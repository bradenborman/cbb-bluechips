package Borman.cbbbluechips.services;

import Borman.cbbbluechips.config.properties.GameRules;
import Borman.cbbbluechips.daos.GameSettingsDao;
import Borman.cbbbluechips.models.responses.GameSettingsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class GameSettingsService {

    private final Logger logger = LoggerFactory.getLogger(GameSettingsService.class);

    private final GameSettingsDao settingsDao;
    private final int STARTING_CASH;

    public GameSettingsService(GameSettingsDao settingsDao, GameRules gameRules) {
        this.settingsDao = settingsDao;
        this.STARTING_CASH = gameRules.getStartingCash();
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

    public String selectLastPriceChange() {
        String lastUpdateTime = settingsDao.selectLastPriceChange();
        //parse to date so there is some integrity
        LocalDateTime.parse(lastUpdateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a"));
        return lastUpdateTime;
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
        settingsDao.updateAllUsersCashBackToStartingCash(STARTING_CASH);
        settingsDao.resetLockedAndIsOutStatus();
        updateRound("64");
        logger.info("~~ REQUEST TO RESET GAME COMPLETED ~~");
    }

}
