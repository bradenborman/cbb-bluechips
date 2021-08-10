package Borman.cbbbluechips.models.requests;

public class UpdateMarketPriceRequest {

    private String teamId;
    private String nextRoundPrice;
    private String nextRound;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getNextRoundPrice() {
        return nextRoundPrice;
    }

    public void setNextRoundPrice(String nextRoundPrice) {
        this.nextRoundPrice = nextRoundPrice;
    }

    public String getNextRound() {
        return nextRound;
    }

    public void setNextRound(String nextRound) {
        this.nextRound = nextRound;
    }

}