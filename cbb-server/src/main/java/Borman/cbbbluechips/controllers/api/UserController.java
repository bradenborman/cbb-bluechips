package Borman.cbbbluechips.controllers.api;

import Borman.cbbbluechips.controllers.AuthenticatedController;
import Borman.cbbbluechips.email.EmailService;
import Borman.cbbbluechips.models.paypal.PaypalDonationRequest;
import Borman.cbbbluechips.models.requests.CreateUserRequest;
import Borman.cbbbluechips.models.responses.PhoneNumberDetails;
import Borman.cbbbluechips.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class UserController extends AuthenticatedController {

    final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final EmailService emailService;
    private final OwnsService ownsService;
    private final TransactionService transactionService;
    private final UserGroupService userGroupService;
    private final PasswordRecoveringService passwordRecoveringService;

    public UserController(UserService userService, EmailService emailService, OwnsService ownsService,
                          TransactionService transactionService, UserGroupService userGroupService,
                          PasswordRecoveringService passwordRecoveringService) {
        this.userService = userService;
        this.emailService = emailService;
        this.ownsService = ownsService;
        this.transactionService = transactionService;
        this.userGroupService = userGroupService;
        this.passwordRecoveringService = passwordRecoveringService;
    }

    @PostMapping("/create-user")
    public synchronized ResponseEntity<Void> createUser(@RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-user")
    ResponseEntity<Void> deleteUser() {
        String userId = retrieveLoggedInUserId();
        userGroupService.deleteUserFromAllGroups(userId);
        transactionService.deleteUser(userId);
        ownsService.deleteUser(userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-phone-number-details")
    public ResponseEntity<PhoneNumberDetails> phoneNumberDetails() {
        String userId = retrieveLoggedInUserId();
        return ResponseEntity.ok(userService.retrievePhoneNumberDetails(userId));
    }

    @PostMapping("/update-phone-number")
    public ResponseEntity<Void> updatePhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber) {
        boolean success = userService.updatePhoneNumber(phoneNumber, retrieveLoggedInUserId());
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/update-text-alert-status")
    public ResponseEntity<Void> updateTextAlert(@RequestParam(value = "textStatus") boolean textStatus) {
        userService.toggleTextAlertSubscription(textStatus, retrieveLoggedInUserId());
        return ResponseEntity.ok().build();
    }

    //TODO -> need to make db table
    @PostMapping("/update-point-spead-alert-status")
    public ResponseEntity<Void> updatePointSpreadWarning(@RequestParam(value = "sendAlerts") boolean sendAlerts) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/paypal-transaction-complete")
    ResponseEntity<Boolean> payEntryFee(@RequestBody PaypalDonationRequest paypalDonationRequest) {
        logger.info("Paid endpoint hit. PayEntryFeeRequest: {}", paypalDonationRequest.toString());
//        userService.updatePlayerHasDonated(retrieveLoggedInUserId());
//        emailService.sendUpdateEmail(paypalDonationRequest);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/recover-email")
    public ResponseEntity<String> forgotPassword(@RequestParam(value = "emailToRecover") String emailToRecover) {
        return ResponseEntity.ok(passwordRecoveringService.getUsersPassword(emailToRecover));
    }


}