package gwentstone.cards.minioncards;

import java.util.ArrayList;

public class Warden extends StandardMinionCard {
    public Warden() { }

    public Warden(final Warden warden) {
        super(warden);
    }

    public Warden(final int mana, final String description,
                  final ArrayList<String> colors, final String name,
                  final int health, final int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    @Override
    public final Warden deepCopy() {
        return new Warden(this);
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
