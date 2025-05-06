package game;


import exception.IncorrectGameValueException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Setter
@Getter
public class Game implements Comparable<Game> {

    private String homeTeam;
    @Setter(AccessLevel.NONE)
    private volatile int homeTeamScore;

    private String awayTeam;
    @Setter(AccessLevel.NONE)
    private volatile int awayTeamScore;

    private LocalDateTime startDateTime;
    private LocalDateTime stopDateTime;

    public Game() {
        startDateTime = LocalDateTime.now();
    }

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

    @Builder(builderMethodName = "gameBuilder")
    public static Game of(String homeTeam, String awayTeam) {
        Game game = new Game();
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);

        return game;
    }

    @Override
    public int compareTo(Game o) {
        int sumOfPoints = this.homeTeamScore + this.awayTeamScore;
        int sumOfComparedPoints = o.getHomeTeamScore() + o.getAwayTeamScore();

        if(sumOfPoints < sumOfComparedPoints) {
            return 1;
        }

        if(sumOfPoints == sumOfComparedPoints) {
            return this.startDateTime.compareTo(o.startDateTime);
        }

        return -1;
    }
}
