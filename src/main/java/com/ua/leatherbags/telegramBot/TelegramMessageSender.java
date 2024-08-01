package com.ua.leatherbags.telegramBot;

import com.ua.leatherbags.service.BagService;
import com.ua.leatherbags.telegramBot.config.BotConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class TelegramMessageSender extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final BagService bagService;

    private final Map<Long, Integer> lastMessageId = new ConcurrentHashMap<>();

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            if (update.hasCallbackQuery()) {
                try {
                    Long chatId = update.getCallbackQuery().getMessage().getChatId();
                    Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

                    removeInlineButtons(chatId, messageId);

                    String callbackData = update.getCallbackQuery().getData();
                    String[] dataParts = callbackData.split(":");
                    String action = dataParts[0];
                    Long bagId = Long.parseLong(dataParts[1]);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chatId));

                    if ("confirm".equals(action)) {
                        sendMessage.setText("Замовлення " + bagId + " успішно підтверджено");
                        bagService.proceedOrder(bagId);
                    } else if ("cancel".equals(action)) {
                        sendMessage.setText("Замовлення " + bagId + " відмінено");
                        bagService.cancelOrder(bagId);
                    }

                    execute(sendMessage);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(Long chatId, String textToSend, Long bagId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton confirmButton = new InlineKeyboardButton();
        confirmButton.setText("Замовлення підтверджено");
        confirmButton.setCallbackData("confirm:" + bagId);

        InlineKeyboardButton cancelButton = new InlineKeyboardButton();
        cancelButton.setText("Замовлення відмінено");
        cancelButton.setCallbackData("cancel:" + bagId);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(confirmButton);
        keyboardButtonsRow1.add(cancelButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
            lastMessageId.put(chatId, sendMessage.getMessageThreadId());
        } catch (TelegramApiException e) {
            e.fillInStackTrace();
        }
    }

    private void removeInlineButtons(Long chatId, Integer messageId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(String.valueOf(chatId));
        editMessageReplyMarkup.setMessageId(messageId);

        InlineKeyboardMarkup emptyMarkup = new InlineKeyboardMarkup();
        emptyMarkup.setKeyboard(new ArrayList<>());

        editMessageReplyMarkup.setReplyMarkup(emptyMarkup);

        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
