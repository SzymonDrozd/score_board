package board;

import exception.DuplicateGameException;
import exception.IncorrectGameValueException;
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

    public void updateScoreByTeams(String homeTeam, String awayTeam, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        updateScoreByKey(getGameKey(homeTeam, awayTeam), newHomeTeamScore, newAwayTeamScore);
    }

    public void updateScoreByKey(String key, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        Optional.ofNullable(findGameByKey(key))
                .ifPresent(game -> updateScore(game, newHomeTeamScore, newAwayTeamScore));

    }

    private void updateScore(Game game, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        synchronized (game) {
            Optional.ofNullable(newHomeTeamScore).ifPresent(value -> {
                if(value < 0) {
                    throw new IncorrectGameValueException("Home Team score value is out of range. Value: " + value);
                }

                game.setHomeTeamScore(value);
            });

            Optional.ofNullable(newAwayTeamScore).ifPresent(value -> {
                if(value < 0) {
                    throw new IncorrectGameValueException("Away Team score value is out of range. Value: " + value);
                }

                game.setAwayTeamScore(value);
            });
        }
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
