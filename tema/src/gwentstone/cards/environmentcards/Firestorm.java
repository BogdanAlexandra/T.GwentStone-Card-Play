package gwentstone.cards.environmentcards;

import gwentstone.Board;
import gwentstone.GwentStone;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCard {
    private int abilityDamage = 1;
    public Firestorm() { }

    public Firestorm(final Firestorm firestorm) {
        super(firestorm);
    }

    public Firestorm(final int mana, final String description,
                     final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final void useEnvironmentAbility(final int rowIdx) {
        Board board = GwentStone.getInstance().getBoard();
        for (int i = board.getRow(rowIdx).size() - 1; i >= 0; i--) {
            board.getRow(rowIdx).get(i).takeDamage(abilityDamage);
        }

        removeFromHand();
    }

    @Override
    public final Firestorm deepCopy() {
        return new Firestorm(this);
    }
}
