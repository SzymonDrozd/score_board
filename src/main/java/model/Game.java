package model;


import exception.IncorrectGameValueException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Setter
@Getter
public class Game {

    private String homeTeam;
    private int homeTeamScore;

    private String awayTeam;
    private int awayTeamScore;

    private LocalDateTime startDateTime;
    private LocalDateTime stopDateTime;

    public Game() {
        startDateTime = LocalDateTime.now();
    }

    public void updateScore(Integer newHomeTeamScore, Integer newAwayTeamScore) {
        Optional.ofNullable(newHomeTeamScore).ifPresent(value -> {
            if(value < 0) {
                throw new IncorrectGameValueException("Home Team score value is out of range. Value: " + value);
            }

            setHomeTeamScore(value);
        });

        Optional.ofNullable(newAwayTeamScore).ifPresent(value -> {
            if(value < 0) {
                throw new IncorrectGameValueException("Away Team score value is out of range. Value: " + value);
            }

            setAwayTeamScore(value);
        });
    }

    @Builder(builderMethodName = "gameBuilder")
    public static Game of(String homeTeam, String awayTeam) {
        Game game = new Game();
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);

        return game;
    }
}
