package Borman.cbbbluechips.controllers.api.admin;

import Borman.cbbbluechips.controllers.AuthenticatedController;
import Borman.cbbbluechips.models.Team;
import Borman.cbbbluechips.models.UpdateSeedRequest;
import Borman.cbbbluechips.models.requests.UpdateMarketPriceRequest;
import Borman.cbbbluechips.models.responses.GameSettingsResponse;
import Borman.cbbbluechips.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
public class AdminController extends AuthenticatedController {

    AdminService adminService;
    GameSettingsService settingsService;
    UserService userService;
    UserGroupService userGroupService;
    TransactionService transactionService;
    OwnsService ownsService;
    TeamService teamService;

    public AdminController(AdminService adminService, GameSettingsService settingsService,
                           UserService userService, UserGroupService userGroupService,
                           TransactionService transactionService, OwnsService ownsService, TeamService teamService) {
        this.adminService = adminService;
        this.settingsService = settingsService;
        this.userService = userService;
        this.userGroupService = userGroupService;
        this.transactionService = transactionService;
        this.ownsService = ownsService;
        this.teamService = teamService;
    }

    @GetMapping("/role-check")
    public ResponseEntity<Boolean> adminRoleCheck() {
        boolean isAdmin = retrieveLoggedInUser().getAuthorities()
                .stream().anyMatch(x -> x.getAuthority().equals("CBB_ADMIN"));
        return ResponseEntity.ok(isAdmin);
    }

    //TODO add signup allowed to response and database from property
    @GetMapping("/game-settings")
    public ResponseEntity<GameSettingsResponse> gameSettings() {
        return ResponseEntity.ok(settingsService.gameSettings());
    }

    @GetMapping("/all-teams")
    public ResponseEntity<List<Team>> allTeams() {
        return ResponseEntity.ok(teamService.getAllTeams(false));
    }

    @GetMapping("/teams-playing-today")
    public ResponseEntity<List<Team>> teamsPlayingToday() {
        return ResponseEntity.ok(settingsService.getTeamsPlayingToday());
    }

    @PostMapping("/update-seed")
    public ResponseEntity<Void> updateSeeds(@RequestBody UpdateSeedRequest updateSeedRequest) {
        adminService.processUpdateSeedRequest(updateSeedRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-current-round")
    public ResponseEntity<Void> updateRound(@RequestParam(value = "round") String round) {
        settingsService.updateRound(round);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update-price")
    public ResponseEntity<Void> updateMarketPrice(@RequestBody UpdateMarketPriceRequest updateMarketPriceRequest) {
        adminService.updateMarketPrice(updateMarketPriceRequest);
        return ResponseEntity.ok().build();
    }


//
//    @PostMapping("/update-locked")
//    public String updateLocked(@RequestParam(value = "teamName") String teamName, @RequestParam(value = "isEliminated", defaultValue = "false") boolean isEliminated,
//                               @RequestParam(value = "isLocked", defaultValue = "false") boolean isLocked) {
//        adminService.updateLockedAndEliminated(teamName, isEliminated, isLocked);
//        return "redirect:/admin/update/teams";
//    }
//
//    @PostMapping("/update-pointspread")
//    public String updatePointSpread(@RequestParam(value = "teamName") String[] teamName, @RequestParam(value = "nextPointSpread") String[] nextPointSpread) {
//        adminService.processUpdatePointSpreadRequest(Arrays.asList(teamName), Arrays.asList(nextPointSpread));
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/delete-group")
//    public ResponseEntity<String> deleteGroup(@RequestBody DeleteGroupRequest deleteGroupRequest) {
//        userGroupService.deleteGroup(deleteGroupRequest.getGroupId());
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping("/resetGame")
//    public String resetGame() {
//        settingsService.resetGame();
//        return "redirect:/admin";
//    }

//    //TODO clean up -- dont use where cluse --> just delete whole table data
//    @PostMapping("/deletePlayers")
//    public String deletePlayers() {
//        userService.getAllUsers().forEach(user -> {
//            System.out.println("Deleting all items for user: " + user.getID());
//            userGroupService.deleteUserFromAllGroups(user.getID());
//            transactionService.deleteUser(user.getID());
//            ownsService.deleteUser(user.getID());
//            userService.deleteUser(user.getID());
//        });
//
//        return "redirect:/users/logout";
//    }

}