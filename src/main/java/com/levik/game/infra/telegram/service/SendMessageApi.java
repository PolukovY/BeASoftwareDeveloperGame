package com.levik.game.infra.telegram.service;

import com.levik.game.domain.model.ClientResponse;
import com.levik.game.infra.Bot;

public interface SendMessageApi {

    void send(Bot bot, ClientResponse clientResponse, Long sendTo);
}
