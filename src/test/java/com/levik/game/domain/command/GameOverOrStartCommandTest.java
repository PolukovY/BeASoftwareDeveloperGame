package com.levik.game.domain.command;

import com.levik.game.domain.model.Button;
import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.model.UserDetails;
import com.levik.game.domain.user.repository.UserRepository;
import com.levik.game.infra.configuration.GameConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameOverOrStartCommandTest {

    private Command testInstance;

    private Command baseCommand;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var gameConfiguration = new GameConfiguration();
        Map<String, UserDetails> userStorage = gameConfiguration.userStorage();
        baseCommand = mock(CommandHelper.class);

        userRepository = new UserRepository(userStorage);
        testInstance = new GameOverOrStartCommand(baseCommand, userRepository);
    }


    @DisplayName("Should reset user details when got start command")
    @Test
    void shouldRestUserDetailsInStorage() {
        String userIdentity = UUID.randomUUID().toString();

        UserDetails prevUserDetails = createBaseUserDetails(userIdentity);
        String start = "/start";

        when(baseCommand.perform(start, userIdentity)).thenReturn(new ClientResponse("some text", new ArrayList<>()));


        ClientResponse clientResponse = testInstance.perform(start, userIdentity);
        UserDetails currentUserDetails = userRepository.getUserDetails(userIdentity);

        assertThat(clientResponse).isNotNull();

        assertThat(currentUserDetails).isNotEqualTo(prevUserDetails);

        assertThat(currentUserDetails.getScore()).isEqualTo(0);
        assertThat(currentUserDetails.getCommandHistory().size()).isEqualTo(0);
        assertThat(currentUserDetails.getMaxScore()).isEqualTo(15);
        assertThat(currentUserDetails.getMaxMenu()).isEqualTo(6);
    }

    @DisplayName("Should not reset user details when got any menu answer command")
    @Test
    void shouldNotTriggerResetWhenPerformMenuAnswer() {
        String userIdentity = UUID.randomUUID().toString();

        UserDetails prevUserDetails = createBaseUserDetails(userIdentity);
        String command = "/menu1_answer2";
        List<Button> buttons = List.of(new Button("/go", "Back to Menu"));

        when(baseCommand.perform(command, userIdentity)).thenReturn(new ClientResponse("some text", buttons));


        ClientResponse clientResponse = testInstance.perform(command, userIdentity);
        UserDetails currentUserDetails = userRepository.getUserDetails(userIdentity);

        assertThat(clientResponse).isNotNull();

        assertThat(currentUserDetails).isEqualTo(prevUserDetails);

        assertThat(currentUserDetails.getScore()).isNotEqualTo(0);
        assertThat(currentUserDetails.getCommandHistory().size()).isNotEqualTo(0);
    }

    @DisplayName("Should not reset user details when got any menu answer command")
    @Test
    void shouldTriggerResetAndGameWinWhenPerformMenu() {
        String userIdentity = UUID.randomUUID().toString();

        UserDetails prevUserDetails = createBaseUserDetails(userIdentity);
        prevUserDetails.setScore(15);
        prevUserDetails.setCommandHistory(Set.of("/start", "/go", "/menu2", "/menu3", "/menu4", "/menu5", "/menu6", "/menu1",  "/menu7"));
        String command = "/menu1";
        List<Button> buttons = List.of(new Button("/go", "Back to Menu"));

        when(baseCommand.perform(command, userIdentity)).thenReturn(new ClientResponse("some text", buttons));


        ClientResponse clientResponse = testInstance.perform(command, userIdentity);
        UserDetails currentUserDetails = userRepository.getUserDetails(userIdentity);

        assertThat(clientResponse).isNotNull();
        assertThat(clientResponse.getBody()).isEqualTo("Win the game!!! Your score null");
        assertThat(clientResponse.getButtons()).isEqualTo(List.of(new Button("/start", "Do you want to play one more time?")));

        assertThat(currentUserDetails).isEqualTo(new UserDetails());

        assertThat(currentUserDetails.getScore()).isEqualTo(0);
        assertThat(currentUserDetails.getCommandHistory().size()).isEqualTo(0);
    }

    private UserDetails createBaseUserDetails(String userIdentity) {
        UserDetails userDetails = userRepository.getUserDetails(userIdentity);

        userDetails.setScore(3);
        userDetails.setCommandHistory(Set.of("/start", "/go"));

        userRepository.updateUserById(userIdentity, userDetails);

        return userDetails;
    }
}