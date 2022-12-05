package com.levik.game.domain.command;

import com.levik.game.domain.model.ClientResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
@AllArgsConstructor
public class CommandHelper implements Command {

    private final Map<String, ClientResponse> gameStepStorage;

    public ClientResponse perform(String command, String userIdentity) {
        return gameStepStorage.get(command).clone();
    }
}
