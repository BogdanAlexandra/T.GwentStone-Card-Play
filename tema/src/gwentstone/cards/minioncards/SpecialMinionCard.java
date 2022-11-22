package gwentstone.cards.minioncards;

import java.util.ArrayList;

public abstract class SpecialMinionCard extends StandardMinionCard {

    protected SpecialMinionCard() { }

    protected SpecialMinionCard(final SpecialMinionCard specialMinionCard) {
        super(specialMinionCard);
    }

    protected SpecialMinionCard(final int mana, final String description,
                                final ArrayList<String> colors, final String name,
                                final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    public abstract void useCardAbility(StandardMinionCard attackedMinionCard);
}
