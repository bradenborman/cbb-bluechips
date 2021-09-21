package Borman.cbbbluechips.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CCBConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean("startingCash")
    public int getStartingCash(@Value("${game-rules.starting-cash}") int startingCash) {
        return startingCash;
    }

    @Bean("admins")
    public List<String> getAdmins(@Value("${admins}") String[] admins) {
        return Arrays.asList(admins);
    }

    @Bean("make_api_call")
    public boolean getMakeApiCall() {
        return Boolean.parseBoolean(System.getenv("Make_Api_Call"));
    }

    @Bean("displayAds")
    public boolean getDisplayAds() {
        return Boolean.parseBoolean(System.getenv("Display_Ads"));
    }

    @Bean("signUpAllowed")
    public boolean getSignUpAllowed() {
        return Boolean.parseBoolean(System.getenv("signUpAllowed"));
    }

    @Bean
    public CaffeineCacheManager getCaffeineCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCacheNames(Arrays.asList("recentTransactions", "leaderboard", "teamsPlayingToday"));
        manager.setCacheSpecification("maximumSize=500,expireAfterAccess=30m");
        return manager;
    }

}