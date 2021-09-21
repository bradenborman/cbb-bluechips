package Borman.cbbbluechips.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

public class Team {

    private String teamId;
    private String teamName;
    private boolean eliminated;
    private boolean locked;
    private String seed;
    @Deprecated
    private String logoULR;
    private LocalDateTime nextGameTime;
    private String nextTeamPlaying;
    private String pointSpread;
    private double currentMarketPrice;
    private String sharesOutstanding;

    private boolean isNextGameHome;

    @Deprecated
    private String priceHistoryString;
    private boolean doesUserOwn;

    private List<String> priceHistory;


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getLogoULR() {
        return logoULR;
    }

    public void setLogoULR(String logoULR) {
        this.logoULR = logoULR;
    }

    public LocalDateTime getNextGameTime() {
        return nextGameTime;
    }

    public void setNextGameTime(LocalDateTime nextGameTime) {
        this.nextGameTime = nextGameTime;
    }

    public String getNextTeamPlaying() {
        return nextTeamPlaying;
    }

    public void setNextTeamPlaying(String nextTeamPlaying) {
        this.nextTeamPlaying = nextTeamPlaying;
    }

    public String getPointSpread() {
        return pointSpread;
    }

    public void setPointSpread(String pointSpread) {
        this.pointSpread = pointSpread;
    }

    public double getCurrentMarketPrice() {
        return currentMarketPrice;
    }

    public void setCurrentMarketPrice(double currentMarketPrice) {
        this.currentMarketPrice = currentMarketPrice;
    }


    public String getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(String sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public boolean isDoesUserOwn() {
        return doesUserOwn;
    }

    public void setDoesUserOwn(boolean doesUserOwn) {
        this.doesUserOwn = doesUserOwn;
    }

    public boolean isNextGameHome() {
        return isNextGameHome;
    }

    public void setNextGameHome(boolean nextGameHome) {
        isNextGameHome = nextGameHome;
    }

    public String getPriceHistoryString() {
        return priceHistoryString;
    }

    public void setPriceHistoryString(String priceHistoryString) {
        this.priceHistoryString = priceHistoryString;
    }

    public List<String> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<String> priceHistory) {
        this.priceHistory = priceHistory;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write Team as string", e);
        }
    }

}
