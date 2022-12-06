package com.levik.game.domain.command;

import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.model.UserDetails;
import com.levik.game.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Slf4j
public class GameOverCommand implements Command {

    private static final List<String> MENU_ITEMS = Arrays.asList(
            "/menu1", "/menu2", "/menu3", "/menu4", "/menu5", "/menu6"
    );
    private final Command commandHelper;

    private final UserRepository userRepository;

    @Override
    public ClientResponse perform(String command, String userIdentity) {
        UserDetails userDetails = userRepository.getUserDetails(userIdentity);

        var clientResponse = commandHelper.perform(command, userIdentity);

        Set<String> commandHistory = userDetails.getCommandHistory();

        boolean isNumberMenuReached = commandHistory.stream()
                .filter(MENU_ITEMS::contains)
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
