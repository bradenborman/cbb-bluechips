package Borman.cbbbluechips.config;

import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class CCBConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean("make_api_call")
    public boolean getMakeApiCall() {
        return Boolean.parseBoolean(System.getenv("Make_Api_Call"));
    }

    @Bean
    public CaffeineCacheManager getCaffeineCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCacheNames(Arrays.asList("recentTransactions", "leaderboard", "ui-auto-lock"));
        manager.setCacheSpecification("maximumSize=500,expireAfterAccess=30m");
        return manager;
    }

}