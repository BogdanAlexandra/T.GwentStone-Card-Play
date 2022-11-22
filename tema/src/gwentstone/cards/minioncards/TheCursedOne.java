package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class TheCursedOne extends SpecialMinionCard {
    public TheCursedOne() { }

    public TheCursedOne(final TheCursedOne theCursedOne) {
        super(theCursedOne);
    }

    public TheCursedOne(final int mana, final String description, final ArrayList<String> colors,
                        final String name, final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final void useCardAbility(final StandardMinionCard attackedMinionCard) {
        int temp = attackedMinionCard.getAttackDamage();
        attackedMinionCard.setAttackDamage(attackedMinionCard.getHealth());
        attackedMinionCard.setHealth(temp);

        if (attackedMinionCard.getHealth() == 0) {
            attackedMinionCard.die();
        }
        setAvailable(false);
    }

    @Override
    public final TheCursedOne deepCopy() {
        return new TheCursedOne(this);
    }
}
