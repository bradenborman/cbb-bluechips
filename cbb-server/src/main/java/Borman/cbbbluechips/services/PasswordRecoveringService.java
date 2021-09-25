package Borman.cbbbluechips.services;

import Borman.cbbbluechips.email.EmailService;
import Borman.cbbbluechips.models.User;
import Borman.cbbbluechips.zdata.PasswordRecoveryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveringService {

    Logger logger = LoggerFactory.getLogger(PasswordRecoveringService.class);

    private UserService userService;
    private EmailService emailService;

    public PasswordRecoveringService(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    public String getUsersPassword(String emailToRecover) {
        logger.info("Attempting to recover password for: " + emailToRecover);
        User user = userService.getUserByEmail(emailToRecover);

        if (user != null && user.getPassword() != null)
            return emailService.sendPasswordRecoveryEmail(emailToRecover, user.getPassword())
                    ? PasswordRecoveryData.GENERIC_SUCCESS_MESSAGE : PasswordRecoveryData.GENERIC_FAILED_MESSAGE;
        else
            return PasswordRecoveryData.GENERIC_FAILED_MESSAGE;
    }

}
