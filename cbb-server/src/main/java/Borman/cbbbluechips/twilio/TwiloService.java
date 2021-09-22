package Borman.cbbbluechips.twilio;

import Borman.cbbbluechips.config.properties.TwilloConfig;
import Borman.cbbbluechips.models.MarketValue;
import Borman.cbbbluechips.models.SMS_Alert;
import Borman.cbbbluechips.services.OwnsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwiloService {

    private Logger logger = LoggerFactory.getLogger(TwiloService.class);
    private final String APPLICATION_PHONE_NUMBER = "15732791590";

    private OwnsService ownsService;
    private final String ACCOUNT_SID;
    private final String AUTH_TOKEN;

    public TwiloService(OwnsService ownsService, TwilloConfig twilloConfig) {
        this.ownsService = ownsService;
        this.ACCOUNT_SID = twilloConfig.getAccountSsd();
        this.AUTH_TOKEN = twilloConfig.getAuth();
    }

    public void sendPriceChangeAlert(MarketValue marketValue) {
        List<SMS_Alert> textsToSend = ownsService.getUsersWhoOwnedTeamWithTextAlertOn(marketValue.getTeamId());
        textsToSend.forEach(text -> {
                    if (!"".equals(text.getPhoneNumber()) || text.getPhoneNumber() != null)
                        sendMessage(text.getPhoneNumber(), TwiloBodyBuilderUtility.buildGameCompletedMessage(marketValue, text.getAmountOwned()));
                }
        );
    }

    public void sendMessage(String phoneNumber, String Body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(APPLICATION_PHONE_NUMBER), Body).create();
        logger.info("Text Message Sent");
    }


}
