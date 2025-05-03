package board;

import exception.DuplicateGameException;
import model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ScoreBoard {

    private final Map<String, Game> games;

    public ScoreBoard() {
        games = new HashMap<>();
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

    public Game findGameByTeams(String homeTeam, String awayTeam) {
        return findGameByKey(getGameKey(homeTeam, awayTeam));
    }

    public Game findGameByKey(String key) {
        return games.get(key);
    }

    public String getGameKey(String homeTeam, String awayTeam) {
        return "$" + homeTeam + "+" + awayTeam;
    }
}
