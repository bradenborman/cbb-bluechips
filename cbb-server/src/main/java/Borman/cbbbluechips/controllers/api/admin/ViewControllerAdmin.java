package Borman.cbbbluechips.controllers.api.admin;

import Borman.cbbbluechips.services.GameSettingsService;
import Borman.cbbbluechips.services.TeamService;
import Borman.cbbbluechips.services.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Deprecated
@Controller
@RequestMapping("/api")
public class ViewControllerAdmin {

    private Logger logger = LoggerFactory.getLogger(ViewControllerAdmin.class);

    TeamService teamService;
    GameSettingsService gameSettingsService;
    UserGroupService groupService;

    public ViewControllerAdmin(TeamService teamService, GameSettingsService gameSettingsService, UserGroupService groupService) {
        this.teamService = teamService;
        this.gameSettingsService = gameSettingsService;
        this.groupService = groupService;
    }


    @RequestMapping("/update/teams")
    public String updateTeams(@RequestParam(required = false) String teamId, Model model) {
        model.addAttribute("teams", teamService.getAllTeams(true));
        if(teamId != null) {
            logger.info("Looking to get Team for updating: {}", teamId);
            model.addAttribute("selectedTeam", teamService.getTeamById(teamId));
        }
        return "team_update";
    }

    @RequestMapping("/set/seeds")
    public String setSeeds(Model model) {
        model.addAttribute("allTeams", teamService.getAllTeams(false));
        return "set_seeds";
    }

    @RequestMapping("/set/pointspread")
    public String setPointSpread(Model model) {
        model.addAttribute("teams", gameSettingsService.getTeamsPlayingToday());
        return "set_pointspread";
    }

    @RequestMapping("/manage-groups")
    public String mangeUserGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "manage-groups";
    }


}