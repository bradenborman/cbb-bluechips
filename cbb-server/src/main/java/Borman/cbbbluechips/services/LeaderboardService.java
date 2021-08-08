package Borman.cbbbluechips.services;

import Borman.cbbbluechips.models.LeaderBoardUser;
import Borman.cbbbluechips.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LeaderboardService {

    Logger logger = LoggerFactory.getLogger(LeaderboardService.class);

    OwnsService ownsService;
    UserGroupService userGroupService;
    UserService userService;

    public LeaderboardService(OwnsService ownsService, UserGroupService userGroupService,
                              UserService userService) {
        this.ownsService = ownsService;
        this.userGroupService = userGroupService;
        this.userService = userService;
    }

    //TODO replace with call to DB to get the last updated time. Make new table/col
    public List<LeaderBoardUser> getLeaders() {
        LocalDateTime startTimeOfMethod = LocalDateTime.now();
        LocalDateTime lastPriceChange = LocalDateTime.of(2021, 6, 5, 10, 15, 10);
        String lastPriceChangeStr = lastPriceChange.format(DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a"));
        logger.info("Getting leaderboard for last price change of: {}", lastPriceChangeStr);
        List<LeaderBoardUser> results = ownsService.retrieveLeaderboard(lastPriceChangeStr);
        logger.info("Done getting leaderboard in: {} milli seconds", Duration.between(startTimeOfMethod, LocalDateTime.now()).toMillis());
        return results;
    }

    public int getUsersLeaderPosition(User user) {
        return getLeaders()
                .stream()
                .filter(entry -> entry.getEmailAddress().equals(user.getEmail()))
                .findFirst()
                .orElse(new LeaderBoardUser("", -1, 0, "", false)).getRanking();
    }

    //TODO takes forever -- might look into making this straight up sql
    public int getUsersLeaderPosition(String userId) {
        String userEmail = userService.retrieveUserEmailById(userId);
        return getLeaders()
                .stream()
                .filter(entry -> entry.getEmailAddress().equals(userEmail))
                .findFirst()
                .orElse(new LeaderBoardUser("", -1, 0, "", false)).getRanking();
    }

    public List<LeaderBoardUser> fetchLeaderBoardDetailsForGroup(String groupId) {
        List<User> playersId = userGroupService.getUsersInGroup(groupId);
        playersId.forEach(player -> player.setCash(ownsService.getPortfolioValue(player.getID()) + ownsService.getFundsAvailable(player.getID())));
        playersId.sort(Comparator.comparing(User::getCash).reversed());
        List<LeaderBoardUser> leaders = new ArrayList<>();
        int i = 1, playersIdSize = playersId.size();
        while (i <= playersIdSize) {
            User user = playersId.get(i - 1);
            leaders.add(new LeaderBoardUser(user.getFirstName() + " " + user.getLastName(), i, user.getCash(), user.getEmail(), user.isHasPayedEntryFee()));
            i++;
        }
        return leaders;
    }
}