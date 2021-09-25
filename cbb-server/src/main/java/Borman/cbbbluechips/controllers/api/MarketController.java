package Borman.cbbbluechips.controllers.api;

import Borman.cbbbluechips.controllers.AuthenticatedController;
import Borman.cbbbluechips.models.responses.MarketResponse;
import Borman.cbbbluechips.services.MatchupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MarketController extends AuthenticatedController {

    MatchupService matchupService;

    public MarketController(MatchupService matchupService) {
        this.matchupService = matchupService;
    }

    @GetMapping("/market")
    public ResponseEntity<MarketResponse> market() {
        return ResponseEntity.ok(matchupService.todaysMarket());
    }

    @GetMapping("/upcoming-games")
    public ResponseEntity<MarketResponse> upComingGames() {
        return ResponseEntity.ok(matchupService.upComingGames());
    }

}