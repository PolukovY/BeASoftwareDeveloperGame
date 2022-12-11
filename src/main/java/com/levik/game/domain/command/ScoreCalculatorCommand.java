package com.levik.game.domain.command;

import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@AllArgsConstructor
public class ScoreCalculatorCommand implements Command {
    private static final int ZERO = 0;
    private final Command commandHelper;

    private final Map<String, Integer> scoreStorage;
    private final UserRepository userRepository;

    @Override
    public ClientResponse perform(String command, String userIdentity) {
        var userDetails = userRepository.getUserDetails(userIdentity);
        int score = scoreStorage.getOrDefault(command, ZERO);

        var clientResponse = commandHelper.perform(command, userIdentity);

        userDetails.setScore(userDetails.getScore() + score);
        clientResponse.setScore(userDetails.getScore());

        userRepository.updateUserById(userIdentity, userDetails);
        clientResponse.setBody(String.format(clientResponse.getBody(), clientResponse.getScore()));
        return clientResponse;
    }
}
