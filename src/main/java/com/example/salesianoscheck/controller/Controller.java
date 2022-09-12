package com.example.salesianoscheck.controller;

import com.example.salesianoscheck.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class Controller {

    @Value("${website.url}")
    String url;

    @Value("${website.user}")
    String user;

    @Value("${website.pass}")
    String pass;

    @Autowired
    private ScraperService scraperService;

    @GetMapping(path = "/check")
    public void check() throws IOException {
        /*
        var expression = CronExpression.parse("* 25 14 * * 1-2");
        var result = expression.next(LocalDateTime.now());
        System.out.println(result);
         */
        //scraperService.loginScraper(url,user,pass);
    }

}
