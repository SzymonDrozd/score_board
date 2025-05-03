package board.configuration;

import board.ScoreBoard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreBoardConfiguration {

    @Bean
    public ScoreBoard scoreBoard() {
        return new ScoreBoard();
    }
}
