package com.levik.game.domain.user.repository;

import com.levik.game.domain.user.model.UserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@AllArgsConstructor
public class UserRepository {

    private final Map<String, UserDetails> userStorage;

    public UserDetails getUserDetails(String id) {
        return userStorage.getOrDefault(id, new UserDetails());
    }

    public void updateUserById(String id, UserDetails userDetails) {
        userStorage.put(id, userDetails);
    }

    public void resetByUserId(String id) {
        userStorage.put(id, new UserDetails());
    }
}
