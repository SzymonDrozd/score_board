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
    public SummaryBuilder defaultSummaryBuilder;

    @Test
    public void testDefaultSummaryBuilder() {
        List<Game> testData = List.of(Game.gameBuilder().homeTeam("Test1").awayTeam("Test2").build(),
                                        Game.gameBuilder().homeTeam("HomeTeam").awayTeam("AwayTeam").build(),
                                        Game.gameBuilder().homeTeam("Hello").awayTeam("There").build(),
                                        Game.gameBuilder().homeTeam("Test1").awayTeam("Test2").build());

        assertEquals("""
                Test1 0 - Test2 0
                HomeTeam 0 - AwayTeam 0
                Hello 0 - There 0
                Test1 0 - Test2 0
                """, defaultSummaryBuilder.buildSummary(testData));
    }

}