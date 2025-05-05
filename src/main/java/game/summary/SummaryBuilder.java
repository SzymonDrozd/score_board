package game.summary;

import game.Game;

import java.util.Collection;

public interface SummaryBuilder {

    String buildSummary(Collection<Game> games);
}
