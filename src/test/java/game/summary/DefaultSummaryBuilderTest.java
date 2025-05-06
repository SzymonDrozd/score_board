package game.summary;

import game.Game;
import game.summary.configuration.SummaryBuilderConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SummaryBuilderConfiguration.class)
class DefaultSummaryBuilderTest {

    @Autowired
    public SummaryBuilder<Game> defaultSummaryBuilder;

    @Test
    public void testDefaultSummaryBuilder() {
        List<Game> testData = List.of(new Game("Test1", "Test2"),
                new Game("HomeTeam", "AwayTeam"),
                new Game("Hello", "There"),
                new Game("Test1", "Test2"));

        assertEquals("""
                Test1 0 - Test2 0
                HomeTeam 0 - AwayTeam 0
                Hello 0 - There 0
                Test1 0 - Test2 0
                """, defaultSummaryBuilder.buildSummary(testData));
    }

}