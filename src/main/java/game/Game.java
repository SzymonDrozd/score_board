package game;

import exception.IncorrectGameValueException;

import java.util.Optional;

public class Game extends AbstractGame {

    public Game() {
        super();
    }

    public Game(String homeTeam, String awayTeam) {
        super(homeTeam, awayTeam);
    }

    @Override
    public synchronized void updateScore(Integer newHomeTeamScore, Integer newAwayTeamScore) {
        Optional.ofNullable(newHomeTeamScore).ifPresent(value -> {
            if (value < 0) {
                throw new IncorrectGameValueException("Home Team score value is out of range. Value: " + value);
            }

            this.homeTeamScore = value;
        });

        Optional.ofNullable(newAwayTeamScore).ifPresent(value -> {
            if (value < 0) {
                throw new IncorrectGameValueException("Away Team score value is out of range. Value: " + value);
            }

            this.awayTeamScore = value;
        });
    }
}
