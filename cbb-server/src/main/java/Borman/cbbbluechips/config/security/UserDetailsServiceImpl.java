package Borman.cbbbluechips.config.security;

import Borman.cbbbluechips.config.properties.GameRules;
import Borman.cbbbluechips.daos.UserDao;
import Borman.cbbbluechips.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserDao userDao;
    private final List<String> admins;

    public UserDetailsServiceImpl(UserDao userDao, GameRules gameRules) {
        this.userDao = userDao;
        this.admins = gameRules.getAdmins();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.info("Looking up user {}", email);

        User user = userDao.getUserByEmail(email.trim());

        if (user == null)
            throw new UsernameNotFoundException(email);
        else
            logger.info("User Found: {}", user.getFirstName());

        user.addAuthority("CBB_USER");

        if (admins.contains(user.getUsername()))
            user.addAuthority("CBB_ADMIN");

        return user;
    }
}