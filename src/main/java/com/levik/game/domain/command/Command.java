package com.levik.game.domain.command;

import com.levik.game.domain.model.ClientResponse;

public interface Command {

    ClientResponse perform(String command, String userId);
}
