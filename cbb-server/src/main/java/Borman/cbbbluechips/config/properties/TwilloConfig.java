package Borman.cbbbluechips.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="twilio")
public class TwilloConfig {

    private String auth;
    private String accountSsd;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAccountSsd() {
        return accountSsd;
    }

    public void setAccountSsd(String accountSsd) {
        this.accountSsd = accountSsd;
    }

}