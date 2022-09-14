package com.example.salesianoscheck.scheduledTask;


import com.example.salesianoscheck.models.dto.CronDTO;
import com.example.salesianoscheck.models.service.CronService;
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

    @Value("${min.delay}")
    Double minDelay;

    @Value("${max.delay}")
    Double maxDelay;

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private CronService cronService;

    @Scheduled(cron = "* 30 8 * * 2-3", zone = "Europe/Madrid")
    public void scheduleTaskCheckIn() {
        try{
            CronDTO cronDTO = cronService.getCronById(Constants.ID_1);
            if(cronDTO.getStatus()==1){
                Long delay = Math.round((ThreadLocalRandom.current().nextDouble(minDelay,maxDelay)) * 1000);
                generateDelay(delay);
                scraperService.loginScraper(url,user,pass,Constants.SELECTOR_OPTION_IN);
            }
        } catch(Exception ex){
            LOG.error("Erro consultado o estado do Cron para o checkIn", ex.getMessage());
        }
    }

    @Scheduled(cron = "* 27 14 * * 2-3", zone = "Europe/Madrid")
    public void scheduleTaskCheckOut() {
        LOG.info("Entrando no Cron de checkOut");
        try{
            CronDTO cronDTO = cronService.getCronById(Constants.ID_2);
            if(cronDTO.getStatus()==1){
                LOG.info("Entrando no Cron de checkOut");
                Long delay = Math.round((ThreadLocalRandom.current().nextDouble(minDelay,maxDelay)) * 1000);
                LOG.info("Producindo delay de: {}",delay);
                generateDelay(delay);
                scraperService.loginScraper(url,user,pass,Constants.SELECTOR_OPTION_OUT);
            }
        } catch(Exception ex){
            LOG.error("Erro consultado o estado do Cron para o checkOut", ex.getMessage());
        }
    }

    private void generateDelay(Long delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ie) {
            LOG.error("Erro xerando delay", ie.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
