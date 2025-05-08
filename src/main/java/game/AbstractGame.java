package game;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractGame implements Comparable<AbstractGame>   {
    protected String homeTeam;
    protected volatile int homeTeamScore;

    protected String awayTeam;
    protected volatile int awayTeamScore;

    protected LocalDateTime startDateTime;
    protected LocalDateTime stopDateTime;

    public AbstractGame() {
        this.startDateTime = LocalDateTime.now();
    }

    public AbstractGame(String homeTeam, String awayTeam) {
        this.startDateTime = LocalDateTime.now();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public abstract void updateScore(Integer newHomeTeamScore, Integer newAwayTeamScore);

    public void finishGame() {
        this.stopDateTime = LocalDateTime.now();
    }

    @Override
    public int compareTo(AbstractGame o) {
        int sumOfPoints = this.homeTeamScore + this.awayTeamScore;
        int sumOfComparedPoints = o.getHomeTeamScore() + o.getAwayTeamScore();

        if (sumOfPoints < sumOfComparedPoints) {
            return 1;
        }

        if (sumOfPoints == sumOfComparedPoints) {
            return this.startDateTime.compareTo(o.startDateTime);
        }

        return -1;
    }
}
