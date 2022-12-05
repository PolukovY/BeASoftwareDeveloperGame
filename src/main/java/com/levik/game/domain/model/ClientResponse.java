package com.levik.game.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class ClientResponse {

    private String body;
    private List<Button> buttons;
    private Integer score;
    private boolean isGameOver;
    private boolean isWin;

    public ClientResponse(String body, List<Button> buttons) {
        this.body = body;
        this.buttons = buttons;
    }

    public void gameOver() {
       setBody("Game over! Try next time... Your score " + getScore());
       setGameOver(true);
       setButtons(List.of(new Button("/start", "Do you want to play one more time?")));
    }

    public void win() {
        setWin(true);
        setBody("Win the game!!! Your score " + getScore());
        setButtons(List.of(new Button("/start", "Do you want to play one more time?")));
    }

    public ClientResponse clone() {
        var clientResponse = new ClientResponse(this.getBody(), new ArrayList<>(this.getButtons()));
        clientResponse.setGameOver(this.isGameOver());
        clientResponse.setScore(this.getScore());
        clientResponse.setGameOver(this.isWin());
        return clientResponse;
    }
}
