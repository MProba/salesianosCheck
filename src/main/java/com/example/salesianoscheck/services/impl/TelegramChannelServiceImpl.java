package com.example.salesianoscheck.services.impl;

import com.example.salesianoscheck.services.TelegramChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Service
public class TelegramChannelServiceImpl implements TelegramChannelService {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramChannelServiceImpl.class);

    @Value("${BotTelegramSpring.apiKey}")
    String apiKey;

    @Value("${BotTelegramSpring.chatId}")
    String chatId;

    @Override
    public void sendMessageToChannel(String message) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        urlString = String.format(urlString,this.apiKey, this.chatId, message);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            // String response = sb.toString();
            // TODO: Capturar excepciones
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
