package com.levik.game.infra.telegram.service;

import com.levik.game.domain.model.Button;
import com.levik.game.domain.model.ClientResponse;
import com.levik.game.infra.Bot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@Service
public class TelegramSendMessageApi implements SendMessageApi {

    @Override
    public void send(Bot bot, ClientResponse clientResponse, Long sendTo) {
        log.info("Send message to chatId {} preparing...", sendTo);
        var sendMessage = sendInlineKeyBoardMessage(clientResponse.getBody(), clientResponse.getButtons(), sendTo);
        try {
            bot.execute(sendMessage);
            log.info("Send message to chatId {} completed with wait 1 sec", sendTo);
            //Added sleep to not reach limit
            TimeUnit.SECONDS.sleep(1);
        } catch (TelegramApiRequestException exe) {
            log.info("Can't send message {}, looks like user stop using bot, chatId {}",
                    exe.getMessage(), sendTo);
        } catch (Exception exe) {
            log.error("Can't send message to {}", sendTo, exe);
        }
    }

    private SendMessage sendInlineKeyBoardMessage(String text, List<Button> buttons, Long sendTo) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardButtonsRows = buttons
                .stream()
                .map(this::createKeyboardButtonsRow)
                .toList();

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return SendMessage.builder()
                .chatId(String.valueOf(sendTo))
                .text(text)
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    private List<InlineKeyboardButton> createKeyboardButtonsRow(Button button) {
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        var inlineKeyboardButton = createButton(button);
        keyboardButtonsRow.add(inlineKeyboardButton);
        return keyboardButtonsRow;
    }

    private InlineKeyboardButton createButton(Button button) {
        var inlineKeyboardButton = new InlineKeyboardButton();

        inlineKeyboardButton.setText(button.label());
        inlineKeyboardButton.setCallbackData(button.code());

        return inlineKeyboardButton;
    }
}
