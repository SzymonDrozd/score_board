# Score Board Project

## Game

Game is a representation of possible game, containing basic attributes, implements Comparable and _updateScore(Integer newHomeTeamScore, Integer newAwayTeamScore)_.
In order to handle race condition this method is synchronized.

## SummarBuilder

Moved summary generation to an explicit Spring Bean in order to make it more customizable. In the future summary can be defined differently and it's good to have it easily modified.

## GameKeyGenerator

GameKeyGenerator creates unique key for a Game in an period of time. For example: there can be a few active games, but only one where there is Italy vs France so for key generator it should be enough.
After game is finished it should be moved to finished game list, and active games collection is open for other game with same teams.

## ScoreBoard

Score board is a service for games. It contains map of active games and list of finished games. Because in the same time game can be updated and summary might be triggered it required additional synchronization.
