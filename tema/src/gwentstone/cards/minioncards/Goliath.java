package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class Goliath extends StandardMinionCard {
    public Goliath() { }

    public Goliath(final Goliath goliath) {
        super(goliath);
    }

    public Goliath(final int mana, final String description,
                   final ArrayList<String> colors, final String name,
                   final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final Goliath deepCopy() {
        return new Goliath(this);
    }

    @Override
    public final boolean isFrontlineMinion() {
        return true;
    }

    @Override
    public final boolean isTank() {
        return true;
    }
}
