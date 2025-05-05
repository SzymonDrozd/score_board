package game.keygenerator.configuration;

import game.keygenerator.DefaultGameKeyGenerator;
import game.keygenerator.GameKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameKeyGeneratorConfiguration {

    @Bean
    public GameKeyGenerator defaultGameKeyGenerator() {
        return new DefaultGameKeyGenerator();
    }
}
