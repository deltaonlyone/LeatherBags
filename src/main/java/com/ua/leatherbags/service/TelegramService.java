package com.ua.leatherbags.service;

import com.ua.leatherbags.data.Bag;
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


    public void sendMessage(Bag bag) {
        String message = "Замовлення №" + bag.getId() + "\n" +
                "ПІБ: " + bag.getFirstName() + " " + bag.getLastName() + "\n" +
                "Тел: " + bag.getPhoneNum() + "\n" +
                "Тип: " + bag.getType() + "\n" +
                "Ключниця: " + (bag.isKeyHolder() ? "Є" : "Нема") + "\n" +
                "Розмір: " + bag.getSize() + "\n" +
                "Колір: " + bag.getColor() + "\n" +
                "Місто: " + bag.getCity() + "\n" +
                "Відділення нової пошти: " + bag.getDepartment() +
                "Ціна: " + bag.getPrice() + "\n";
        telegramMessageSender.sendMessage(chatId, message, bag.getId());
    }
}
