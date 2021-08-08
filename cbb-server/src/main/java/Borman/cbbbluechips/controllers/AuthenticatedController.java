package Borman.cbbbluechips.controllers;

import Borman.cbbbluechips.models.User;
import Borman.cbbbluechips.models.exceptions.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AuthenticatedController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticatedController.class);

    protected String retrieveLoggedInUserId() {
        return retrieveLoggedInUser().getID();
    }

    protected User retrieveLoggedInUser() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new UserNotLoggedInException("Failed to cast user. More than likely not logged in");
        }
    }

}