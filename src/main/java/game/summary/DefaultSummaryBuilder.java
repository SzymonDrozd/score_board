package game.summary;

import game.Game;

import java.util.Collection;

public class DefaultSummaryBuilder implements SummaryBuilder {

    @Override
    public String buildSummary(Collection<Game> games) {
        StringBuffer stringBuffer = new StringBuffer();
        games.forEach(game -> stringBuffer
                .append(game.getHomeTeam())
                .append(" ")
                .append(game.getHomeTeamScore())
                .append(" - ")
                .append(game.getAwayTeam())
                .append(" ")
                .append(game.getAwayTeamScore())
                .append("\n"));

        return stringBuffer.toString();
    }
}
