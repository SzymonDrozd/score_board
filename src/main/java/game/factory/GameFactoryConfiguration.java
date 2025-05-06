package game.factory;

import game.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameFactoryConfiguration {

    @Bean
    public GameFactory<Game> gameFactory() {
        return new GameFactory<>(Game.class);
    }
}
