package board;

import board.configuration.ScoreBoardConfiguration;
import exception.DuplicateGameException;
import model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScoreBoardConfiguration.class)
class ScoreBoardTest {

    @Autowired
    ScoreBoard scoreBoard;

    @Test
    void testStartGame() {
        scoreBoard.startGame("Test1", "Test2");
        Game game = scoreBoard.findGameByTeams("Test1", "Test2");

        assertNotNull(game);
        assertEquals("Test1", game.getHomeTeam());
        assertEquals("Test2", game.getAwayTeam());

        assertEquals(0, game.getHomeTeamScore());
        assertEquals(0, game.getAwayTeamScore());

        assertThrows(DuplicateGameException.class, () -> scoreBoard.startGame("Test1", "Test2"));
    }
}