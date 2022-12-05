package com.levik.game.domain;

import com.levik.game.domain.command.Command;
import com.levik.game.domain.model.ClientResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GameCommandManager {

    private final Command commandHelper;

    public ClientResponse handle(String command, String userIdentity) {
        return commandHelper.perform(command, userIdentity);
    }
}
