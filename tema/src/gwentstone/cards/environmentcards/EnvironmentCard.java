package gwentstone.cards.environmentcards;

import gwentstone.GwentStone;
import gwentstone.cards.Card;

import java.util.ArrayList;

public abstract class EnvironmentCard extends Card {

    protected EnvironmentCard() { }

    protected EnvironmentCard(final EnvironmentCard environmentCard) {
        super(environmentCard);
    }

    protected EnvironmentCard(final int mana, final String description,
                              final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final boolean isEnvironmentCard() {
        return true;
    }

    public abstract void useEnvironmentAbility(int rowIdx);

    public final void removeFromHand() {
        GwentStone.getInstance().getPlayers()[GwentStone.getInstance().getCurrentPlayerIdx() - 1].
                getHand().getCards().remove(this);
    }
}
