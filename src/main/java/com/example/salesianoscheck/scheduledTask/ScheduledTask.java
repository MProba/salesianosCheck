package com.example.salesianoscheck.scheduledTask;


import com.example.salesianoscheck.services.ScraperService;
import com.example.salesianoscheck.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@EnableScheduling
public class ScheduledTask {

    private final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);

    @Value("${website.url}")
    String url;

    @Value("${website.user}")
    String user;

    @Value("${website.pass}")
    String pass;

    @Autowired
    private ScraperService scraperService;

    @Scheduled(cron = "* 30 8 * * 1-2", zone = "Europe/Madrid")
    public void scheduleTaskCheckIn() {
        generateDelay();
        scraperService.loginScraper(url,user,pass,Constants.SELECTOR_OPTION_IN);
    }

    @Scheduled(cron = "* 27 14 * * 1-2", zone = "Europe/Madrid")
    public void scheduleTaskCheckOut() {
        generateDelay();
        scraperService.loginScraper(url,user,pass,Constants.SELECTOR_OPTION_OUT);
    }

    private void generateDelay(){
        try {
            Thread.sleep((long)(ThreadLocalRandom.current().nextDouble(0,7)) * 1000);
        } catch (InterruptedException ie) {
            LOG.error("Error xerando delay");
            Thread.currentThread().interrupt();
        }
    }

}
