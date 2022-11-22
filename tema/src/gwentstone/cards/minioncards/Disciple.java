package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class Disciple extends SpecialMinionCard {
    public Disciple() { }

    public Disciple(final Disciple disciple) {
        super(disciple);
    }

    public Disciple(final int mana, final String description,
                    final ArrayList<String> colors, final String name,
                    final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final Disciple deepCopy() {
        return new Disciple(this);
    }

    @Override
    public final void useCardAbility(final StandardMinionCard attackedMinionCard) {
        attackedMinionCard.setHealth(attackedMinionCard.getHealth() + 2);
        setAvailable(false);
    }
}
