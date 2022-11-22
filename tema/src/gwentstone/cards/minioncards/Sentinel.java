package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class Sentinel extends StandardMinionCard {
    public Sentinel() { }

    public Sentinel(final Sentinel sentinel) {
        super(sentinel);
    }

    public Sentinel(final int mana, final String description,
                    final ArrayList<String> colors, final String name,
                    final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final Sentinel deepCopy() {
        return new Sentinel(this);
    }
}
