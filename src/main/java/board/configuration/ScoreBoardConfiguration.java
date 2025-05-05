package board.configuration;

import board.ScoreBoard;
import game.keygenerator.GameKeyGenerator;
import game.summary.SummaryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreBoardConfiguration {

    @Bean
    public ScoreBoard scoreBoard(SummaryBuilder defaultSummaryBuilder, GameKeyGenerator defaultGameKeyGenerator) {
        return new ScoreBoard(defaultSummaryBuilder, defaultGameKeyGenerator);
    }
}
