package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class TheRipper extends SpecialMinionCard {
    public TheRipper() { }

    public TheRipper(final TheRipper theRipper) {
        super(theRipper);
    }

    public TheRipper(final int mana, final String description,
                     final ArrayList<String> colors, final String name,
                     final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final void useCardAbility(final StandardMinionCard attackedMinionCard) {
        attackedMinionCard.setAttackDamage(Math.max(attackedMinionCard.getAttackDamage() - 2, 0));
        setAvailable(false);
    }

    @Override
    public final TheRipper deepCopy() {
        return new TheRipper(this);
    }

    @Override
    public final boolean isFrontlineMinion() {
        return true;
    }
}
