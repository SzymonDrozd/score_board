package board.configuration;

import board.ScoreBoard;
import game.Game;
import game.factory.GameFactory;
import game.keygenerator.GameKeyGenerator;
import game.summary.SummaryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreBoardConfiguration {

    @Bean
    public ScoreBoard<Game> scoreBoard(GameFactory<Game> gameFactory, SummaryBuilder<Game> defaultSummaryBuilder, GameKeyGenerator defaultGameKeyGenerator) {
        return new ScoreBoard<Game>(gameFactory, defaultSummaryBuilder, defaultGameKeyGenerator);
    }
}
