package gwentstone.cards.environmentcards;

import gwentstone.Board;
import gwentstone.GwentStone;
import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;

public class Winterfell extends EnvironmentCard {
    public Winterfell() { }

    public Winterfell(final Winterfell winterfell) {
        super(winterfell);
    }

    public Winterfell(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final void useEnvironmentAbility(final int rowIdx) {
        Board board = GwentStone.getInstance().getBoard();

        for (StandardMinionCard standardMinionCard : board.getRow(rowIdx)) {
            standardMinionCard.freeze();
        }

        removeFromHand();
    }

    @Override
    public final Winterfell deepCopy() {
        return new Winterfell(this);
    }
}
