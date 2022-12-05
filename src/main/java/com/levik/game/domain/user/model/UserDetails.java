package com.levik.game.domain.user.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDetails {
    private int score;
    private Set<String> commandHistory = new HashSet<>();

    private int maxMenu = 6;

    private int maxScore = 15;

    public void addCommand(String command) {
        commandHistory.add(command);
    }
}
