package game.summary.configuration;

import game.summary.DefaultSummaryBuilder;
import game.summary.SummaryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SummaryBuilderConfiguration {

    @Bean
    public SummaryBuilder defaultSummaryBuilder() {
        return new DefaultSummaryBuilder();
    }
}
