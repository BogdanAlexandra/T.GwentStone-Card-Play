package gwentstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.DecksInput;
import fileio.StartGameInput;
import fileio.Input;
import fileio.ActionsInput;
import fileio.GameInput;



import java.util.ArrayList;

public final class GwentStone {
    private static GwentStone instance = null;
    private int shuffleSeed;
    private Player[] players = new Player[2];
    private Board board = new Board(FOUR, FIVE);
    private int currentPlayerIdx;
    private int playedGamesCount = 0;
    private int manaPerRound = 1;
    private ArrayNode output;
    private boolean gameHasEnded = false;
    static final int FOUR = 4;
    static final int FIVE = 5;
    static final int TEN = 10;


    public ArrayNode getOutput() {
        return output;
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }


    public int getCurrentPlayerIdx() {
        return currentPlayerIdx;
    }

    public void setCurrentPlayerIdx(final int currentPlayerIdx) {
        this.currentPlayerIdx = currentPlayerIdx;
    }

    public int getPlayedGamesCount() {
        return playedGamesCount;
    }

    private GwentStone() { }

    public static GwentStone getInstance() {
        if (instance == null) {
            instance = new GwentStone();
        }

        return instance;
    }

    /**
     * for preparing the players
     * @param playerOneDecks deck for player one
     * @param playerTwoDecks deck for second player
     */
    public void preparePlayers(final DecksInput playerOneDecks, final DecksInput playerTwoDecks) {
        for (int i = 0; i < 2; i++) {
            players[i] = new Player();
        }

        players[0].setDecks(playerOneDecks);
        players[1].setDecks(playerTwoDecks);
    }

    /**
     * for preparing the game
     * @param startGameInput
     */
    public void prepareGame(final StartGameInput startGameInput) {
        players[0].setCurrentDeck(startGameInput.getPlayerOneDeckIdx());
        players[1].setCurrentDeck(startGameInput.getPlayerTwoDeckIdx());

        shuffleSeed = startGameInput.getShuffleSeed();

        for (int i = 0; i < 2; i++) {
            players[i].getCurrentDeck().shuffle();
        }

        players[0].setHeroCard(startGameInput.getPlayerOneHero());
        players[1].setHeroCard(startGameInput.getPlayerTwoHero());

        currentPlayerIdx = startGameInput.getStartingPlayer();
    }

    /**
     * for starting the Game
     * @param actionsArr
     * @param outputstart
     */
    public void startGame(final ArrayList<ActionsInput> actionsArr, final ArrayNode outputstart) {
        playedGamesCount++;
        manaPerRound = 1;
        gameHasEnded = false;

        for (int i = 0; i < 2; i++) {
            players[i].resetMana();
            players[i].getHand().reset();
        }

        board.reset();
        startNextRound();

        for (ActionsInput actionsInput : actionsArr) {
            Action.runAction(actionsInput, output);

            if (gameHasEnded) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();

                if (currentPlayerIdx == 1) {
                    node.put("gameEnded", "Player one killed the enemy hero.");
                } else if (currentPlayerIdx == 2) {
                    node.put("gameEnded", "Player two killed the enemy hero.");
                }

                output.add(node);

                gameHasEnded = false;
            }
        }
    }

    public void play(final Input inputData, final ArrayNode outputplay) {
        this.output = outputplay;
        playedGamesCount = 0;
        preparePlayers(inputData.getPlayerOneDecks(), inputData.getPlayerTwoDecks());

        for (GameInput gameInput : inputData.getGames()) {
            prepareGame(gameInput.getStartGame());
            startGame(gameInput.getActions(), output);
        }
    }

    /**
     * for staring the next round
     */
    public void startNextRound() {
        for (int i = 0; i < 2; i++) {
            players[i].setFinishedTurn(false);
            players[i].getCurrentDeck().dealCard(players[i].getHand());
            players[i].addMana(manaPerRound);
        }

        if (manaPerRound < TEN) {
            manaPerRound++;
        }
    }

    /**
     * for ending the game
     */
    public void endGame() {
        gameHasEnded = true;
        players[currentPlayerIdx - 1]
                .setWonGamesCount(players[currentPlayerIdx - 1].getWonGamesCount() + 1);
    }
}
