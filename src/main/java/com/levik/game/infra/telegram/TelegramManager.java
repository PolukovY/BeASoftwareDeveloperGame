package com.levik.game.infra.telegram;

import com.levik.game.domain.GameCommandManager;
import com.levik.game.domain.model.ClientResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramManager {

    private final GameCommandManager gameCommandManager;


    public ClientResponse handle(Update update) {
        String userId = getClientIdentity(update);

        if (update.hasCallbackQuery()) {
            var command = update.getCallbackQuery().getData();
            return gameCommandManager.handle(command, userId);
        }

        var command = update.getMessage().getText();
        return gameCommandManager.handle(command, userId);
    }

    private String getClientIdentity(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getUserName();
        }


        return update.getCallbackQuery().getMessage().getChat().getUserName();
    }
}
