package Borman.cbbbluechips.services;

import Borman.cbbbluechips.config.properties.GameRules;
import Borman.cbbbluechips.daos.UserDao;
import Borman.cbbbluechips.email.EmailService;
import Borman.cbbbluechips.models.User;
import Borman.cbbbluechips.models.exceptions.UserAlreadyExistsException;
import Borman.cbbbluechips.models.requests.CreateUserRequest;
import Borman.cbbbluechips.models.responses.PhoneNumberDetails;
import Borman.cbbbluechips.utilities.UserNameUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;
    private final EmailService emailService;
    private final int STARTING_CASH;
    private final boolean SIGNUP_ALLOWED;

    public UserService(UserDao userDao, GameRules gameRules, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
        this.STARTING_CASH = gameRules.getStartingCash();
        this.SIGNUP_ALLOWED = gameRules.isSignUpAllowed();
    }

    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    @Transactional
    public void createUser(CreateUserRequest request) {
        if (SIGNUP_ALLOWED) {

            if (isUserAlreadyPresent(request.getEmail())) {
                logger.info("{} already in database", request.getEmail());
                throw new UserAlreadyExistsException(request.getEmail());
            } else {

                User user = new User(
                        UserNameUtility.titleCaseConversion(request.getFirstName()),
                        UserNameUtility.titleCaseConversion(request.getLastName()),
                        request.getEmail(),
                        request.getPassword()
                );

                user.setCash(STARTING_CASH);
                String userId = userDao.createNewUser(user);
                user.setID(userId);//Do I need to set this still? Investigate
                emailService.sendTermsAndServices(user.getEmail());
            }
        }
    }


    public void deleteUser(String UserId) {
        userDao.deleteUser(UserId);
    }

    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    public double getUserCashById(String userId) {
        return getUserById(userId).getCash();
    }

    void addProceedsToUser(String userId, double moneyToAdd) {
        userDao.addCashToUser(userId, moneyToAdd);
    }


    void removeProceedsFromUser(String userId, double moneyToRemove) {
        userDao.removeCashFromUser(userId, moneyToRemove);
    }

    private boolean isUserAlreadyPresent(String email) {
        return userDao.countEmailAddressInDatabase(email) > 0;
    }

    String getUserFullName(String userId) {
        User user = userDao.getUserById(userId);
        return user != null ? String.format("%s %s", user.getFirstName(), user.getLastName()) : "No User Found.";
    }

    public User retrieveUserById(String id) {
        return userDao.retrieveUserById(id);
    }

    public String retrieveUserEmailById(String id) {
        return userDao.retrieveUserById(id).getEmail();
    }

    public void toggleTextAlertSubscription(boolean textStatus, String userIdLoggedIn) {
        logger.info("Toggle-ing text alerts: {}", textStatus);
        if (textStatus)
            userDao.subscribeUserToTextAlerts(userIdLoggedIn);
        else
            userDao.unSubscribeUserToTextAlerts(userIdLoggedIn);
    }

    public boolean updatePhoneNumber(String phoneNumber, String UserId) {
        logger.info("Request to change Phone Number: {} by user: {}", phoneNumber, UserId);
        userDao.updatePhoneNumber(phoneNumber, UserId);
        if ("".equals(phoneNumber))
            userDao.unSubscribeUserToTextAlerts(UserId);

        //TODO => update with validation. Will display red ! on front end if error

        return true;
    }

    public User getUserByEmail(String emailToRecover) {
        return userDao.getUserByEmail(emailToRecover);
    }

    public boolean hasUserDonated(String loggedInUserId) {
        logger.info("Checking to see if user {} donated", loggedInUserId);
        return userDao.hasUserDonated(loggedInUserId);
    }

    public void updatePlayerHasDonated(String userId) {
        logger.info("Awarding $50,000 to user: {}", userId);
        userDao.addCashToUser(userId, 50000);
        userDao.updateHasPlayerPaidTrue(userId);
    }

    public PhoneNumberDetails retrievePhoneNumberDetails(String userId) {
        PhoneNumberDetails x = new PhoneNumberDetails();
        x.setPhoneNumber(userDao.getUserPhoneNumber(userId));
        x.setSubscribedToMessages(userDao.doesUserSubscribeToTextAlerts(userId));
        return x;
    }
}