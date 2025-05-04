package board;

import board.configuration.ScoreBoardConfiguration;
import exception.DuplicateGameException;
import exception.IncorrectGameValueException;
import model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScoreBoardConfiguration.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class ScoreBoardTest {

    @Autowired
    ScoreBoard scoreBoard;

    @Test
    public void testStartGame() {
        scoreBoard.startGame("Test1", "Test2");
        Game game = scoreBoard.findGameByTeams("Test1", "Test2");

        assertNotNull(game);
        assertEquals("Test1", game.getHomeTeam());
        assertEquals("Test2", game.getAwayTeam());

        assertEquals(0, game.getHomeTeamScore());
        assertEquals(0, game.getAwayTeamScore());

        assertThrows(DuplicateGameException.class, () -> scoreBoard.startGame("Test1", "Test2"));
    }

    @Test
    public void testUpdateScoreGameNotFound() {
        assertThrows(NoSuchElementException.class, () -> scoreBoard.updateScore("Test1", "Test2", 1, 1));
    }

    @Test
    public void testUpdateScoreInvalidValue() {
        scoreBoard.startGame("Test1", "Test2");

        assertThrows(IncorrectGameValueException.class, () -> scoreBoard.updateScore("Test1", "Test2", -1, 1));
    }

    @Test
    public void testUpdateScore() {
        scoreBoard.startGame("Test1", "Test2");
        scoreBoard.updateScore("Test1", "Test2", 1, 1);

        Game game = scoreBoard.findGameByTeams("Test1", "Test2");

        assertEquals(1, game.getHomeTeamScore());
        assertEquals(1, game.getAwayTeamScore());

        scoreBoard.updateScore("Test1", "Test2", 2, 0);

        assertEquals(2, game.getHomeTeamScore());
        assertEquals(0, game.getAwayTeamScore());

        scoreBoard.updateScore("Test1", "Test2", null, 3);

        assertEquals(2, game.getHomeTeamScore());
        assertEquals(3, game.getAwayTeamScore());
    }

    @Test
    public void testSummary() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2);

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);

        assertEquals("""
                Uruguay 6 - Italy 6
                Spain 10 - Brazil 2
                Mexico 0 - Canada 5
                Argentina 3 - Australia 1
                Germany 2 - France 2
                """, scoreBoard.summary());
    }

    @Test
    public void testSummaryMultiThreading() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2);

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);

        Runnable task1 = () -> {
            System.out.println("First task");
            assertEquals("""
                Uruguay 6 - Italy 6
                Spain 10 - Brazil 2
                Mexico 0 - Canada 5
                Argentina 3 - Australia 1
                Germany 2 - France 2
                """, scoreBoard.summary());
        };

        Runnable task2 = () -> {
            System.out.println("Second task");
            scoreBoard.updateScore("Uruguay", "Italy", 0, 0);
        };

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            executorService.execute(task1);
            executorService.execute(task2);
        }

    }
}