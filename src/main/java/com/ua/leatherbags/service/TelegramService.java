package com.ua.leatherbags.service;

import com.ua.leatherbags.telegramBot.TelegramMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramService {

    @Value("${bot.chatId}")
    private Long chatId;

    private final TelegramMessageSender telegramMessageSender;


    public void sendMessage(String message) { //поміняти на об'єкт і сформувати повідомлення
        telegramMessageSender.sendMessage(chatId,message);
    }
}
