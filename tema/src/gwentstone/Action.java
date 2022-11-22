package gwentstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;
import gwentstone.cards.Card;
import gwentstone.cards.environmentcards.EnvironmentCard;
import gwentstone.cards.herocards.HeroCard;
import gwentstone.cards.minioncards.SpecialMinionCard;
import gwentstone.cards.minioncards.StandardMinionCard;

public class Action {
    private static ObjectMapper mapper = new ObjectMapper();

    static final int TWO = 2;
    static final int THREE = 3;
    static final int FOUR = 4;
    static final int FIVE = 5;
    static final int SIX = 6;

    /** Functie care primeste actiunile de la Actions Input
     * si trateaza fiecare task cu un casepentru a fi mult mai ordonat main-ul
     */
    public static void runAction(final ActionsInput actionsInput, final ArrayNode output) {
        ObjectNode node = mapper.createObjectNode();
        node.put("command", actionsInput.getCommand());

        switch (actionsInput.getCommand()) {
            case "endPlayerTurn" -> endPlayerTurn();
            case "placeCard" -> placeCard(
                    actionsInput.getHandIdx(), node, output);
            case "cardUsesAttack" -> cardUsesAttack(
                    actionsInput.getCardAttacker(),
                    actionsInput.getCardAttacked(), node, output);
            case "cardUsesAbility" -> cardUsesAbility(
                    actionsInput.getCardAttacker(),
                    actionsInput.getCardAttacked(), node, output);
            case "useAttackHero" -> useAttackHero(
                    actionsInput.getCardAttacker(), node, output);
            case "useHeroAbility" -> useHeroAbility(
                    actionsInput.getAffectedRow(), node, output);
            case "useEnvironmentCard" -> useEnvironmentCard(
                    actionsInput.getHandIdx(),
                    actionsInput.getAffectedRow(), node, output);
            case "getCardsInHand" -> {
                getCardsInHand(actionsInput.getPlayerIdx(), node);
                output.add(node);
            }
            case "getCardsOnTable" -> {
                getCardsOnTable(node);
                output.add(node);
            }
            case "getCardAtPosition" -> {
                getCardAtPosition(actionsInput.getX(),
                        actionsInput.getY(), node);
                output.add(node);
            }
            case "getPlayerMana" -> {
                getPlayerMana(actionsInput.getPlayerIdx(), node);
                output.add(node);
            }
            case "getEnvironmentCardsInHand" -> {
                getEnvironmentCardsInHand(
                        actionsInput.getPlayerIdx(), node);
                output.add(node);
            }
            case "getFrozenCardsOnTable" -> {
                getFrozenCardsOnTable(node);
                output.add(node);
            }
            case "getTotalGamesPlayed" -> {
                getTotalGamesPlayed(node);
                output.add(node);
            }
            case "getPlayerOneWins" -> {
                node.put("output",
                        GwentStone.getInstance().getPlayers()[0].getWonGamesCount());
                output.add(node);
            }
            case "getPlayerTwoWins" -> {
                node.put("output",
                        GwentStone.getInstance().
                                getPlayers()[1].getWonGamesCount());
                output.add(node);
            }
            case "getPlayerDeck" -> {
                getPlayerDeck(actionsInput.getPlayerIdx(), node);
                output.add(node);
            }
            case "getPlayerTurn" -> {
                getPlayerTurn(node);
                output.add(node);
            }
            case "getPlayerHero" -> {
                getPlayerHero(actionsInput.getPlayerIdx(), node);
                output.add(node);
            }
            default -> throw new IllegalArgumentException("Invalid action input" + actionsInput);
        }
    }

    private static void getPlayerDeck(final int playerIdx, final ObjectNode node) {
        node.put("playerIdx", playerIdx);

        ArrayNode arrayNode = mapper.createArrayNode();

        for (Card card : GwentStone.getInstance()
                .getPlayers()[playerIdx - 1].getCurrentDeck().getCards()) {
            arrayNode.add(mapper.valueToTree(card));
        }

        node.set("output", arrayNode);
    }

    private static void getPlayerHero(final int playerIdx, final ObjectNode node) {
        node.put("playerIdx", playerIdx);
        node.set("output", mapper.valueToTree(GwentStone
                .getInstance().getPlayers()[playerIdx - 1].getHeroCard()));
    }

    private static void getPlayerTurn(final ObjectNode node) {
        node.put("output", GwentStone.getInstance().getCurrentPlayerIdx());
    }

