package board;

import exception.DuplicateGameException;
import model.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ScoreBoard {

    private final Map<String, Game> games;

    public ScoreBoard() {
        games = Collections.synchronizedMap(new HashMap<>());
    }

    public void startGame(String homeTeam, String awayTeam) {
        String key = getGameKey(homeTeam, awayTeam);

        Optional.ofNullable(games.get(key)).ifPresent(value -> {
            throw new DuplicateGameException("Game already exists. Key: " + value);
        });

        Game game = Game.gameBuilder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .build();

        games.put(key, game);
    }

    public void updateScore(String homeTeam, String awayTeam, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        updateScoreByKey(getGameKey(homeTeam, awayTeam), newHomeTeamScore, newAwayTeamScore);
    }

    public void updateScoreByKey(String key, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        Optional.ofNullable(findGameByKey(key))
                .ifPresent(game -> game.updateScore(newHomeTeamScore, newAwayTeamScore));

    }

    public String summary() {
        synchronized (games) {
            StringBuffer stringBuffer = new StringBuffer();
            games.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(game -> stringBuffer
                    .append(game.getValue().getHomeTeam())
                    .append(" ")
                    .append(game.getValue().getHomeTeamScore())
                    .append(" - ")
                    .append(game.getValue().getAwayTeam())
                    .append(" ")
                    .append(game.getValue().getAwayTeamScore())
                    .append("\n"));

            return stringBuffer.toString();
        }
    }

    public Game findGameByTeams(String homeTeam, String awayTeam) {
        return findGameByKey(getGameKey(homeTeam, awayTeam));
    }

    public Game findGameByKey(String key) {
        synchronized (games) {
            return Optional.ofNullable(games.get(key)).orElseThrow();
        }
    }

    public String getGameKey(String homeTeam, String awayTeam) {
        return "$" + homeTeam + "+" + awayTeam;
    }
}
