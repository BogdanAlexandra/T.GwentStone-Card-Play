package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class Miraj extends SpecialMinionCard {
    public Miraj() { }

    public Miraj(final Miraj miraj) {
        super(miraj);
    }

    public Miraj(final int mana, final String description,
                 final ArrayList<String> colors, final String name,
                 final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final void useCardAbility(final StandardMinionCard attackedMinionCard) {
        int temp = getHealth();

        setHealth(attackedMinionCard.getHealth());
        attackedMinionCard.setHealth(temp);

        setAvailable(false);
    }

    @Override
    public final Miraj deepCopy() {
        return new Miraj(this);
    }

    @Override
    public final boolean isFrontlineMinion() {
        return true;
    }
}
