package com.example.salesianoscheck.services.impl;

import com.example.salesianoscheck.services.ScraperService;
import com.example.salesianoscheck.utils.Constants;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ScraperServiceImpl implements ScraperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperServiceImpl.class);

    @Override
    public void loginScraper(String loginUrl, String user, String pass, String checkValue) {
        LOGGER.info("Inicializando rexistro");
        Playwright playwright = Playwright.create();
        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
        BrowserContext newContext = browser.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
        setLoginCredentials(newContext,loginUrl,user,pass,checkValue);
    }

    private void setLoginCredentials(BrowserContext browserContext, String loginUrl, String user, String pass, String checkValue){
        try {
            Page page = browserContext.newPage();
            page.navigate(loginUrl);
            page.type(Constants.USERNAME_TAG, user);
            page.type(Constants.PASSWORD_TAG, pass);
            page.locator(Constants.LOGIN_BUTTON).click();
            page.locator(Constants.CHECK_OPTION_BUTTON).click();
            page.locator(Constants.SELECTOR_TAG).selectOption(checkValue);
            //page.locator(Constants.CHECK_BUTTON).click();
            LOGGER.info("Rexistro realizado con éxito");
        }catch(TimeoutError ex){
            LOGGER.error("Produciuse un erro de timeout timeout. {}",ex.getMessage());
            throw new TimeoutError(ex.getMessage());
        }
    }


}
