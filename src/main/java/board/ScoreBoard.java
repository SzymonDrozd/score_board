package board;

import exception.DuplicateGameException;
import game.AbstractGame;
import game.factory.GameFactory;
import game.keygenerator.GameKeyGenerator;
import game.summary.SummaryBuilder;

import java.util.*;

public class ScoreBoard<T extends AbstractGame> {

    private final GameFactory<T> gameFactory;
    private final SummaryBuilder<T> summaryBuilder;
    private final GameKeyGenerator gameKeyGenerator;

    private final Map<String, T> activeGames;
    private final List<T> finishedGames;

    public ScoreBoard(GameFactory<T> gameFactory, SummaryBuilder<T> summaryBuilder, GameKeyGenerator gameKeyGenerator) {
        this.gameFactory = gameFactory;
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

        activeGames.put(key, gameFactory.getGame(homeTeam, awayTeam));
    }

    public void updateScoreByTeams(String homeTeam, String awayTeam, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        updateScoreByKey(gameKeyGenerator.generateKey(homeTeam, awayTeam), newHomeTeamScore, newAwayTeamScore);
    }

    public void updateScoreByKey(String key, Integer newHomeTeamScore, Integer newAwayTeamScore) {
        Optional<T> gameOpt = Optional.ofNullable(findGameByKey(key));
        gameOpt.ifPresent(game -> game.updateScore(newHomeTeamScore, newAwayTeamScore));
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
        T game = (T) Optional.ofNullable(findGameByKey(key)).orElseThrow();

        synchronized (game) {
            game.finishGame();
            activeGames.remove(key);
            finishedGames.add((T) game);
        }
    }

    public <T> T findGameByTeams(String homeTeam, String awayTeam) {
        return findGameByKey(gameKeyGenerator.generateKey(homeTeam, awayTeam));
    }

    public <T> T findGameByKey(String key) {
        synchronized (activeGames) {
            return (T) Optional.ofNullable(activeGames.get(key)).orElseThrow();
        }
    }
}
