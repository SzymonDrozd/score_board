package game.summary;

import game.AbstractGame;

import java.util.Collection;

public interface SummaryBuilder<T extends AbstractGame> {

    String buildSummary(Collection<T> games);
}
