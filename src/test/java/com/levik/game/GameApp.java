package com.levik.game;

import com.levik.game.infra.configuration.GameConfiguration;
import com.levik.game.domain.GameCommandManager;
import com.levik.game.domain.command.*;
import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.repository.UserRepository;
import com.levik.game.domain.user.model.UserDetails;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class GameApp {

    @Test
    public void poc() {
        String userId = "test";

        var gameConfiguration = new GameConfiguration();
        Map<String, ClientResponse> gameStepStorage = gameConfiguration.gameStepStorage();
        Map<String, Integer> scoreStorage = gameConfiguration.scoreStorage();
        Map<String, UserDetails> userStorage = gameConfiguration.userStorage();
        UserRepository userRepository = new UserRepository(userStorage);

        Command commandHelper = new CommandHelper(gameStepStorage);
        Command scoreCalculator = new ScoreCalculator(commandHelper, scoreStorage, userRepository);
        Command menuCommand = new MenuCommand(scoreCalculator, userRepository);

        Command gameOverCommand = new GameOverOrStartCommand(menuCommand, userRepository);

        var gameCommandManager = new GameCommandManager(gameOverCommand);

        ClientResponse clientResponse = gameCommandManager.handle("/start", userId);
        System.out.println(clientResponse);
        System.out.println("--------------------------------------------------------------");

        clientResponse = gameCommandManager.handle("/play", userId);
        System.out.println(clientResponse);
        System.out.println("--------------------------------------------------------------");

        clientResponse = gameCommandManager.handle("/go", userId);
        System.out.println(clientResponse);
        System.out.println("--------------------------------------------------------------");

        clientResponse = gameCommandManager.handle("/menu1", userId);
        System.out.println(clientResponse);
        System.out.println("--------------------------------------------------------------");

        clientResponse = gameCommandManager.handle("/menu1_answer3", userId);
        System.out.println(clientResponse);
        System.out.println("--------------------------------------------------------------");

        clientResponse = gameCommandManager.handle("/go", userId);
        System.out.println(clientResponse);
        System.out.println("--------------------------------------------------------------");

    }
}
