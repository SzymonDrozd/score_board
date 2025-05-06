package game.factory;

import game.AbstractGame;

import java.lang.reflect.InvocationTargetException;

public class GameFactory<T extends AbstractGame> {

    private final Class<T> typeOfGame;

    public GameFactory(Class<T> tClass) {
        this.typeOfGame = tClass;
    }

    public <T> T getGame(String homeTeam, String awayTeam) {
        try {
            return (T) typeOfGame.getConstructor(String.class, String.class).newInstance(homeTeam, awayTeam);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
