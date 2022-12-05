package com.levik.game.domain.command;

import com.levik.game.domain.model.Button;
import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.model.UserDetails;
import com.levik.game.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Slf4j
public class MenuCommand implements Command {

    public static final String MENU_PREFIX = "/menu";
    private final Command commandHelper;
    private final UserRepository userRepository;

    @Override
    public ClientResponse perform(String command, String userIdentity) {
        var userDetails = userRepository.getUserDetails(userIdentity);
        Set<String> commandHistory = userDetails.getCommandHistory();

        var clientResponse = commandHelper.perform(command, userIdentity);

        List<Button> filteredButtons = new ArrayList<>(clientResponse.getButtons().size());
        for (Button button :  clientResponse.getButtons()) {
            if (button.code().startsWith(MENU_PREFIX)) {
                if (!commandHistory.contains(button.code())) {
                    filteredButtons.add(button);
                }
            } else {
                filteredButtons.add(button);
            }
        }

        clientResponse.setButtons(filteredButtons);

        userDetails.addCommand(command);
        return clientResponse;
    }
}
