package gwentstone.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public abstract class Card {

    private int mana;

    private String description;

    private ArrayList<String> colors;

    private String name;

    protected Card() { }

    protected Card(final Card card) {
        this.mana = card.mana;
        this.description = card.description;
        this.colors = card.colors;
        this.name = card.name;
    }

    protected Card(final int mana, final String description,
                   final ArrayList<String> colors, final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    @JsonIgnore
    public abstract boolean isEnvironmentCard();

    public abstract Card deepCopy();

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }
}
