package Borman.cbbbluechips.controllers.api;

import Borman.cbbbluechips.controllers.AuthenticatedController;
import Borman.cbbbluechips.models.TradeRequest;
import Borman.cbbbluechips.models.enums.TradeAction;
import Borman.cbbbluechips.models.responses.TeamExchangeDetailsResponse;
import Borman.cbbbluechips.services.OwnsService;
import Borman.cbbbluechips.services.TeamService;
import Borman.cbbbluechips.services.TradeCentralService;
import Borman.cbbbluechips.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TradeController extends AuthenticatedController {

    private final TradeCentralService tradeCentralService;
    private final OwnsService ownsService;
    private final TeamService teamService;
    private final TransactionService transactionService;

    public TradeController(TradeCentralService tradeCentralService, OwnsService ownsService, TeamService teamService, TransactionService transactionService) {
        this.tradeCentralService = tradeCentralService;
        this.ownsService = ownsService;
        this.teamService = teamService;
        this.transactionService = transactionService;
    }

    @GetMapping("/exchange-details/{team_Id}")
    public ResponseEntity<TeamExchangeDetailsResponse> teamExchangeDetailsResponse(@PathVariable("team_Id") String teamId) {
        String userId = retrieveLoggedInUserId();
        return ResponseEntity.ok(tradeCentralService.fillExchangeDetails(userId, teamId));
    }

    @PostMapping("/trade-action/Sell")
    public synchronized ResponseEntity<Void> sellTeam(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "volume") int volume) {
        String user = retrieveLoggedInUserId();
        TradeRequest tradeRequest = new TradeRequest(teamId, user, volume, TradeAction.SELL);
        if (ownsService.validateOwnership(tradeRequest) && teamService.isTeamUnLocked(tradeRequest.getTeamId()))
            transactionService.completeSell(tradeRequest);
        return ResponseEntity.ok().build();
    }

    //TODO verify
    @PostMapping("/trade-action/Buy")
    public synchronized ResponseEntity<Void> buyTeam(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "volume") int volume) {
        String user = retrieveLoggedInUserId();
        TradeRequest tradeRequest = new TradeRequest(teamId, user, volume, TradeAction.BUY);
        double fundsAvailable = ownsService.getFundsAvailable(tradeRequest);
        if (teamService.isTeamUnLocked(tradeRequest.getTeamId()))
            transactionService.buyStockInTeam(tradeRequest, fundsAvailable);
        return ResponseEntity.ok().build();
    }

//    //TODO verify
//    @RequestMapping("/trade/{team_Id}")
//    public String tradeCentral(@PathVariable("team_Id") String teamId, Model model) {
//        User user = userService.getUserLoggedIn(getLoggedInUserId());
//        model.addAttribute("user", user);
//        model.addAttribute("team", teamService.getTeamById(teamId));
//        model.addAttribute("details", tradeCentralService.fillTradeCentralDetails(user, teamId));
//        return "trade";
//    }
//

//
//    //TODO verify
//    //note: Concerns include selling teams that are already sold in another tab or buying too much same way.. by selling/buying alt way
//    @PostMapping("/trade/sell-all")
//    public synchronized ResponseEntity<String> sellAllInUsersPortfolio() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<Owns> owns = ownsService.getTeamsUserOwns(user.getID());
//        owns.forEach(team -> {
//            TradeRequest tradeRequest = new TradeRequest(team.getTeamId(), user.getID(), team.getAmountOwned(), TradeAction.SELL);
//            if (ownsService.validateOwnership(tradeRequest) && teamService.isTeamUnLocked(tradeRequest.getTeamId()))
//                transactionService.completeSell(tradeRequest);
//        });
//        return ResponseEntity.ok("OKAY");
//    }

}