package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class Berserker extends StandardMinionCard {
    public Berserker() { }

    public Berserker(final Berserker berserker) {
        super(berserker);
    }

    public Berserker(final int mana, final String description,
                     final ArrayList<String> colors,
                     final String name, final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final Berserker deepCopy() {
        return new Berserker(this);
    }
}
