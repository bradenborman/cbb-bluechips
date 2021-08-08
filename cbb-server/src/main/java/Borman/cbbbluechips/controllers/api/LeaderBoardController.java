package Borman.cbbbluechips.controllers.api;

import Borman.cbbbluechips.controllers.AuthenticatedController;
import Borman.cbbbluechips.models.LeaderBoardUser;
import Borman.cbbbluechips.services.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class LeaderBoardController extends AuthenticatedController {

    LeaderboardService leaderboardService;

    public LeaderBoardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderBoardUser>> leaderboard() {
        return ResponseEntity.ok(leaderboardService.getLeaders());
    }

}