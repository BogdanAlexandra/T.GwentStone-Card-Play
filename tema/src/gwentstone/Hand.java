package gwentstone;

import gwentstone.cards.Card;
import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards = new ArrayList<>();

    public final List<Card> getCards() {
        return cards;
    }

    public final void setCards(final List<Card> cards) {
        this.cards = cards;
    }

    public final void addCard(final Card card) {
        cards.add(card);
    }

    public final void removeCard(final int handIdx) {
        cards.remove(handIdx);
    }

    public final Card getCard(final int handIdx) {
        return cards.get(handIdx);
    }

    /**
     * for placing card on the row od the board
     * @param rowIdx number of the row where place de card
     * @param handIdx
     */
    public final void placeCardOnRow(final int rowIdx, final int handIdx) {
        GwentStone.getInstance().getBoard().getRow(rowIdx)
                .add((StandardMinionCard) getCard(handIdx));
        removeCard(handIdx);
    }

    public final void reset() {
        cards.clear();
    }
}
