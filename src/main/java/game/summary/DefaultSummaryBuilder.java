package game.summary;

import game.AbstractGame;

import java.util.Collection;

public class DefaultSummaryBuilder implements SummaryBuilder<AbstractGame> {

    @Override
    public String buildSummary(Collection<AbstractGame> games) {
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
