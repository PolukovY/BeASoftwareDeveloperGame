package com.levik.game.domain.command;

import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.model.UserDetails;
import com.levik.game.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@AllArgsConstructor
@Slf4j
public class GameOverOrStartCommand implements Command {

    private static final String MENU_PATTERN = "^/menu[0-9]+";

    private static final String START_PREFIX = "/start";
    private final Command commandHelper;

    private final UserRepository userRepository;

    @Override
    public ClientResponse perform(String command, String userIdentity) {
        if (command.startsWith(START_PREFIX)) {
            userRepository.resetByUserId(userIdentity);
            return commandHelper.perform(command, userIdentity);
        }

        UserDetails userDetails = userRepository.getUserDetails(userIdentity);

        var clientResponse = commandHelper.perform(command, userIdentity);

        Set<String> commandHistory = userDetails.getCommandHistory();

        boolean isNumberMenuReached = commandHistory.stream()
                .filter(it -> Pattern.matches(MENU_PATTERN, it))
                .count() > userDetails.getMaxMenu();

        if (isNumberMenuReached || clientResponse.getButtons().isEmpty()) {
            int currentScore = userDetails.getScore();
            if (currentScore < userDetails.getMaxScore()) {
                clientResponse.gameOver();
            } else {
                clientResponse.win();
            }

            userRepository.resetByUserId(userIdentity);

            return clientResponse;
        }

        return clientResponse;
    }
}
