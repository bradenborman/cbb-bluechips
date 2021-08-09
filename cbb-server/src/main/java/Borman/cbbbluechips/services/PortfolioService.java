package Borman.cbbbluechips.services;

import Borman.cbbbluechips.models.GameData;
import Borman.cbbbluechips.models.UserGameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    private OwnsService ownsService;
    private GameSettingsService gameSettingsService;
    private TransactionService transactionService;
    private UserService userService;
    private LeaderboardService leaderboardService;

    public PortfolioService(OwnsService ownsService, GameSettingsService gameSettingsService,
                            TransactionService transactionService, UserService userService, LeaderboardService leaderboardService) {
        this.ownsService = ownsService;
        this.gameSettingsService = gameSettingsService;
        this.transactionService = transactionService;
        this.userService = userService;
        this.leaderboardService = leaderboardService;
    }

    public GameData retrieveGameData() {
        GameData gameData = new GameData();
        gameData.setTotalMoneyInPlay(ownsService.getTotalMoneyInPlay());
        gameData.setCurrentRound(gameSettingsService.getCurrentRound());
        gameData.setTotalTransactionsCount(transactionService.retrieveTransactionCount());
        return gameData;
    }

    public UserGameData retrieveUsersGameData(String userId) {
        UserGameData x = new UserGameData();
//        logger.info("Getting users net-worth");
        x.setNetWorth(ownsService.retrieveUserNetWorthById(userId));

//        logger.info("Getting users cash");
        x.setCash(userService.getUserCashById(userId));

//        logger.info("Getting users full-name");
        x.setFullName(userService.getUserFullName(userId));

//        logger.info("Getting users leaderboard pos");
//        x.setLeaderboardPosition(leaderboardService.getUsersLeaderPosition(userId));
        x.setLeaderboardPosition(1000);

//        logger.info("Getting users transaction count");
        x.setUserTransactionCount(transactionService.transactionCountByUser(userId));

        return x;
    }

}
