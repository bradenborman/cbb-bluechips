package Borman.cbbbluechips.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="game-rules")
public class GameRules {

    private int startingCash;
    private int startingPricePerShare;
    private boolean signUpAllowed;
    private List<String> admins;
    private Map<String, String> payoutMap;

    public int getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(int startingCash) {
        this.startingCash = startingCash;
    }

    public int getStartingPricePerShare() {
        return startingPricePerShare;
    }

    public void setStartingPricePerShare(int startingPricePerShare) {
        this.startingPricePerShare = startingPricePerShare;
    }

    public boolean isSignUpAllowed() {
        return signUpAllowed;
    }

    public void setSignUpAllowed(boolean signUpAllowed) {
        this.signUpAllowed = signUpAllowed;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public Map<String, String> getPayoutMap() {
        return payoutMap;
    }

    public void setPayoutMap(Map<String, String> payoutMap) {
        this.payoutMap = payoutMap;
    }

}