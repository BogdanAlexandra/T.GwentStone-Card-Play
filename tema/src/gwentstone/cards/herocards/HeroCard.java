package gwentstone.cards.herocards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gwentstone.GwentStone;
import gwentstone.cards.Card;

import java.util.ArrayList;

public abstract class HeroCard extends Card {
    static final int THIRTY = 30;
    private int health = THIRTY;
    @JsonIgnore

    private boolean available = true;

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final boolean isAvailable() {
        return available;
    }

    public final void setAvailable(final boolean available) {
        this.available = available;
    }

    protected HeroCard() { }

    protected HeroCard(final HeroCard heroCard) {
        super(heroCard);

        this.health = heroCard.health;
    }

    protected HeroCard(final int mana, final String description,
                       final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final boolean isEnvironmentCard() {
        return false;
    }

    public abstract void useHeroAbility(int rowIdx);

    public final void takeDamage(final int damage) {
        health -= damage;

        if (health <= 0) {
            GwentStone.getInstance().endGame();
        }
    }
}