    private static void getCardsInHand(final int playerIdx, final ObjectNode node) {
        node.put("playerIdx", playerIdx);

        ArrayNode arrayNode = mapper.createArrayNode();

        for (Card card : GwentStone.getInstance()
                .getPlayers()[playerIdx - 1].getHand().getCards()) {
            arrayNode.add(mapper.valueToTree(card));
        }

        node.set("output", arrayNode);
    }

    private static void getPlayerMana(final int playerIdx, final ObjectNode node) {
        node.put("playerIdx", playerIdx);
        node.put("output", GwentStone.getInstance().getPlayers()[playerIdx - 1].getMana());
    }

    private static void getEnvironmentCardsInHand(final int playerIdx, final ObjectNode node) {
        node.put("playerIdx", playerIdx);

        ArrayNode arrayNode = mapper.createArrayNode();

        for (Card card : GwentStone.getInstance()
                .getPlayers()[playerIdx - 1].getHand().getCards()) {
            if (card.isEnvironmentCard()) {
                arrayNode.add(mapper.valueToTree(card));
            }
        }

        node.set("output", arrayNode);
    }

    private static void getTotalGamesPlayed(final ObjectNode node) {
        node.put("output", GwentStone.getInstance().getPlayedGamesCount());
    }

    private static void endPlayerTurn() {
        for (int i = FOUR; i < SIX; i++) {
            for (StandardMinionCard standardMinionCard
                    : GwentStone.getInstance().getBoard()
                    .getRow(GwentStone.getInstance()
                            .getCurrentPlayerIdx() * (-TWO) + i)) {
                standardMinionCard.unfreeze();
                standardMinionCard.setAvailable(true);
            }
        }

        GwentStone.getInstance().getPlayers()[GwentStone.getInstance().getCurrentPlayerIdx() - 1]
                .getHeroCard().setAvailable(true);

        GwentStone.getInstance().getPlayers()[GwentStone.getInstance()
                .getCurrentPlayerIdx() - 1].setFinishedTurn(true);
        GwentStone.getInstance().setCurrentPlayerIdx(GwentStone.getInstance()
                .getCurrentPlayerIdx() % TWO + 1);

        if (GwentStone.getInstance().getPlayers()[0].getFinishedTurn()
                && GwentStone.getInstance().getPlayers()[1].getFinishedTurn()) {
            GwentStone.getInstance().startNextRound();
        }
    }

    private static void placeCard(final int handIdx, final ObjectNode node,
                                  final ArrayNode output) {
        node.put("handIdx", handIdx);

        Player currentPlayer = GwentStone.getInstance()
                .getPlayers()[GwentStone.getInstance().getCurrentPlayerIdx() - 1];
        Hand hand = currentPlayer.getHand();
        Card card = hand.getCard(handIdx);
        Board board = GwentStone.getInstance().getBoard();

        if (card.isEnvironmentCard()) {
            node.put("error", "Cannot place environment card on table.");
            output.add(node);
            return;
        }

        if (card.getMana() > currentPlayer.getMana()) {
            node.put("error", "Not enough mana to place card on table.");
            output.add(node);
            return;
        }

        int rowIdx;

        if (((StandardMinionCard) card).isFrontlineMinion()) {
            rowIdx = GwentStone.getInstance().getCurrentPlayerIdx() * (-1) + THREE;
        } else {
            rowIdx = GwentStone.getInstance().getCurrentPlayerIdx() * (-THREE) + SIX;
        }

        if (board.rowIsFull(rowIdx)) {
            node.put("error", "Cannot place card on table since row is full.");
            output.add(node);
            return;
        }

        ((StandardMinionCard) card).setRowIdx(rowIdx);
        currentPlayer.subtractMana(card.getMana());
        hand.placeCardOnRow(rowIdx, handIdx);
    }

    private static void getCardsOnTable(final ObjectNode node) {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < FOUR; i++) {
            ArrayNode tempArrayNode = mapper.createArrayNode();

            for (Card card : GwentStone.getInstance().getBoard().getRow(i)) {
                tempArrayNode.add(mapper.valueToTree(card));
            }

            arrayNode.add(tempArrayNode);
        }


