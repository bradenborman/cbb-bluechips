package Borman.cbbbluechips.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="paypal")
public class PaypalConfig {

    Logger logger = LoggerFactory.getLogger(PaypalConfig.class);

    private String clientId;
    private double donationAmount;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getPayPalUrl() {
        return  "https://www.paypal.com/sdk/js?client-id=${client_id}&disable-funding=credit,card".replace("${client_id}", clientId);
    }

}