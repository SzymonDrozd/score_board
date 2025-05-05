package board;

import exception.DuplicateGameException;
import exception.IncorrectGameValueException;
import game.Game;
import game.keygenerator.GameKeyGenerator;
import game.summary.SummaryBuilder;

import java.time.LocalDateTime;
import java.util.*;

public class ScoreBoard {

    private final SummaryBuilder summaryBuilder;
    private final GameKeyGenerator gameKeyGenerator;

    private final Map<String, Game> activeGames;
    private final List<Game> finishedGames;

    public ScoreBoard(SummaryBuilder summaryBuilder, GameKeyGenerator gameKeyGenerator) {
        this.summaryBuilder = summaryBuilder;
        this.gameKeyGenerator = gameKeyGenerator;

        activeGames = Collections.synchronizedMap(new HashMap<>());
        finishedGames = new ArrayList<>();
    }

    public void startGame(String homeTeam, String awayTeam) {
        String key = gameKeyGenerator.generateKey(homeTeam, awayTeam);

        Optional.ofNullable(activeGames.get(key)).ifPresent(value -> {
            throw new DuplicateGameException("Game already exists. Key: " + value);
        });

        Game game = Game.gameBuilder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .build();

        activeGames.put(key, game);
    }

    public void updateScoreByTeams(String homeTeam, String awayTeam, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        updateScoreByKey(gameKeyGenerator.generateKey(homeTeam, awayTeam), newHomeTeamScore, newAwayTeamScore);
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

    public String summaryOfActiveGames() {
        synchronized (activeGames) {
            return summaryBuilder.buildSummary(activeGames.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getValue).toList());
        }
    }

    public String summaryOfFinishedGames() {
        return summaryBuilder.buildSummary(finishedGames);
    }

    public void finishGameByTeams(String homeTeam, String awayTeam) {
        String key = gameKeyGenerator.generateKey(homeTeam, awayTeam);
        Game game = Optional.ofNullable(findGameByKey(key)).orElseThrow();

        synchronized (game) {
            game.setStopDateTime(LocalDateTime.now());
            activeGames.remove(key);
            finishedGames.add(game);
        }
    }

    public Game findGameByTeams(String homeTeam, String awayTeam) {
        return findGameByKey(gameKeyGenerator.generateKey(homeTeam, awayTeam));
    }

    public Game findGameByKey(String key) {
        synchronized (activeGames) {
            return Optional.ofNullable(activeGames.get(key)).orElseThrow();
        }
    }
}
