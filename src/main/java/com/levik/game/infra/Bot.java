package com.levik.game.infra;

import com.levik.game.infra.telegram.TelegramManager;
import com.levik.game.infra.telegram.properties.BotProperties;
import com.levik.game.infra.telegram.service.SendMessageApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final TelegramManager telegramManager;
    private final SendMessageApi sendMessageApi;

    @PostConstruct
    public void onInit() {
        log.info("TelegramBotsApi username {} configured...", getBotUsername());
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received message {}", update);
        var action = telegramManager.handle(update);
        var message = update.getMessage();
        Long sendTo = Objects.nonNull(message) ? message.getChatId() : update.getCallbackQuery().getMessage().getChatId();
        sendMessageApi.send(this, action, sendTo);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }
}
