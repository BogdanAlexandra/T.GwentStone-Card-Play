package gwentstone.cards.herocards;

import gwentstone.Board;
import gwentstone.GwentStone;
import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;

public class KingMudface extends HeroCard {
    public KingMudface() { }

    public KingMudface(final KingMudface kingMudface) {
        super(kingMudface);
    }

    public KingMudface(final int mana, final String description,
                       final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final void useHeroAbility(final int rowIdx) {
        Board board = GwentStone.getInstance().getBoard();

        for (StandardMinionCard standardMinionCard : board.getRow(rowIdx)) {
            standardMinionCard.setHealth(standardMinionCard.getHealth() + 1);
        }

        setAvailable(false);
    }

    @Override
    public final KingMudface deepCopy() {
        return new KingMudface(this);
    }
}