        node.set("output", arrayNode);
    }

    private static void useEnvironmentCard(final int handIdx, final int rowIdx,
                                           final ObjectNode node,
                                           final ArrayNode output) {
        node.put("handIdx", handIdx);
        node.put("affectedRow", rowIdx);

        Player currentPlayer = GwentStone.getInstance()
                .getPlayers()[GwentStone.getInstance().getCurrentPlayerIdx() - 1];
        Hand hand = currentPlayer.getHand();
        Card card = hand.getCard(handIdx);
        Board board = GwentStone.getInstance().getBoard();

        if (!card.isEnvironmentCard()) {
            node.put("error", "Chosen card is not of type environment.");
            output.add(node);
            return;
        }

        if (card.getMana() > currentPlayer.getMana()) {
            node.put("error", "Not enough mana to use environment card.");
            output.add(node);
            return;
        }

        if (rowIdx == GwentStone.getInstance().getCurrentPlayerIdx() * (-TWO) + FOUR
            || rowIdx == GwentStone.getInstance().getCurrentPlayerIdx() * (-TWO) + FIVE) {
            node.put("error", "Chosen row does not belong to the enemy.");
            output.add(node);
            return;
        }

        if (card.getName().equals("Heart Hound")) {
            int oppositeRowIdx = THREE - rowIdx;

            if (board.rowIsFull(oppositeRowIdx)) {
                node.put("error", "Cannot steal enemy card since the player's row is full.");
                output.add(node);
                return;
            }
        }

        currentPlayer.subtractMana(card.getMana());
        ((EnvironmentCard) card).useEnvironmentAbility(rowIdx);
    }

    private static void getCardAtPosition(final int x, final int y, final ObjectNode node) {
        node.put("x", x);
        node.put("y", y);

        Board board = GwentStone.getInstance().getBoard();

        if (board.getRow(x).size() - 1 < y) {
            node.put("output", "No card available at that position.");
        } else {
            node.set("output", mapper.valueToTree(board.getRow(x).get(y)));
        }
    }

    private static void getFrozenCardsOnTable(final ObjectNode node) {
        ArrayNode arrayNode = mapper.createArrayNode();

        for (int i = 0; i < FOUR; i++) {
            for (StandardMinionCard standardMinionCard
                    : GwentStone.getInstance().getBoard().getRow(i)) {
                if (standardMinionCard.isFrozen()) {
                    arrayNode.add(mapper.valueToTree(standardMinionCard));
                }
            }
        }

        node.set("output", arrayNode);
    }

    private static void cardUsesAttack(final Coordinates cardAttacker,
                                       final Coordinates cardAttacked, final ObjectNode node,
                                       final ArrayNode output) {
        node.set("cardAttacker", mapper.valueToTree(cardAttacker));
        node.set("cardAttacked", mapper.valueToTree(cardAttacked));

        if (cardAttacked.getX() == GwentStone.getInstance().getCurrentPlayerIdx() * (-TWO) + FOUR
            || cardAttacked.getX() == GwentStone.getInstance().
                getCurrentPlayerIdx() * (-TWO) + FIVE) {
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
            return;
        }

        Board board = GwentStone.getInstance().getBoard();
        StandardMinionCard attackerMinionCard = board
                .getRow(cardAttacker.getX()).get(cardAttacker.getY());
        StandardMinionCard attackedMinionCard = board
                .getRow(cardAttacked.getX()).get(cardAttacked.getY());

        if (!attackerMinionCard.isAvailable()) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }

        if (attackerMinionCard.isFrozen()) {
            node.put("error", "Attacker card is frozen.");
            output.add(node);
            return;
        }

        if (!attackedMinionCard.isTank()) {
            for (StandardMinionCard standardMinionCard
                    : board.getRow(GwentStone.getInstance().getCurrentPlayerIdx())) {
                if (standardMinionCard.isTank()) {
                    node.put("error", "Attacked card is not of type 'Tank'.");
                    output.add(node);
                    return;
                }
            }
        }

        attackerMinionCard.attackCard(attackedMinionCard);
    }

    private static void cardUsesAbility(final Coordinates cardAttacker,
                                        final Coordinates cardAttacked, final ObjectNode node,
                                        final ArrayNode output) {
        node.set("cardAttacker", mapper.valueToTree(cardAttacker));
        node.set("cardAttacked", mapper.valueToTree(cardAttacked));

        Board board = GwentStone.getInstance().getBoard();
        SpecialMinionCard attackerMinionCard =
                (SpecialMinionCard) board.getRow(cardAttacker.getX())
                        .get(cardAttacker.getY());
        StandardMinionCard attackedMinionCard =
                board.getRow(cardAttacked.getX())
                        .get(cardAttacked.getY());

        if (attackerMinionCard.isFrozen()) {
            node.put("error", "Attacker card is frozen.");
            output.add(node);
            return;
        }

        if (!attackerMinionCard.isAvailable()) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }

        if (attackerMinionCard.getName().equals("Disciple")) {
            if (cardAttacked.getX() == (GwentStone.getInstance()
                    .getCurrentPlayerIdx() % TWO + 1) * (-TWO) + FOUR
                    || cardAttacked.getX() == (GwentStone
                    .getInstance().getCurrentPlayerIdx() % TWO + 1) * (-TWO) + FIVE) {
                node.put("error", "Attacked card does not belong to the current player.");
                output.add(node);
                return;
            }

            attackerMinionCard.useCardAbility(attackedMinionCard);
            return;
        }

        if (cardAttacked.getX() == GwentStone.getInstance().getCurrentPlayerIdx() * (-TWO) + FOUR
                || cardAttacked.getX() == GwentStone
                .getInstance().getCurrentPlayerIdx() * (-TWO) + FIVE) {
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
            return;
        }

        if (!attackedMinionCard.isTank()) {
            for (StandardMinionCard standardMinionCard
                    : board.getRow(GwentStone.getInstance().getCurrentPlayerIdx())) {
                if (standardMinionCard.isTank()) {
                    node.put("error", "Attacked card is not of type 'Tank'.");
                    output.add(node);
                    return;
                }
            }
        }

        attackerMinionCard.useCardAbility(attackedMinionCard);
    }

    private static void useAttackHero(final Coordinates cardAttacker,
                                      final ObjectNode node, final ArrayNode output) {
        node.set("cardAttacker", mapper.valueToTree(cardAttacker));

        Board board = GwentStone.getInstance().getBoard();
        StandardMinionCard attackerMinionCard =
                board.getRow(cardAttacker.getX()).get(cardAttacker.getY());

        if (attackerMinionCard.isFrozen()) {
            node.put("error", "Attacker card is frozen.");
            output.add(node);
            return;
        }

        if (!attackerMinionCard.isAvailable()) {
            node.put("error", "Attacker card has already attacked this turn.");
            output.add(node);
            return;
        }

        for (StandardMinionCard standardMinionCard
                : board.getRow(GwentStone.getInstance().getCurrentPlayerIdx())) {
            if (standardMinionCard.isTank()) {
                node.put("error", "Attacked card is not of type 'Tank'.");
                output.add(node);
                return;
            }
        }

        attackerMinionCard.attackHero();
    }

    public static void useHeroAbility(final int rowIdx, final ObjectNode node,
                                      final ArrayNode output) {
        node.put("affectedRow", rowIdx);

        Player currentPlayer = GwentStone.getInstance()
                .getPlayers()[GwentStone.getInstance().getCurrentPlayerIdx() - 1];
        HeroCard hero = currentPlayer.getHeroCard();
        if (currentPlayer.getMana() < hero.getMana()) {
            node.put("error", "Not enough mana to use hero's ability.");
            output.add(node);
            return;
        }

        if (!hero.isAvailable()) {
            node.put("error", "Hero has already attacked this turn.");
            output.add(node);
            return;
        }

        if (hero.getName().equals("Lord Royce")
                || hero.getName().equals("Empress Thorina")) {
            if (rowIdx == GwentStone.getInstance().getCurrentPlayerIdx() * (-TWO) + FOUR
                    || rowIdx == GwentStone.getInstance()
                    .getCurrentPlayerIdx() * (-TWO) + FIVE) {
                node.put("error", "Selected row does not belong to the enemy.");
                output.add(node);
                return;
            }
        }

        if (hero.getName().equals("General Kocioraw")
                || hero.getName().equals("King Mudface")) {
            if (rowIdx == (GwentStone.getInstance().getCurrentPlayerIdx() % TWO + 1) * (-TWO) + FOUR
                    || rowIdx == (GwentStone.getInstance()
                    .getCurrentPlayerIdx() % TWO + 1) * (-TWO) + FIVE) {
                node.put("error", "Selected row does not belong to the current player.");
                output.add(node);
                return;
            }
        }

        currentPlayer.subtractMana(hero.getMana());
        hero.useHeroAbility(rowIdx);
    }
}
