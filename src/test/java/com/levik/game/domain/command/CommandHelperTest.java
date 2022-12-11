package com.levik.game.domain.command;

import com.levik.game.domain.model.Button;
import com.levik.game.domain.model.ClientResponse;
import com.levik.game.infra.configuration.GameConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CommandHelperTest {

    private static final String START_GAME_DETAILS = """
            Ever wonder what it would be like to be a professional Software Developer? Well, here is your chance. My name is Brian, and I've been a professional Software Developer for over 10 years now. I'm going to take you through some common situations that Software Developer's face. You'll have the opportunity to respond to each situation the way you feel it should be handled. The result of your choice may be positive, negative, or something in between.
                        
            Don't worry, no programming knowledge is required.
            """;

    private static final String MAIN_MENU = """
            Main Menu
                        
            Current Score: %s out of 15
            Tries: 0 out of 5
                        
            Choose which situation you would you like to explore and earn points:
            """;

    private Command testInstance;

    @BeforeEach
    void setUp() {
        Map<String, ClientResponse> gameStepStorage = new GameConfiguration().gameStepStorage();
        testInstance = new CommandHelper(gameStepStorage);
    }

    @DisplayName("Should get start game details when get start command")
    @Test
    void shouldGetStartGameDetailsWhenPerformStartCommand() {
        List<Button> buttons = List.of(new Button("/play", "Play Online"));

        ClientResponse clientResponse = testInstance.perform("/start", UUID.randomUUID().toString());

        assertThat(clientResponse.getBody()).isEqualTo(START_GAME_DETAILS);
        assertThat(clientResponse.getButtons()).isEqualTo(buttons);
    }

    @DisplayName("Should get menu details when get go command")
    @Test
    void shouldGetMenuWhenPerformStartCommand() {
        List<Button> buttons = List.of(
                new Button("/menu1", "Balance between quality and speed"),
                new Button("/menu2", "Unclear requirements"),
                new Button("/menu3", "Extracurricular programming"),
                new Button("/menu4", "Build or use"),
                new Button("/menu5", "Estimate time"),
                new Button("/menu6", "Defect found")
        );


        ClientResponse clientResponse = testInstance.perform("/go", UUID.randomUUID().toString());

        assertThat(clientResponse.getBody()).isEqualTo(MAIN_MENU);
        assertThat(clientResponse.getButtons()).isEqualTo(buttons);
    }
}