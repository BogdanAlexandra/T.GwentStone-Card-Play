package gwentstone.cards.herocards;

import gwentstone.Board;
import gwentstone.GwentStone;
import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;

public class GeneralKocioraw extends HeroCard {
    public GeneralKocioraw() { }

    public GeneralKocioraw(final GeneralKocioraw generalKocioraw) {
        super(generalKocioraw);
    }

    public GeneralKocioraw(final int mana, final String description,
                           final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final void useHeroAbility(final int rowIdx) {
        Board board = GwentStone.getInstance().getBoard();

        for (StandardMinionCard standardMinionCard
                : board.getRow(rowIdx)) {
            standardMinionCard.setAttackDamage(standardMinionCard
                    .getAttackDamage() + 1);
        }

        setAvailable(false);
    }

    @Override
    public final GeneralKocioraw deepCopy() {
        return new GeneralKocioraw(this);
    }
}
