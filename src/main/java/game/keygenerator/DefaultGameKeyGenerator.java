package game.keygenerator;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultGameKeyGenerator implements GameKeyGenerator{

    private static final String KEY_START = "$";
    private static final String KEY_DELIMITER = "+";
    private static final String KEY_END = "END";

    @Override
    public String generateKey(String... args) {
        StringBuffer key = new StringBuffer();
        Optional.ofNullable(args)
                .ifPresentOrElse(
                        params -> key
                                .append(KEY_START)
                                .append(Arrays.stream(params).filter(Objects::nonNull).map(param -> param + KEY_DELIMITER).collect(Collectors.joining()))
                                .append(KEY_END),
                        () -> {
                            throw new NoSuchElementException();
                        });

        return key.toString();
    }
}
