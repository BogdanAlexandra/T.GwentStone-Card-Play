package gwentstone.cards.herocards;

import gwentstone.Board;
import gwentstone.GwentStone;
import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmpressThorina extends HeroCard {
    public EmpressThorina() { }

    public EmpressThorina(final EmpressThorina empressThorina) {
        super(empressThorina);
    }

    public EmpressThorina(final int mana, final String description,
                          final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final void useHeroAbility(final int rowIdx) {
        Board board = GwentStone.getInstance().getBoard();
        StandardMinionCard attackedCard = Collections.max(board.getRow(rowIdx),
                Comparator.comparingInt(StandardMinionCard::getHealth));

        attackedCard.die();
        setAvailable(false);
    }

    @Override
    public final EmpressThorina deepCopy() {
        return new EmpressThorina(this);
    }
}
