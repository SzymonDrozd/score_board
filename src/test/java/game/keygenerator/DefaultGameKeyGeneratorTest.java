package game.keygenerator;

import game.keygenerator.configuration.GameKeyGeneratorConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GameKeyGeneratorConfiguration.class)
class DefaultGameKeyGeneratorTest {

    @Autowired
    public GameKeyGenerator defaultGameKeyGenerator;

    @Test
    public void testDefaultGameKeyGeneratorNull() {
        assertThrows(NoSuchElementException.class, () -> defaultGameKeyGenerator.generateKey(null));
    }

    @Test
    public void testDefaultGameKeyGenerator() {
        assertEquals("$HomeTeam+AwayTeam+END", defaultGameKeyGenerator.generateKey("HomeTeam", "AwayTeam"));
        assertEquals("$HomeTeam+AwayTeam+END", defaultGameKeyGenerator.generateKey("HomeTeam", null, "AwayTeam"));
        assertEquals("$HomeTeam+AwayTeam+TEST1+END", defaultGameKeyGenerator.generateKey("HomeTeam", null, "AwayTeam", "TEST1"));
        assertEquals("$HomeTeam+AwayTeam+TEST2+END", defaultGameKeyGenerator.generateKey("HomeTeam", null, "AwayTeam", "TEST2", null));
    }

}