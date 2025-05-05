package game;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Game implements Comparable<Game> {

    private String homeTeam;
    private volatile int homeTeamScore;

    private String awayTeam;
    private volatile int awayTeamScore;

    private LocalDateTime startDateTime;
    private LocalDateTime stopDateTime;

    public Game() {
        startDateTime = LocalDateTime.now();
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
